package gov.lanl.nisac.fragility.lpnorm.RDTJson;

public class RDTLines{

	private String id;
	private String node1_id;
	private String node2_id;
	private boolean[] has_phase;
	private double capacity = 1e30;
	private float length = 1.0f;
	private int num_phases = 3;
	private boolean is_transformer = false;
	private int line_code;
	private int num_poles;
	private double harden_cost = 1e30;
	private double switch_cost = 1e30;
	private boolean is_new = false;
	private boolean has_switch = false;
	private double construction_cost = 1e30;
	private boolean can_harden = true;
	private boolean can_add_switch = true;

	public RDTLines() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNode1_id() {
		return node1_id;
	}

	public void setNode1_id(String node1_id) {
		this.node1_id = node1_id;
	}

	public String getNode2_id() {
		return node2_id;
	}

	public void setNode2_id(String node2_id) {
		this.node2_id = node2_id;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public int getNum_phases() {
		return num_phases;
	}

	public void setNum_phases(int num_phases) {
		this.num_phases = num_phases;
	}

	public boolean isIs_transformer() {
		return is_transformer;
	}

	public void setIs_transformer(boolean is_transformer) {
		this.is_transformer = is_transformer;
	}

	public int getLine_code() {
		return line_code;
	}

	public void setLine_code(int line_code) {
		this.line_code = line_code;
	}

	public int getNum_poles() {
		return num_poles;
	}

	public void setNum_poles(int num_poles) {
		this.num_poles = num_poles;
	}

	public double getHarden_cost() {
		return harden_cost;
	}

	public void setHarden_cost(double harden_cost) {
		this.harden_cost = harden_cost;
	}

	public double getSwitch_cost() {
		return switch_cost;
	}

	public void setSwitch_cost(double switch_cost) {
		this.switch_cost = switch_cost;
	}

	public boolean isIs_new() {
		return is_new;
	}

	public void setIs_new(boolean is_new) {
		this.is_new = is_new;
	}

	public boolean isHas_switch() {
		return has_switch;
	}

	public void setHas_switch(boolean has_switch) {
		this.has_switch = has_switch;
	}

	public boolean[] getHas_phase() {
		return has_phase;
	}

	public void setHas_phase(boolean[] has_phase) {
		this.has_phase = has_phase;
	}

	public double getConstruction_cost() {
		return construction_cost;
	}

	public void setConstruction_cost(double construction_cost) {
		this.construction_cost = construction_cost;
	}

	public boolean isCan_harden() {
		return can_harden;
	}

	public void setCan_harden(boolean can_harden) {
		this.can_harden = can_harden;
	}

	public boolean isCan_add_switch() {
		return can_add_switch;
	}

	public void setCan_add_switch(boolean can_add_switch) {
		this.can_add_switch = can_add_switch;
	}
}
