package gov.lanl.nisac.fragility.lpnorm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.lanl.nisac.fragility.FragilityCommandLineParser;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class RDTWorkTest {

    static String FILE_NAME = "RDT-to-Poles.json";

    @Test
    public void generateScenarios() throws Exception {

        String[] cmds = new String[4];

        cmds[0] = "-r";
        cmds[1] = "test_data/rdt_1.json";
        cmds[2] = "-wf";
        cmds[3] = "test_data/windField_example.asc";

        System.out.println("go...");
        FragilityCommandLineParser cmdLine = new FragilityCommandLineParser(cmds);
        System.out.println(cmdLine.isHasWindField());

        new RDTWork("test_data/rdt_1.json", cmdLine);

        File f = new File(FILE_NAME);

        // file has been written
        assert (f.exists());

        JsonNode n = readJSON(FILE_NAME);
        JsonNode assetNodes = n.get("assets");

        // correct number of poles created
        assert (assetNodes.size() == 2856);

        TimeUnit.SECONDS.sleep(1);

        f.delete();
    }

    public JsonNode readJSON(String filePath) {
        InputStream inStream;
        JsonNode jsonNode = null;
        final ObjectMapper objectMapper = new ObjectMapper();

        try {
            inStream = new FileInputStream(filePath);
            jsonNode = objectMapper.readTree(inStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonNode;
    }

}