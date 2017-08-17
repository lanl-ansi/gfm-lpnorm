package gov.lanl.nisac.fragility.lpnorm.RDTJson;

public class RDTLoads {

	private String id;
	private String node_id;
	private boolean[] has_phase;
	private float[] max_real_phase;
	private float[] max_reactive_phase;
	private boolean is_critical = false;

	public RDTLoads() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNode_id() {
		return node_id;
	}

	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}

	public boolean[] getHas_phase() {
		return has_phase;
	}

	public void setHas_phase(boolean[] has_phase) {
		this.has_phase = has_phase;
	}

	public float[] getMax_real_phase() {
		return max_real_phase;
	}

	public void setMax_real_phase(float[] max_real_phase) {
		this.max_real_phase = max_real_phase;
	}

	public float[] getMax_reactive_phase() {
		return max_reactive_phase;
	}

	public void setMax_reactive_phase(float[] max_reactive_phase) {
		this.max_reactive_phase = max_reactive_phase;
	}

	public boolean isIs_critical() {
		return is_critical;
	}

	public void setIs_critical(boolean is_critical) {
		this.is_critical = is_critical;
	}

}
