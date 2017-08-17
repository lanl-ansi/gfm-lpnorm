package gov.lanl.nisac.fragility.lpnorm.RDTJson;

import java.util.List;

public class RDTData {
	
	private List<RDTBuses> buses; 
	private List<RDTLoads> loads;
	private List<RDTGenerators> generators;
	private List<RDTLineCodes> line_codes;
	private List<RDTLines> lines;
	private float critical_load_met = 0.98f;
	private float total_load_met = 0.5f;
	private float chance_constraint = 1.0f;
	private float phase_variation = 0.15f;
	private List<RDTScenarios> scenarios;
	private float mva_base = 100.0f;
	private String algorithm = "sbd";
	private String solver = "scip";
	
	
	public RDTData() {}

	public List<RDTBuses> getBuses() {
		return buses;
	}

	public void setBuses(List<RDTBuses> buses) {
		this.buses = buses;
	}

	public List<RDTLoads> getLoads() {
		return loads;
	}

	public void setLoads(List<RDTLoads> loads) {
		this.loads = loads;
	}

	public List<RDTGenerators> getGenerators() {
		return generators;
	}

	public void setGenerators(List<RDTGenerators> generators) {
		this.generators = generators;
	}

	public List<RDTLineCodes> getLine_codes() {
		return line_codes;
	}

	public void setLine_codes(List<RDTLineCodes> line_codes) {
		this.line_codes = line_codes;
	}

	public float getCritical_load_met() {
		return critical_load_met;
	}

	public void setCritical_load_met(float critical_load_met) {
		this.critical_load_met = critical_load_met;
	}

	public float getTotal_load_met() {
		return total_load_met;
	}

	public void setTotal_load_met(float total_load_met) {
		this.total_load_met = total_load_met;
	}

	public float getChance_constraint() {
		return chance_constraint;
	}

	public void setChance_constraint(float chance_constraint) {
		this.chance_constraint = chance_constraint;
	}

	public float getPhase_variation() {
		return phase_variation;
	}

	public void setPhase_variation(float phase_variation) {
		this.phase_variation = phase_variation;
	}

	public List<RDTScenarios> getScenarios() {
		return scenarios;
	}

	public void setScenarios(List<RDTScenarios> scenarios) {
		this.scenarios = scenarios;
	}

	public List<RDTLines> getLines() {
		return lines;
	}

	public void setLines(List<RDTLines> lines) {
		this.lines = lines;
	}


	public float getMva_base() {
		return mva_base;
	}

	public void setMva_base(float mva_base) {
		this.mva_base = mva_base;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getSolver() {
		return solver;
	}

	public void setSolver(String solver) {
		this.solver = solver;
	}
}
