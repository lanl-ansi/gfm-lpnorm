package gov.lanl.nisac.fragility.io.validators;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingMessage;
import com.github.fge.jsonschema.report.ProcessingReport;

public class JSONSchemaValidationExample {

	public static void main(String[] args) {
		// File to validate.
		String infile = "src/main/java/gov/lanl/nisac/fragility/io/adHoc/GFM_schema_example.json";
		// Schema file.
		String schemaURL = "https://lanl-ansi.github.io/micot-fragility/schemas/GFM_schema_1.2.json";
		try {
			// Proxy settings:
			System.setProperty("http.proxyHost", "proxyout.lanl.gov");
			System.setProperty("http.proxyPort", "8080");
			System.setProperty("https.proxyHost", "proxyout.lanl.gov");
			System.setProperty("https.proxyPort", "8080");

			// Validation test:
			InputStream schemaStream = new URL(schemaURL).openStream();
			InputStream inputStream = new FileInputStream(infile);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode dataRoot = mapper.readTree(inputStream);
			JsonNode schemaRoot = mapper.readTree(schemaStream);
			JsonSchemaFactory jsfact = JsonSchemaFactory.byDefault();
			JsonSchema schema = jsfact.getJsonSchema(schemaRoot);
			ProcessingReport report = schema.validate(dataRoot);
			System.out.println("Validation successful:"+report.isSuccess());
			System.out.println(report);
			System.out.println("Messages:");
			Iterator<ProcessingMessage> iter = report.iterator();
			while(iter.hasNext()){
				System.out.println("  "+iter.next());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ProcessingException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}



	}

}
