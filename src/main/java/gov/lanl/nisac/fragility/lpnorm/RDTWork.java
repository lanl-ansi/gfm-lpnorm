package gov.lanl.nisac.fragility.lpnorm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.net.MalformedURLException;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.lanl.nisac.fragility.FragilityCommandLineParser;
import gov.lanl.nisac.fragility.responseModels.IResponse;

import javax.json.*;
import javax.json.stream.JsonGenerator;

import static gov.lanl.nisac.fragility.lpnorm.PoleConstants.*;
import static javax.json.Json.createArrayBuilder;


/**
 * Created by 301338 on 8/2/2017.
 */
public class RDTWork {

    private static String windFieldPathLocation;
    private static FragilityCommandLineParser clp;
    private int numberOfScenarios = 1;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static HashMap<String, List<Double>> nodeLocation;
    private static Map<String, Object> config = new HashMap<String, Object>();
    private static JsonBuilderFactory factory;
    private static JsonObjectBuilder distributionPoles;
    private static JsonArrayBuilder polesArray;
    private static JsonArrayBuilder hazardFieldsArray;
    private static JsonArrayBuilder responseEstimatorsArray;
    private static JsonArrayBuilder scenarioArray;
    private static JsonNode newPolesJSON;
    private static JsonNode rdtNodes;
    private static String poleOutput = null;
    private String rdtScenarioFile = null;

    public RDTWork(String rdtDataFilePath, FragilityCommandLineParser cmdLine) {

        clp = cmdLine;
        windFieldPathLocation = clp.getWindFieldInputPath();
        rdtScenarioFile = clp.getRdtOutputPath();
        numberOfScenarios = clp.getNumberOfScenarios();

        System.out.println("RDT data option enabled ...");
        initiateProcedure();
        processData(rdtDataFilePath);
        
        // create the distribution poles
        poleOutput = clp.getPolesOutputPath();
        generatePoleData();
    }

    private static void setRdtNodes(JsonNode rdtNodes) {
        RDTWork.rdtNodes = rdtNodes;
    }

    private void processData(String rdtDataFilePath) {
        inferPoleData(rdtDataFilePath);
        distributionPoles.add("assets", polesArray);

        createHazardFields();
        distributionPoles.add("hazardFields", hazardFieldsArray);

        createResponseEstimator();
        distributionPoles.add("responseEstimators", responseEstimatorsArray);

    }

    private static void initiateProcedure() {
        config.put(JsonGenerator.PRETTY_PRINTING, true);
        factory = Json.createBuilderFactory(config);

        distributionPoles = factory.createObjectBuilder();
        polesArray = createArrayBuilder();
        hazardFieldsArray = createArrayBuilder();
        responseEstimatorsArray = createArrayBuilder();

        scenarioArray = createArrayBuilder();
    }

