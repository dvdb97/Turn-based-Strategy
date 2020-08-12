package math;

public class MathUtils {
	
	public static float square(float value) {
		return value * value;
	}
	
	
	public static float sqrt(float value) {
		return (float)Math.sqrt(value);
	}
	

	public static float lerp(float t, float x, float y) {
		return x + t * (y - x);
	}
	
	
	public static float blerp(float a, float b, float x00, float x10, float x01, float x11) {
		return lerp(b, lerp(a, x00, x10), lerp(b, x01, x11));
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
	
	
	public static int floor(float x) {
		return (int)Math.floor(x);
	}
	
	
	public static int ceil(float x) {
		return (int)Math.ceil(x);
	}
	
	
	public static float clamp(float value, float min, float max) {
		return min(max(value, min), max);
	}
	
	
	public static int clamp(int value, int min, int max) {
		return min(max(value, min), max);
	}
	
}
