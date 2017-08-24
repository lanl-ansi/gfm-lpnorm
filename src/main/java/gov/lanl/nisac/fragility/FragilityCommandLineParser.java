package gov.lanl.nisac.fragility;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class FragilityCommandLineParser {

    private static Options options = defineOptions();
    private String inputPath = null;
    private String outputPath = null;
    private String schemaURI = null;
    private boolean validateInput = false;
    private boolean exposureOnly = false;

    private String rdtInputPath = null;
    private String polesInputPath = null;
    private String windFieldInputPath = null;
    private String outputFilePath = null;
    private boolean hasRdt = false;
    private boolean hasPoles = false;
    private boolean hasWindField = false;
    private boolean hasOutputFileName = false;
    private int numberOfScenarios;
    private boolean numScenarios = false;
    private boolean scenarioBlock = false;


    public FragilityCommandLineParser(String[] args) {
        defineOptions();
        parse(args);
    }

    private static Options defineOptions() {
        options = new Options();
        options.addOption(new Option("h", "help", false,
                "print fragility help"));
        options.addOption(new Option("s", "schema", true,
                "validate schema using JSON schema at given URI"));
        options.getOption("s").setArgName("JSON_SCHEMA_URI");
        options.addOption(new Option("e", "exposure", false,
                "evaluate exposure only"));
        options.addOption(new Option("r", "rdt", true,
                "RDTJson JSON input"));
        options.addOption(new Option("p", "poles", true,
                "pole JSON input"));
        options.addOption(new Option("wf", "windField", true,
                "Wind field Esri Ascii input"));
        options.addOption(new Option("o", "output", true,
                "output file name"));
        options.addOption(new Option("num", "numScenario", true,
                "Number of Scenarios"));
        options.addOption(new Option("sb", "scenarioBlock", false,
                "Output only RDT scenario block"));

        return options;
    }

    public static void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        StringWriter swriter = new StringWriter();
        PrintWriter writer = new PrintWriter(swriter);
        String header = "fragility  [OPTIONS]\n options:\n";
        String footer =
                "\nExamples:\n\nFragility.jar  -r <RDT_path.json> -o <output.json>\n\n" +
                        "Fragility.jar -p <Poles_input> -o <output> --schema http://org.lanl.fragility/schemas/fragilitySchema.json \n\n" +
                        "Fragility.jar -r <RDT_path> -wf <windHazard_path> -o <RDT_output> \n\n";
        System.out.println(swriter.toString());
    }

    private void parse(String[] args) {
        CommandLine commandLine;
        // Parse the command line.
        CommandLineParser parser = new DefaultParser();
        try {
            // Parse the options and return the command line object.
            commandLine = parser.parse(options, args);

            // Check to see if only help is requested.
            if (commandLine.hasOption("help")) {
                System.out.println("Fragility help:");
                printUsage();
                System.exit(0);
            }

            schemaURI = null;
            exposureOnly = false;
            if (commandLine.hasOption("schema")) {
                validateInput = true;
                schemaURI = commandLine.getOptionValue("schema");
            }
            if (commandLine.hasOption("exposure")) {
                exposureOnly = true;
            }
            // lpnorm --
            if (commandLine.hasOption("rdt")) {
                hasRdt = true;
                rdtInputPath = commandLine.getOptionValue("rdt");
            }

            if (commandLine.hasOption("num")) {
                numScenarios = true;
                numberOfScenarios = Integer.parseInt(commandLine.getOptionValue("num"));
            }

            if (commandLine.hasOption("output")) {
                hasOutputFileName = true;
                outputFilePath = commandLine.getOptionValue("output");
            }

            if (commandLine.hasOption("poles")) {
                hasPoles = true;
                polesInputPath = commandLine.getOptionValue("poles");
            }

            if (commandLine.hasOption("windField")) {
                hasWindField = true;
                windFieldInputPath = commandLine.getOptionValue("windField");
            }
            if (commandLine.hasOption("scenarioBlock")) {
                scenarioBlock = true;
            }
        } catch (ParseException exp) {
            printUsage();
            System.exit(1);
        }
    }

    public static Options getOptions() {
        return options;
    }

    public String getInputPath() {
        return inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getSchemaURI() {
        return schemaURI;
    }

    public boolean isValidateInput() {
        return validateInput;
    }

    public boolean isExposureOnly() {
        return exposureOnly;
    }

    public String getRdtInputPath() {
        return rdtInputPath;
    }

    public String getPolesInputPath() {
        return polesInputPath;
    }

    public String getWindFieldInputPath() {
        return windFieldInputPath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public boolean isHasRdt() {
        return hasRdt;
    }

    public boolean isHasPoles() {
        return hasPoles;
    }

    public boolean isHasWindField() {
        return hasWindField;
    }

    public boolean isHasOutputFileName() {
        return hasOutputFileName;
    }

    public int getNumberOfScenarios() {
        return numberOfScenarios;
    }

    public boolean isNumScenarios() {
        return numScenarios;
    }

    public boolean isScenarioBlock() {
        return scenarioBlock;
    }

    public void setScenarioBlock(boolean scenarioBlock) {
        this.scenarioBlock = scenarioBlock;
    }
}
