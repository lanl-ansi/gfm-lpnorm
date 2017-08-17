package gov.lanl.nisac.fragility.lpnorm.RDTJson;

public class RDTLineCodes {

	private int line_code;
	private int num_phases;
	private float[][] rmatrix;
	private float[][] xmatrix;

	public RDTLineCodes() {
	}

	public int getLine_code() {
		return line_code;
	}

	public void setLine_code(int line_code) {
		this.line_code = line_code;
	}

	public int getNum_phases() {
		return num_phases;
	}

	public void setNum_phases(int num_phases) {
		this.num_phases = num_phases;
	}

	public float[][] getRmatrix() {
		return rmatrix;
	}

	public void setRmatrix(float[][] rmatrix) {
		this.rmatrix = rmatrix;
	}

	public float[][] getXmatrix() {
		return xmatrix;
	}

	public void setXmatrix(float[][] xmatrix) {
		this.xmatrix = xmatrix;
	}

}
