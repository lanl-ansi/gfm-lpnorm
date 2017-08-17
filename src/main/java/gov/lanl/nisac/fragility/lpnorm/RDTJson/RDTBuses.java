package gov.lanl.nisac.fragility.lpnorm.RDTJson;



public class RDTBuses {

	private String id;
	private double x;
	private double y;
	private float min_voltage = 0.8f;
	private float max_voltage = 1.2f;
	private float[] ref_voltage = {1.0f,1.0f,1.0f};
	private boolean[] has_phase = {true,true,true};
	private boolean has_generator = false;

	public RDTBuses() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public float getMin_voltage() {
		return min_voltage;
	}

	public void setMin_voltage(float min_voltage) {
		this.min_voltage = min_voltage;
	}

	public float getMax_voltage() {
		return max_voltage;
	}

	public void setMax_voltage(float max_voltage) {
		this.max_voltage = max_voltage;
	}

	public float[] getRef_voltage() {
		return ref_voltage;
	}

	public void setRef_voltage(float[] ref_voltage) {
		this.ref_voltage = ref_voltage;
	}

	public boolean[] getHas_phase() {
		return has_phase;
	}

	public void setHas_phase(boolean[] has_phase) {
		this.has_phase = has_phase;
	}

	public boolean isHas_generator() {
		return has_generator;
	}

	public void setHas_generator(boolean has_generator) {
		this.has_generator = has_generator;
	}

}
