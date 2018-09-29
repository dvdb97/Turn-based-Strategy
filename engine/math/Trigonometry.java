package math;

public class Trigonometry {
	
	public static final float PI = (float)Math.PI;
	
	public static final float HALF_PI = (float)Math.PI / 2;
	
	public static final float QUARTER_PI = (float)Math.PI / 4;
	
	
	public static float cos(float value) {
		return (float)Math.cos(value);
	}
	
	
	public static float acos(float f) {
		if (f > 1f) {
			return acos(1f);
		}
		
		return (float)Math.acos(f);
	}
	
	
	public static float sin(float value) {
		return (float)Math.sin(value);
	}
	
	
	//TODO: Add more

}
