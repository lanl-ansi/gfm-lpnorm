package gov.lanl.nisac.fragility;

import gov.lanl.nisac.fragility.assets.IAsset;
import gov.lanl.nisac.fragility.core.IProperty;
import gov.lanl.nisac.fragility.core.Properties;
import gov.lanl.nisac.fragility.core.Property;
import gov.lanl.nisac.fragility.engine.DefaultExposureEngine;
import gov.lanl.nisac.fragility.engine.DefaultFragilityEngine;
import gov.lanl.nisac.fragility.engine.FragilityEngine;
import gov.lanl.nisac.fragility.engine.IExposureEngine;
import gov.lanl.nisac.fragility.exposure.IExposure;
import gov.lanl.nisac.fragility.exposure.IExposureEvaluator;
import gov.lanl.nisac.fragility.exposure.PointExposureEvaluator;
import gov.lanl.nisac.fragility.gis.Feature;
import gov.lanl.nisac.fragility.gis.RasterField;
import gov.lanl.nisac.fragility.hazards.HazardField;
import gov.lanl.nisac.fragility.hazards.IHazardField;
import gov.lanl.nisac.fragility.io.AssetData;
import gov.lanl.nisac.fragility.io.AssetDataStore;
import gov.lanl.nisac.fragility.io.ExposureData;
import gov.lanl.nisac.fragility.io.HazardFieldData;
import gov.lanl.nisac.fragility.io.HazardFieldDataStore;
import gov.lanl.nisac.fragility.io.RasterDataFieldFactory;
import gov.lanl.nisac.fragility.io.RasterFieldData;
import gov.lanl.nisac.fragility.io.ResponseData;
import gov.lanl.nisac.fragility.io.ResponseEstimatorData;
import gov.lanl.nisac.fragility.io.ResponseEstimatorDataStore;
import gov.lanl.nisac.fragility.io.validators.JSONSchemaValidator;
import gov.lanl.nisac.fragility.io.validators.JSONSchemaValidatorReport;
import gov.lanl.nisac.fragility.io.validators.JacksonJSONSchemaValidator;
import gov.lanl.nisac.fragility.lpnorm.RDTWork;
import gov.lanl.nisac.fragility.responseEstimators.AbstractResponseEstimator;
import gov.lanl.nisac.fragility.responseEstimators.ResponseEstimatorFactory;
import gov.lanl.nisac.fragility.responseModels.IResponse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A main class for the Fragility framework. The class reads the input and builds the data objects.
 * It then delegates execution to an instance of a class that implements FragilityEngine. This allows
 * customization of how models are executed within the framework.
 */

public class Fragility {

    private static FragilityEngine responseEngine;
    private static AssetDataStore assetDataStore;
    private static HazardFieldDataStore hazardFieldDataStore;
    private static ResponseEstimatorDataStore responseEstimatorDataStore;
    private static List<IResponse> responses = new ArrayList<>();
    private static List<IExposure> exposures = new ArrayList<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final JsonFactory jsonFactory = new JsonFactory();
    private static final ResponseEstimatorFactory estimatorFactory = new ResponseEstimatorFactory();
    private static String schemaURI = null;
    private static boolean validateInput = false;
    private static String fragilityRSPPath = null;
    private static String fragilityExposurePath = null;
    
    private static FragilityCommandLineParser parser;

    public Fragility() {
    }

    public static void main(String[] args) {
        checkOptions(args);
        System.out.println("Analysis complete.");
    }

