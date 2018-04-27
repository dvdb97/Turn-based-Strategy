package utils;

public class Calc {
	
	/**
	 * 
	 * @param array set of floats
	 * @return the average of the values of array
	 */
	public static float averageOf(float[] array) {
		
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
	public static float standardDeviationOf(float[] array) {
		
		return standardDeviationOf(array, averageOf(array));
		
	}
	
	/**
	 * 
	 * @param array set of floats
	 * @param average the average of the float array
	 * @return the standarddeviation of the float array (the square root of the variance)
	 */
	public static float standardDeviationOf(float[] array, float average) {
		
		return (float)Math.sqrt(varianceOf(array, average));
		
	}
	
	
	/**
	 * 
	 * @param array a set of floats
	 * @param average the average of the float array
	 * @return the variance of the float array (the standard deviation squared)
	 */
	public static float varianceOf(float[] array, float average) {
		
		float variance = 0;
		for (float f : array) {
			variance += (f-average) * (f-average);
		}
		variance /= array.length;
		
		return variance;
	}
	
	
	
}