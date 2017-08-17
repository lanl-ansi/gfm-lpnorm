package gov.lanl.nisac.fragility.lpnorm;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import gov.lanl.nisac.fragility.assets.IAsset;
import gov.lanl.nisac.fragility.io.*;
import gov.lanl.nisac.fragility.lpnorm.PoleJson.*;
import gov.lanl.nisac.fragility.lpnorm.RDTJson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.lanl.nisac.fragility.responseModels.IResponse;

/**
 * Created by 301338 on 8/2/2017.
 */
class RDTWork {

    private static RDTData rdt;
    private static String windFieldPathLocation;
    private static FragilityCommandLineParser clp;
    private int numberOfScenarios = 1;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final JsonFactory jsonFactory = new JsonFactory();
    private static PoleData polesData;
    private static final String RDT_TO_POLES = "RDT-to-Poles.json";

    private AssetDataStore assetDataStore;


    RDTWork(String filePath, FragilityCommandLineParser cmdLine) {
        System.out.println("RDT data option enabled ...");
        clp = cmdLine;
        windFieldPathLocation = clp.getWindFieldInputPath();

        if (clp.isNumScenarios()){
            numberOfScenarios = clp.getNumberOfScenarios();
        }

        readRDT(filePath);
        inferPoleData();
        defineHazardResponseEstimator();
        writePoleData(RDT_TO_POLES);
    }

    private static void readRDT(String filePath) {

        try {
            rdt = objectMapper.readValue(new File(filePath), RDTData.class);
        } catch (IOException e) {
            System.out.println("Could not read template RDT input file");
            e.printStackTrace();
        }

    }

    private static PoleAssets definePole(int id, String line, double[] coord) {

        PoleAssets pole = new PoleAssets();
        PoleProperties poleProperty = new PoleProperties();

        pole.setAssetGeometry(new PoleAssetGeometry());
        pole.getAssetGeometry().setCoordinates(coord);
        pole.getAssetGeometry().setType("Point");
        pole.setAssetClass("PowerDistributionPole");
        pole.setId(String.valueOf(id));

        pole.setProperties(poleProperty);
        poleProperty.setLineId(line);
        poleProperty.setPowerCircuitName(String.valueOf(id));

        return pole;
    }

