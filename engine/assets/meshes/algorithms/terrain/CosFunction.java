package assets.meshes.algorithms.terrain;

public class CosFunction implements ElevationFunction {

	private int width, height;
	
	private float frequency;
	
	public CosFunction(int width, int height, float frequency) {
		this.width = width;
		this.height = height;
		this.frequency = frequency;
	}
	
	
	@Override
	public float getDepth(int x, int y) {
		float v = (float)x / (float)width;
		return (float)Math.cos(v * frequency);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

}
