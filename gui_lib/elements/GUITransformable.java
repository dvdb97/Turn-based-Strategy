package elements;

public interface GUITransformable {
	
	/**
	 * 
	 * Moves the element to the position (x, y)
	 *  
	 * @param x The x position
	 * @param y The y position
	 */
	public abstract void move(float x, float y);
	
	
	/**
	 * 
	 * Resizes the element by changing the size and maybe adapting the position
	 * 
	 * @param rx The additional width
	 * @param ry The additional height
	 */
	public abstract void resize(float rx, float ry);

}
