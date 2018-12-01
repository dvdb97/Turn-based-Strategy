package utils;

/**
 * 
 * @author jona
 * 
 * a class to represent values between zero and one.
 * the unit is not % !
 * 
 */
public class Percentage implements Cloneable {
	
	private float value;
	
	//********************************** constructor *****************************************
	
	/**
	 * 
	 * @param value must be greater than zero and smaller than one
	 */
	public Percentage(float value) {
		
		if (value < 0) {
			throw new IllegalArgumentException("a percentage can't be negative: " + value);
		}
		
		if (value > 1) {
			throw new IllegalArgumentException("a percentage can't be greater than 1: " + value);
		}
		
		this.value = value;
		
	}
	
	/**
	 * creates a Percentage with value zero
	 */
	public Percentage() {
		this(0);
	}
	
	//**************************************** math ************************************************
	
	/**
	 * 
	 * @param factor percentage this percentage is multiplied with
	 * @return a Percentage
	 */
	public Percentage times(Percentage factor) {
		
		return new Percentage(this.value*factor.getValue());
		
	}
	
	//**************************************** get & set ************************************************
	
	/**
	 * 
	 * @return value as a float
	 */
	public float getValue() {
		return value;
	}
	
	/**
	 * 
	 * @return a string in the form of "  5%" or " 62%"
	 */
	public String getString() {
		
		int rounded = Math.round(value*100);
	//	String a = rounded >= 100 ? "" : " ";
	//	String b = rounded >= 10 ? "" : " ";
		
	//	return a + b + rounded + "%";
		
		return rounded + "%";
	}
	
	
	//*************************************** equals & clone ********************************************
	
	/**
	 * 
	 * @param p the Percentage to compare this with
	 * @return true, if both Percentages have the same value. returns false, if p is null.
	 */
	public boolean equals(Percentage p) {
		
		if (p == null) {
			return false;
		}
		
		if (p.getValue() == this.getValue()) {
			return true;
		}
		
		return false;
		
	}
	
	@Override
	public Percentage clone() {
		
		return new Percentage(value);
		
	}
}
