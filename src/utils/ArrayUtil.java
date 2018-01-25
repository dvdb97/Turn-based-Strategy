package utils;

public class ArrayUtil {
	
	
	//*********************************** 1D-array *******************************
	
	/**
	 * sets every element of the array to the value
	 */
	public static void setArray(float[] array, float value) {
		
		for (int i=0; i<array.length; i++) {
			array[i] = value;
		}
		
	}	
	
	
	/**
	 * @return the average of all array's elements
	 */
	public static float calcAverage(float[] array) {
		
		float sum = calcSum(array);
		
		float average = sum/array.length;
		
		return average;
		
	}
	
	
	
	/**
	 * @return all elements of the array summe up
	 */
	public static float calcSum(float[] array) {
		
		float sum = 0;
		
		for (int i=0; i<array.length; i++) {
			sum += array[i];
		}
		
		return sum;
		
	}
	
	//*********************************** 2D-array *******************************
	
	/**
	 * sets every element of the array to the value
	 */
	public static void setArray(float[][] array, float value) {
		
		for (int i=0; i<array.length; i++) {
			setArray(array[i], value);
		}
		
	}
	
	/**
	 * @return the average of all array's elements
	 */
	public static float calcAverage(float[][] array) {
		
		float[] partialAvg = new float[array.length];
		
		for (int i=0; i<array.length; i++) {
			partialAvg[i] = calcAverage(array[i]);
		}
		
		return calcAverage(partialAvg);
		
	}
	
}
