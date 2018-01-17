package utils;

public class Calc {
	
	/**
	 * 
	 * @param array set of floats
	 * @return the average of the values of array
	 */
	public static float average(float[] array) {
		
		float sum = 0;
		for (float f : array) {
			sum += f;
		}
		
		return sum/array.length;
		
	}
	
	/**
	 * 
	 * @param array set of floats
	 * @return the standard deviation of the values of array
	 */
	public static float standardDeviation(float[] array) {
		
		return standardDeviation(array, average(array));
		
	}
	
	public static float standardDeviation(float[] array, float avg) {
		
		float variance = 0;
		for (float f : array) {
			variance += (f-avg) * (f-avg);
		}
		variance /= array.length;
		
		return (float)Math.sqrt(variance);
		
	}
	
}