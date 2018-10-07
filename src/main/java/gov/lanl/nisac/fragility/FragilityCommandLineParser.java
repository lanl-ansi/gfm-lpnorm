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

    private String rdtInputPath = null;
  //  private String polesInputPath = null;
    private String windFieldInputPath = null;
    private String rdtOutputPath = null;
    private String polesOutputPath = null;
    private String responseEstimatorOutputPath = null;
    private String fragilityExposureOutputPath = null;

    private int numberOfScenarios = 1;

    public static final String WIND_FIELD_FLAG = "wf";
    public static final String RDT_INPUT_FLAG = "r";
    public static final String RDT_OUTPUT_FLAG = "o";
    public static final String POLES_OUTPUT_FLAG = "po";
    public static final String RESPONSE_ESTIMATOR_OUTPUT_FLAG = "reo";
    public static final String FRAGILITY_EXPOSURE_OUTPUT_FLAG = "feo";    
    public static final String NUM_SCENARIOS_FLAG = "num";

    public FragilityCommandLineParser(String[] args) {
        defineOptions();
        parse(args);
    }

    private static Options defineOptions() {
        options = new Options();
        options.addOption(new Option("h", "help", false, "print fragility help"));
        options.addOption(new Option(FRAGILITY_EXPOSURE_OUTPUT_FLAG, "exposure_output", true, "[optional] fragility exposure output path"));
        options.addOption(new Option(RDT_INPUT_FLAG, "rdt", true, "RDTJson input path"));
//        options.addOption(new Option("p", "poles input", true, "pole JSON input"));
        options.addOption(new Option(POLES_OUTPUT_FLAG, "poles_output", true, "[optional] pole data output path"));
        options.addOption(new Option(RESPONSE_ESTIMATOR_OUTPUT_FLAG, "response_estimator_output", true, "[optional] response estimator output path"));
        options.addOption(new Option(WIND_FIELD_FLAG, "windField", true, "Wind field Esri Ascii input path"));
        options.addOption(new Option(RDT_OUTPUT_FLAG, "output", true, "damage output file path"));
        options.addOption(new Option(NUM_SCENARIOS_FLAG, "numScenario", true, "[optional] number of scenarios, default is 1"));
        return options;
    }

    public static void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        StringWriter swriter = new StringWriter();
        PrintWriter writer = new PrintWriter(swriter);
        String header = "gfm-lpnorm  [OPTIONS]\n options:\n";
        String footer =
                "\nExamples:\n\njava -jar gfm-lpnorm.jar  -wf <RDT_input_path.asc> -r <RDT_input_path.json> -o <output.json>\n\n" +
                		"java -jar gfm-lpnorm.jar  -wf <RDT_input_path.asc> -r <RDT_input_path.json> -o <output.json> -num 5 \n\n";
        formatter.printHelp("gfm-lpnorm", header, options, footer, true);
        
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

            // lpnorm --
            if (commandLine.hasOption("rdt")) {
                rdtInputPath = commandLine.getOptionValue("rdt");
            }

            if (commandLine.hasOption("num")) {
                numberOfScenarios = Integer.parseInt(commandLine.getOptionValue("num"));
            }

            if (commandLine.hasOption("output")) {
                rdtOutputPath = commandLine.getOptionValue("output");
            }

    //        if (commandLine.hasOption("poles input")) {
      //          polesInputPath = commandLine.getOptionValue("poles input");
        //    }
            
            if (commandLine.hasOption("poles_output")) {
                polesOutputPath = commandLine.getOptionValue("poles_output");
            }
            
            if (commandLine.hasOption("response_estimator_output")) {
                responseEstimatorOutputPath = commandLine.getOptionValue("response_estimator_output");
            }
            
            if (commandLine.hasOption("exposure_output")) {
                fragilityExposureOutputPath = commandLine.getOptionValue("exposure_output");
            }

            if (commandLine.hasOption("windField")) {
                windFieldInputPath = commandLine.getOptionValue("windField");
            }

        } catch (ParseException exp) {
            printUsage();
            System.exit(1);
        }
    }

    public static Options getOptions() {
        return options;
    }

    public String getRdtInputPath() {
        return rdtInputPath;
    }

//    public String getPolesInputPath() {
  //      return polesInputPath;
   // }
    
    public String getPolesOutputPath() {
        return polesOutputPath;
    }
    
    public String getResponseEstimatorOutputPath() {
        return responseEstimatorOutputPath;
    }
    
    public String getFragilityExposureOutputPath() {
        return fragilityExposureOutputPath;
    }

    public String getWindFieldInputPath() {
        return windFieldInputPath;
    }
    
    public int getNumberOfScenarios() {
        return numberOfScenarios;
    }


    public String getRdtOutputPath() {
        return rdtOutputPath;
    }
}
