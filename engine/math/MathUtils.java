package math;

public class MathUtils {
	
	public static float square(float value) {
		return value * value;
	}
	
	
	public static float sqrt(float value) {
		return (float)Math.sqrt(value);
	}
	
	
	public static float getAbsoluteValue(float value) {
		
		return value < 0 ? -1f * value : value;
		
	}
	
	
	public static float max(float a, float b, float c) {
		float max;
		
		if (a < b) {
			max = b;
		} else {
			max = a;
		}
		
		if (max < c) {
			return c;
		}
		
		return max;
	}
	
	
	public static float min(float a, float b) {
		return a < b ? a : b;
	}
	
	
	public static int min(int a, int b) {
		return a < b ? a : b;
	}
	
	
	public static float max(float a, float b) {
		return a > b ? a : b;
	}
	
	
	public static int max(int a, int b) {
		return a > b ? a : b;
	}
	
	
	public static float clamp(float value, float min, float max) {
		return min(max(value, min), max);
	}
	
	
	public static int clamp(int value, int min, int max) {
		return min(max(value, min), max);
	}
}
