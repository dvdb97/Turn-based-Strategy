package utils;

public class Algebra {
	
	/**
	 * 
	 * @param a value to round
	 * @param n number of decimal places to round to
	 * @return a rounded to n decimal places
	 */
	public static float round(float a, float n) {
		
		float d = (float)Math.pow(10, n);
		return Math.round(a*d)/d;
		
	}
	
}
