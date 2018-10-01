package assets.meshes.algorithms.terrain;

public interface ElevationFunction {
	
	public abstract float getDepth(int x, int y);
	
	public abstract int getWidth();
	
	public abstract int getHeight();

}
