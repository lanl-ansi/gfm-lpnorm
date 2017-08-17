package gov.lanl.nisac.fragility.io.validators;

import gov.lanl.nisac.fragility.config.ProxySettings;

import java.io.InputStream;
import java.net.URL;

public abstract class JSONSchemaValidator {
	
	protected InputStream schemaInput;
	protected boolean proxyRequired;

	public JSONSchemaValidator(URL schemaURL, boolean proxyRequired) {
		try {
			this.proxyRequired = proxyRequired;
			if(proxyRequired){
				ProxySettings.setProxy();
			}
			schemaInput = schemaURL.openStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract JSONSchemaValidatorReport validate(InputStream jsonData);

}
