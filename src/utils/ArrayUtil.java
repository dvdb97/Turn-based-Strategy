package utils;

public class ArrayUtil {
	
	/**
	 * sets every element of the array to the value
	 */
	public static void setArray(float[] array, float value) {
		
		for (int i=0; i<array.length; i++) {
			array[i] = value;
		}
		
	}
	
	/**
	 * sets every element of the array to the value
	 */
	public static void setArray(float[][] array, float value) {
		
		for (int i=0; i<array.length; i++) {
			for (int j=0; j<array[i].length; j++) {
				array[i][j] = value;
			}
		}
		
	}
	
}
