package gov.lanl.nisac.fragility.io.validators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.fge.jsonschema.report.ProcessingMessage;
import com.github.fge.jsonschema.report.ProcessingReport;

public class JSONSchemaValidatorReport {

	boolean isSuccess;
	List<String> messages = new ArrayList<>();
	
	public JSONSchemaValidatorReport(ProcessingReport report) {
		isSuccess = report.isSuccess();
		Iterator<ProcessingMessage> iter = report.iterator();
		while(iter.hasNext()){
			messages.add(iter.next().toString());
		}
	}
	
	public JSONSchemaValidatorReport(Exception e){
		isSuccess = false;
		messages.add(e.getMessage());
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public List<String> getMessages() {
		return messages;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Validation successful: "+isSuccess+"\n");
		if(messages.size()>0){
			for(String m:messages){
				sb.append("  "+m+"\n");
			}
		}
		return sb.toString();
	}
}
