package gov.lanl.nisac.fragility.io.validators;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingReport;

public class JacksonJSONSchemaValidator extends JSONSchemaValidator {
	
	private JsonSchema schema;
	private ObjectMapper mapper;

	public JacksonJSONSchemaValidator(URL schemaURL, boolean proxyRequired) {
		super(schemaURL, proxyRequired);
		mapper = new ObjectMapper();
		try {
			JsonNode schemaRoot = mapper.readTree(schemaInput);
			JsonSchemaFactory jsfact = JsonSchemaFactory.byDefault();
			schema = jsfact.getJsonSchema(schemaRoot);
		} catch (IOException | ProcessingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public JSONSchemaValidatorReport validate(InputStream jsonData) {
		JsonNode dataRoot;
		ProcessingReport report;
		JSONSchemaValidatorReport validatorReport;
		try {
			dataRoot = mapper.readTree(jsonData);
			report = schema.validate(dataRoot);
			validatorReport = new JSONSchemaValidatorReport(report);
		} catch (IOException|ProcessingException e) {
			validatorReport = new JSONSchemaValidatorReport(e);
		}
		return validatorReport;
	}
}