    private void inferPoleData() {

        System.out.println("Producing generic pole data ...");
        this.assetDataStore = new AssetDataStore();

        List<RDTLines> rdtline = rdt.getLines();
        HashMap<String, String> nodeToLine = new HashMap<>();

        for (RDTLines lid : rdtline) {
            nodeToLine.put(lid.getNode1_id(), lid.getId());
            nodeToLine.put(lid.getNode2_id(), lid.getId());
        }

        List<RDTBuses> rdtbuses = rdt.getBuses();
        List<PoleAssets> assets = new ArrayList<>();
        PoleAssets npa;

        HashMap<String, List<Double>> ht = new HashMap<>();
        int id_count = 0;
        String bid;

        // defining a pole at each bus location
        for (RDTBuses bus : rdtbuses) {
            bid = bus.getId();
            ht.put(bid, new ArrayList<Double>());
            // Double
            ht.get(bid).add(bus.getX());
            ht.get(bid).add(bus.getY());

            try {

                String busId = nodeToLine.get(bus.getId());
                npa = definePole(id_count, busId, new double[]{bus.getX(), bus.getY()});
                assets.add(npa);
                String npastr = objectMapper.writeValueAsString(npa);
                JsonParser jp = jsonFactory.createParser(npastr);
                AssetData aData = objectMapper.readValue(jp, AssetData.class);
                IAsset asset = aData.createAsset();
                assetDataStore.addAsset(asset);

            } catch (IOException e) {
                e.printStackTrace();
            }
            id_count += 1;
        }// end for

        // ------- now adding poles between buses -------
        String node1;
        String node2;
        String lid;
        double v1, v2, x0, y0;
        double ndist, numPoles;


        for (RDTLines line : rdtline) {
            node1 = line.getNode1_id();
            node2 = line.getNode2_id();
            x0 = ht.get(node1).get(0);
            y0 = ht.get(node1).get(1);

            v1 = ht.get(node2).get(0) - ht.get(node1).get(0);
            v2 = ht.get(node2).get(1) - ht.get(node1).get(1);
            ndist = Math.sqrt(v1 * v1 + v2 * v2);

            // normalized vector
            v1 = v1 / ndist;
            v2 = v2 / ndist;

            // using 111.111 km per degree or 111,111 meters
            // pole spacing at 91 meters 0.000817463169242 degrees
            // 0.000817463169242
            numPoles = Math.floor(ndist * 111111) / 91.0;
            numPoles = Math.floor(numPoles);

            for (int i = 0; i < numPoles; i++) {
                lid = line.getId();

                try {
                    npa = definePole(id_count, lid, new double[]{x0, y0});
                    assets.add(npa);

                    String npastr = objectMapper.writeValueAsString(npa);
                    JsonParser jp = jsonFactory.createParser(npastr);

                    AssetData aData = objectMapper.readValue(jp, AssetData.class);
                    IAsset asset = aData.createAsset();
                    assetDataStore.addAsset(asset);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // move next pole location
                x0 = x0 + v1 * 0.000817463169242;
                y0 = y0 + v2 * 0.000817463169242;
                id_count = id_count + 1;

            }
            // spacing at 91 meters ~ 300 ft
        }// end for

        System.out.println("Number of poles created: " + assets.size());
        polesData = new PoleData();
        polesData.setAssets(assets);

    }

    private static void defineHazardResponseEstimator() {

        String urlpath = null;
        try {
            urlpath = String.valueOf(new File(System.getProperty("user.dir")).toURI().toURL());
            urlpath = urlpath.substring(6);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        urlpath = urlpath + windFieldPathLocation;

        List<PoleHazardFields> poleHazardFieldList = new ArrayList<>();
        PoleHazardFields phf = new PoleHazardFields();
        phf.setRasterFieldData(new PoleRasterFieldData());
        phf.getRasterFieldData().setCrsCode("EPSG:4326");
        phf.getRasterFieldData().setGridFormat("ArcGrid");
        phf.getRasterFieldData().setnBands(1);
        phf.getRasterFieldData().setRasterBand(1);
        phf.getRasterFieldData().setValueType("double");
        phf.getRasterFieldData().setUri("file:///" + urlpath);
        phf.setId("Fragility-generated-ID");

        List<PoleResponseEstimators> poleRespEstList = new ArrayList<>();
        PoleResponseEstimators pr = new PoleResponseEstimators();
        pr.setId("PowerPoleWindStressEstimator");
        pr.setResponseEstimatorClass("PowerPoleWindStressEstimator");
        pr.setAssetClass("PowerDistributionPole");
        pr.setProperties(new PoleResponseEstimatorsProperties());

        List<String> ls = new ArrayList<>();
        ls.add("Windspeed");
        pr.setHazardQuantityTypes(ls);
        pr.setResponseQuantityType("DamageProbability");
        poleRespEstList.add(pr);

        phf.setHazardQuantityType("Windspeed");
        poleHazardFieldList.add(phf);
        polesData.setResponseEstimators(poleRespEstList);
        polesData.setHazardFields(poleHazardFieldList);

        // Fragility's hazard class
        HazardFieldData hdf = new HazardFieldData();
        hdf.setRasterFieldData(new RasterFieldData());
        hdf.getRasterFieldData().setUri("file:///" + urlpath);
        hdf.getRasterFieldData().setnBands(1);
        hdf.getRasterFieldData().setRasterBand(1);
        hdf.getRasterFieldData().setCrsCode("EPSG:4326");
        hdf.setHazardQuantityType("Windspeed");
        hdf.getRasterFieldData().setGridFormat("ArcGrid");
        hdf.getRasterFieldData().setValueType(Double.class);
        hdf.setId("Fragility-TesGrid");

    }

    private static void writePoleData(String fileName) {

        try {
            FileOutputStream os = new FileOutputStream(fileName);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(os, polesData);
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

    void generateScenarios(List<IResponse> rspData) {
        int timeCt = 0;
        RDTScenarios scene;
        List<String> lineIds;
        Random r = new Random();
        List<RDTScenarios> lscenario = new ArrayList<>();

        String uniqueId;
        String damagedLine;

        HashMap<String, String> poleToLine = new HashMap<>();
        HashMap<List<String>, String> nodesmap = new HashMap<>();
        List<String> ns;


        for(RDTLines line: rdt.getLines()){
            ns = new ArrayList<>();
            ns.add(line.getId());
            nodesmap.put(ns, line.getId());
        }

        for (PoleAssets pa : polesData.getAssets()) {
            poleToLine.put(pa.getId(), pa.getProperties().getLineId());
        }

        do {

            lineIds = new ArrayList<>();
            scene = new RDTScenarios();

            for (IResponse rd : rspData) {

                // if response > rand(0,1) --> line is disabled
                if (rd.getValue() > r.nextFloat()) {

                    uniqueId = poleToLine.get(rd.getAssetID());

                    ns = new ArrayList<>();
                    ns.add(uniqueId); // line id
                    damagedLine = nodesmap.get(ns);

                    if (!lineIds.contains(damagedLine)) {
                        // collect damaged lines
                        lineIds.add(damagedLine);
                    }
                }
            } // end for loop

            scene.setId(String.valueOf(timeCt));
            scene.setDisable_lines(new ArrayList<>(lineIds));
            lscenario.add(scene);
            timeCt += 1;

        } while (timeCt < numberOfScenarios);

        if (clp.isScenarioBlock()){
            RDTScenarioBlock rsb = new RDTScenarioBlock();
            rsb.setScenarios(lscenario);
            writeLpnorm("RDTScenarioBlock.json", rsb);
        }
        else{
            rdt.setScenarios(lscenario);
            writeLpnorm("RDT_scenario.json", rdt);
        }

    }

    private static void writeLpnorm(String fileName, Object obj) {

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

    PoleData getPoleData(){
        return polesData;
    }

}

