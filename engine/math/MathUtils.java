package math;

public class MathUtils {
	
	public static float square(float value) {
		return value * value;
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

}
