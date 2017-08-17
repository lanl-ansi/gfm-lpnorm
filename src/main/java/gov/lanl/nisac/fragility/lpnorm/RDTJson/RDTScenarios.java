package gov.lanl.nisac.fragility.lpnorm.RDTJson;

import java.util.ArrayList;
import java.util.List;

public class RDTScenarios {

	private String id;
	private List<String> hardened_disabled_lines;
	private List<String> disable_lines;

	public RDTScenarios() {
		hardened_disabled_lines = new ArrayList<>();
	}

	public List<String> getHardened_disabled_lines() {
		return hardened_disabled_lines;
	}

	public void setHardened_disabled_lines(List<String> hardened_disabled_lines) {
		this.hardened_disabled_lines = hardened_disabled_lines;
	}

	public List<String> getDisable_lines() {
		return disable_lines;
	}


	public void setDisable_lines(List<String> disable_lines) {
		this.disable_lines = disable_lines;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
