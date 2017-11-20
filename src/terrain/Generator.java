package terrain;


public interface Generator {
	
	public abstract float getElevation(int x, int y);
	
	public abstract int getLength();
	
	public abstract int getWidth();
	
}