    private static void checkOptions(String[] args) {
        // Parse the command line.
        parser = new FragilityCommandLineParser(args);
        RDTWork rdtwork = null;

        // Program 1 - The standard GFM to RDT program
//        if (parser.isHasRdt()) {

            if (parser.getWindFieldInputPath() == null) {
                System.out.println("Missing the hazard field option -" + FragilityCommandLineParser.WIND_FIELD_FLAG + " ...");
                System.exit(0);
            }
            
            if (parser.getRdtInputPath() == null) {
                System.out.println("Missing the power system input file (RDT format) option -" + FragilityCommandLineParser.RDT_INPUT_FLAG + " ...");
                System.exit(0);
            }

            if (parser.getRdtOutputPath() == null) {
                System.out.println("Missing the RDT output file option -" + FragilityCommandLineParser.RDT_OUTPUT_FLAG + " ...");
                System.exit(0);
            }            
            
            fragilityRSPPath = parser.getResponseEstimatorOutputPath();
            fragilityExposurePath = parser.getFragilityExposureOutputPath();

            
            String rdtFilePath = parser.getRdtInputPath();
            rdtwork = new RDTWork(rdtFilePath, parser);
            JsonNode allNodes = rdtwork.getNewPoles();

            if (allNodes.get("assets").size() > 1){
                runFragilityPoles(allNodes);
                run();
                generateResults();
                // produce scenarios TODO
                rdtwork.generateScenarios(responses);
                // should probably exit here
                
                
            }
            else{
                System.out.println(" No assets were created...terminating");
                System.exit(4);
            }
        //} 
        // Program 2 - Does something else
  //      else if (parser.isHasPoles()) {

    //        System.out.println("Poles only option . . .");
      //      if (parser.isHasWindField()) {
        //        System.out.println("Wind field option is not used for -p option");
        //    }
           // String poleFilePath = parser.getPolesInputPath();
       //    runFragility(poleFilePath);
           // run();
           // outputResults();
        //}
    }

