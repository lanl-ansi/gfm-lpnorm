package gov.lanl.nisac.fragility.test.io.validators;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.lanl.nisac.fragility.io.validators.JSONSchemaValidator;
import gov.lanl.nisac.fragility.io.validators.JSONSchemaValidatorReport;
import gov.lanl.nisac.fragility.io.validators.JacksonJSONSchemaValidator;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.junit.Test;

public class JacksonJSONSchemaValidatorTest {

	@Test
	public void test() {

		// File to validate.
		String infile = "src/test/resources/GFM_schema_test_example.json";
		// Schema file.
		String schemaURLString = "https://lanl-ansi.github.io/micot-fragility/schemas/GFM_schema_1.2.json";
		try {
			URL schemaURL = new URL(schemaURLString);
			JSONSchemaValidator validator = new JacksonJSONSchemaValidator(schemaURL,true);
			InputStream instream = new FileInputStream(infile);
			JSONSchemaValidatorReport report = validator.validate(instream);
			List<String> messages = report.getMessages();
			System.out.println("Success = "+report.isSuccess());
			for(String m : messages){
				System.out.println("  "+m);
			}
			assertTrue(report.isSuccess());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test failed with exception "+e.getMessage());
		}

		
		
	}

}