    private static void inferPoleData(String filePath) {
        InputStream inStream;
        nodeLocation = new HashMap<>();
        double xValue, yValue, ndist, numPoles;
        double v1, v2, x0, y0;
        String node1, node2, sid;
        int id_count = 0;

        try {
            inStream = new FileInputStream(filePath);
            setRdtNodes(objectMapper.readTree(inStream));

            for (JsonNode n : rdtNodes.findValue("buses")) {
                sid = String.valueOf(n.get("id").asText());
                xValue = n.get("x").doubleValue();
                yValue = n.get("y").doubleValue();

                nodeLocation.put(sid, new ArrayList<Double>());
                nodeLocation.get(sid).add(xValue);
                nodeLocation.get(sid).add(yValue);

            }

            // create poles for each line segment
            for (JsonNode n : rdtNodes.findValue("lines")) {

                // checking to make sure NOT a transformer
                boolean xfmrExist = false;
                boolean isnew = false;
                JsonNode xmfValue = n.findValue("is_transformer");
                JsonNode isnewValue = n.findValue("is_new");

                if (xmfValue != null){
                    xfmrExist = n.get("is_transformer").asBoolean();
                }
                if (isnewValue != null){
                    isnew = n.get("is_new").asBoolean();
                }

                
                if (!xfmrExist && !isnew) {

                    sid = String.valueOf(n.get("id").asText());
                    node1 = String.valueOf(n.get("node1_id").asText());
                    node2 = String.valueOf(n.get("node2_id").asText());

                    if (nodeLocation.containsKey(node1) && nodeLocation.containsKey(node2)) {
                        x0 = nodeLocation.get(node1).get(0);
                        y0 = nodeLocation.get(node1).get(1);


                        // get lat/lon values
                        v1 = nodeLocation.get(node2).get(0) - nodeLocation.get(node1).get(0);
                        v2 = nodeLocation.get(node2).get(1) - nodeLocation.get(node1).get(1);

                        ndist = Math.sqrt(v1 * v1 + v2 * v2);

                        // v = (x1,y1) - (x2,y2)
                        // normalized vector u = v  / ||v||
                        // now point distance is along line (x1,y1) + du =
                        v1 = v1 / ndist;
                        v2 = v2 / ndist;

                        // using 111.111 km per degree (or 111,111 meters)
                        // pole spacing at 91 meters 0.000817463169242 degrees
                        // 0.000817463169242
                        numPoles = Math.floor(ndist * DEG_TO_METERS) / POLE_SPACING;
                        numPoles = Math.floor(numPoles);

                        if (numPoles < 1) {
                            System.out.println(" distance between nodes: " + node1 + " & " + node2 + " is 0.0");
                            System.out.println(" -- NO POLES created for line: " + sid);
                        }

                        for (int i = 0; i < numPoles; i++) {
                            createPoleAsset(id_count, sid, new double[]{x0, y0});

                            // move to next pole location
                            x0 = x0 + v1 * NEXT_DISTANCE;
                            y0 = y0 + v2 * NEXT_DISTANCE;
                            id_count = id_count + 1;
                        }

                    } else {
                        System.out.println(node1 + " and/or " + node2 + " doesn't exist for line id " + sid + "");
                    }

                } // if - checked if transformer

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Number of poles created: " + id_count);
    }


    private static void createPoleAsset(int id, String line, double[] coord) {

        JsonObject poleJson = factory.createObjectBuilder()
                .add("assetClass", ASSET_CLASS)
                .add("assetGeometry", factory.createObjectBuilder()
                        .add("coordinates", factory.createArrayBuilder()
                                .add(coord[0])
                                .add(coord[1]))
                        .add("type", GEOMETRY_TYPE))
                .add("id", String.valueOf(id))
                .add("properties", factory.createObjectBuilder()
                        .add("baseDiameter", BASE_DIAMETER)
                        .add("cableSpan", CABLE_SPAN)
                        .add("commAttachmentHeight", COMM_ATTACHMENT_HEIGHT)
                        .add("commCableDiameter", COMM_CABLE_DIAMETER)
                        .add("commCableNumber", COMM_CABLE_NUMBER)
                        .add("commCableWireDensity", COMM_CABLE_WIRE_DENSITY)
                        .add("height", HEIGHT)
                        .add("meanPoleStrength", MEAN_POLE_STRENGTH)
                        .add("powerAttachmentHeight", POWER_ATTACHMENT_HEIGHT)
                        .add("powerCableDiameter", POWER_CABLE_DIAMETER)
                        .add("powerCableNumber", POWER_CABLE_NUMBER)
                        .add("powerCableWireDensity", POWER_CABLE_WIRE_DENSITY)
                        .add("powerCircuitName", POWER_CIRCUIT_NAME)
                        .add("stdDevPoleStrength", STD_DEV_POLE_STRENGTH)
                        .add("topDiameter", TOP_DIAMETER)
                        .add("woodDensity", WOOD_DENSITY)
                        .add("lineId", line)
                ).build();

        polesArray.add(poleJson);

    }

    private static void createHazardFields() {

        String file = windFieldPathLocation;

        JsonObject hazardField = factory.createObjectBuilder()
                .add("id", HAZ_ID)
                .add("hazardQuantityType", HAZARD_QUANTITY_TYPE)
                .add("rasterFieldData", factory.createObjectBuilder()
                        .add("file", file)
                        .add("gridFormat", HAZ_GRID_FORMAT)
                        .add("crsCode", HAZ_CRS_CODE)
                        .add("nBands", HAZ_NUMBER_OF_BANDS)
                        .add("rasterBand", HAZ_RASTER_BAND)
                        .add("valueType", HAZ_VALUE_TYPE)
                ).build();

        hazardFieldsArray.add(hazardField);
    }

    private static void createResponseEstimator() {

        JsonObject responseEstimators = factory.createObjectBuilder()
                .add("id", RSP_ID)
                .add("responseEstimatorClass", RSP_ESTIMATOR_CLASS)
                .add("assetClass", RSP_ASSET_CLASS)
                .add("hazardQuantityTypes", factory.createArrayBuilder().add(RSP_HAZ_QUANTITY_TYPE))
                .add("responseQuantityType", RSP_QUANTITY_TYPE)
                .add("properties", factory.createObjectBuilder()
                ).build();

        responseEstimatorsArray.add(responseEstimators);

    }

    private static void createScenarioBlock(List<String> lineIds, int id) {

        JsonArrayBuilder disabledLines = factory.createArrayBuilder();

        for (String s : lineIds) {
            disabledLines.add(s);
        }

        JsonObject scenarioBlock = factory.createObjectBuilder()
                .add("id", String.valueOf(id))
                .add("hardened_disabled_lines", factory.createArrayBuilder())
                .add("disable_lines", disabledLines)
                .build();

        scenarioArray.add(scenarioBlock);

    }

    private static void generatePoleData() {

        String jsonString = distributionPoles.build().toString();

        try  {
            Object json = objectMapper.readValue(jsonString, Object.class);
            setNewPoles(objectMapper.readTree(jsonString));            

            if (poleOutput != null) {
            	FileWriter file = new FileWriter(poleOutput);
            	objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, json);
            	file.close();
            }

        } 
        catch (IOException e) {
            System.out.println("Couldn't write Pole Assets to JSON file.");
            e.printStackTrace();
        }

    }

    public static JsonNode getNewPoles() {
        return newPolesJSON;
    }

    private static void setNewPoles(JsonNode newPoles) {
        RDTWork.newPolesJSON = newPoles;
    }

    public void generateScenarios(List<IResponse> rspData) {
        int timeCt = 0;
        List<String> lineIds;
        Random r = new Random();
        String uniqueId, lineId, keyId;

        HashMap<String, String> nodesToLines = new HashMap<>();
        JsonNode poleAssets = getNewPoles();
        JsonNode poleAssetList = poleAssets.findValue("assets");

        for (JsonNode n : poleAssetList) {

            keyId = n.get("id").asText();
            JsonNode properities = n.findValue("properties");
            lineId = properities.get("lineId").asText();

            if (!nodesToLines.containsKey(keyId)) {
                nodesToLines.put(keyId, lineId);
            } else {
                System.out.print("- duplicate id : " + keyId);
            }
        }

        // generating scenarios
        do {
            lineIds = new ArrayList<>();

            for (IResponse rd : rspData) {
                // if response > rand(0,1) --> line is disabled
                if (rd.getValue() > r.nextFloat()) {

                    uniqueId = nodesToLines.get(rd.getAssetID());

                    if (!lineIds.contains(uniqueId)) lineIds.add(uniqueId);
                }
            }
            createScenarioBlock(lineIds, timeCt);
            timeCt += 1;

        } while (timeCt < numberOfScenarios);

        // create new RDT input
        ObjectNode nodes = createScenarioBLock();
        writeLpnorm(rdtScenarioFile, nodes);
    }

    private static ObjectNode createScenarioBLock() {

        ObjectNode nodes = rdtNodes.deepCopy();
        String scenarioString = scenarioArray.build().toString();
        // write out original RDT input with new scenario data
        try {
            JsonNode parsedJson = objectMapper.readTree(scenarioString);
            ObjectNode outerObject = objectMapper.createObjectNode();
            outerObject.putPOJO("scenarios", parsedJson);
            nodes.putArray("scenarios");
            nodes.putPOJO("scenarios", outerObject.findValue("scenarios"));
        } catch (IOException e) {
            System.out.println("Could not write scenario block.");
            e.printStackTrace();
        }

        return nodes;
    }

    private static void writeLpnorm(String fileName, Object obj) {

        System.out.println("-=-=-=-=-=" + fileName);
        try {
            FileOutputStream os = new FileOutputStream(fileName);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(os, obj);
            os.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("RDT JSON processing issue.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not write RDT input.");
        }

    }
}