    private static void runFragility(String inputFile) {

        responseEngine = new DefaultFragilityEngine();

        // Read the input data. Validate against schema if required.
        InputStream inStream;
        JSONSchemaValidator validator;

        try {
            inStream = new FileInputStream(inputFile);
            if (validateInput) {
                URL schemaURL = new URL(schemaURI);
                validator = new JacksonJSONSchemaValidator(schemaURL, true);
                JSONSchemaValidatorReport report = validator.validate(inStream);
                if (!report.isSuccess()) {
                    System.out.println(report);
                }
                // Reset input stream for reading.
                inStream = new FileInputStream(inputFile);
            }

            JsonNode root = readJSONInput(inStream);
            System.out.println("Input read successfully.");

            // Parse the asset data.
            parseAssetData(root.findValue("assets"));
            System.out.println(assetDataStore.size() + " assets stored.");

            // Parse the hazard data.
            parseHazardFields(root.findValue("hazardFields"));
            System.out.println(hazardFieldDataStore.size() + " hazard fields stored.");

            // Parse the response estimator data if present.
            JsonNode responseEstimatorRoot = root.findValue("responseEstimators");

            if (responseEstimatorRoot != null) {
                parseResponseEstimators(responseEstimatorRoot);
                System.out.println(responseEstimatorDataStore.size() + " response estimators instantiated.");
            }

        } catch (FileNotFoundException | MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private static void runFragilityPoles(JsonNode poles) {

        responseEngine = new DefaultFragilityEngine();

        // Read the PoleData data.
        try {
            String polesString = objectMapper.writeValueAsString(poles);
            JsonNode jsonNode = objectMapper.readTree(polesString);

            // Parse the asset data.
            parseAssetData(jsonNode.findValue("assets"));
            System.out.println(assetDataStore.size() + " assets stored.");

            // Parse the hazard data.
            parseHazardFields(jsonNode.findValue("hazardFields"));
            System.out.println(hazardFieldDataStore.size() + " hazard fields stored.");

            // Parse the response estimator data if present.
            JsonNode responseEstimatorRoot = jsonNode.findValue("responseEstimators");

            if (responseEstimatorRoot != null) {
                parseResponseEstimators(responseEstimatorRoot);
                System.out.println(responseEstimatorDataStore.size() + " response estimators instantiated.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void run() {
        responses = responseEngine.execute(assetDataStore, hazardFieldDataStore, responseEstimatorDataStore);

        if (fragilityExposurePath != null) {
            IExposureEngine exposureEngine = new DefaultExposureEngine();
            IExposureEvaluator exposureEvaluator = new PointExposureEvaluator();
            exposures = exposureEngine.execute(assetDataStore, hazardFieldDataStore, exposureEvaluator);
        }

    }

    private static void generateResults() {
        // Output the response data.
        generateResponseOutput();
        System.out.println(" -- Asset responses written.");

        if (fragilityExposurePath != null) {
            // Output the exposure data.
            writeJSONExposureOutput();
            System.out.println(" -- Asset exposures written.");
        }
    }

    private static JsonNode readJSONInput(InputStream inStream) {

        try {
            return objectMapper.readTree(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void parseAssetData(JsonNode node) {
        assetDataStore = new AssetDataStore();

        if (node.isArray()) {
            for (JsonNode n : node) {
                try {
                    JsonParser parser = jsonFactory.createParser(n.toString());
                    AssetData aData = objectMapper.readValue(parser, AssetData.class);
                    IAsset asset = aData.createAsset();
                    assetDataStore.addAsset(asset);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void parseHazardFields(JsonNode node) {
        hazardFieldDataStore = new HazardFieldDataStore();

        if (node.isArray()) {
            for (JsonNode n : node) {
                try {
                    JsonParser parser = jsonFactory.createParser(n.toString());
                    HazardFieldData hazardFieldData = objectMapper.readValue(parser, HazardFieldData.class);
                    String id = hazardFieldData.getId();
                    String hazardQuantityType = hazardFieldData.getHazardQuantityType();
                    RasterFieldData rasterFieldData = hazardFieldData.getRasterFieldData();
                    RasterField rasterField = RasterDataFieldFactory.createRasterField(rasterFieldData);
                    IHazardField hazardField = new HazardField(id, hazardQuantityType, rasterField);
                    hazardFieldDataStore.addHazardField(hazardField);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void parseResponseEstimators(JsonNode node) {
        responseEstimatorDataStore = new ResponseEstimatorDataStore();

        if (node.isArray()) {
            for (JsonNode n : node)
                try {
                    JsonParser parser = jsonFactory.createParser(n.toString());
                    ResponseEstimatorData responseEstimatorData =
                            objectMapper.readValue(parser, ResponseEstimatorData.class);
                    String id = responseEstimatorData.getId();
                    String responseEstimatorClass = responseEstimatorData.getResponseEstimatorClass();
                    String assetClass = responseEstimatorData.getAssetClass();
                    List<String> hazardQuantityList = new ArrayList<>();
                    String[] hazardQuantityTypes = responseEstimatorData.getHazardQuantityTypes();
                    Collections.addAll(hazardQuantityList, hazardQuantityTypes);
                    String responseQuantityType = responseEstimatorData.getResponseQuantityType();
                    Map<String, Object> dataProperties = responseEstimatorData.getProperties();

                    Properties properties = new Properties();
                    for (String key : dataProperties.keySet()) {
                        Property prop = new Property(key, dataProperties.get(key));
                        properties.addProperty(prop);
                    }
                    AbstractResponseEstimator responseEstimator =
                            (AbstractResponseEstimator) estimatorFactory.createResponseEstimator(id,
                                    responseEstimatorClass, assetClass, hazardQuantityList, responseQuantityType,
                                    properties);
                    responseEstimatorDataStore.addResponseEstimator(responseEstimator);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

    private static void writeJSONExposureOutput() {
        try {
            int nExposures = exposures.size();
            ExposureData[] exposureData = new ExposureData[nExposures];

            for (int i = 0; i < nExposures; i++) {
                IExposure exposure = exposures.get(i);
                ExposureData data = new ExposureData();
                IAsset asset = exposure.getAsset();
                data.setAssetID(asset.getID());
                data.setAssetClass(asset.getAssetClass());
                IHazardField hazardField = exposure.getHazardField();
                data.setHazardQuantityType(hazardField.getHazardQuantityType());
                Feature feature = exposure.getExposure().getFeature(0);
                IProperty exposureProperty = feature.getProperty("exposure");
                data.setValue((double) exposureProperty.getValue());
                exposureData[i] = data;
            }

            if (fragilityExposurePath != null) {
            	FileOutputStream os = new FileOutputStream(fragilityExposurePath);
            	objectMapper.writerWithDefaultPrettyPrinter().writeValue(os, exposureData);
            	os.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateResponseOutput() {
        try {
            int nResponses = responses.size();
            ResponseData[] responseData = new ResponseData[nResponses];

            for (int i = 0; i < nResponses; i++) {
                IResponse response = responses.get(i);
                ResponseData data = new ResponseData();
                data.setAssetID(response.getAssetID());
                data.setAssetClass(response.getAssetClass());
                data.setHazardQuantityType(response.getHazardQuantityType());
                data.setResponseQuantityType(response.getResponseQuantityType());
                data.setValue(response.getValue());
                responseData[i] = data;
            }
            
            if (fragilityRSPPath != null) {
            	FileOutputStream os = new FileOutputStream(fragilityRSPPath);
            	objectMapper.writerWithDefaultPrettyPrinter().writeValue(os, responseData);
            	os.close();
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
