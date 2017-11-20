package terrain;

public class LogFunction implements Generator {

	@Override
	public float getElevation(int x, int y) {
		float a = (float)Math.random();
		
		float value = (float)Math.log(square(a) + square(1 + x) + square(-1 + y)) + (float)Math.log(square(a) + square(-1 + x) + square(1 + y));
		
		return value;
	}
	
	
	private float square(float value) {
		return value * value;
	}
	

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

}
