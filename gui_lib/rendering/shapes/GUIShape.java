package rendering.shapes;

import assets.meshes.geometry.Color;

public abstract class GUIShape {
	
	private Color color;
	protected String state;

	/**
	 * 
	 * @param color The color to render the shape with.
	 */
	public GUIShape(Color color) {
		this.color = color;
	}
	
	
	/**
	 * 
	 * @param color The color to render the shape with.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	
	/**
	 * 
	 * @return Returns the color of this shape.
	 */
	public Color getColor() {
		return color;
	}
	
	
	/**
	 * 
	 * Changes the state of this shape.
	 * 
	 * @param state The new state.
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	
	/**
	 * 
	 * Renders the Shape onto the screen.
	 * 
	 * @param x The x coordinate of the left upper corner of the element.
	 * @param y The y coordinate of the left upper corner of the element.
	 * @param width The width of the element.
	 * @param height The height of the element.
	 */
	public abstract void render(int x, int y, int width, int height);
	
	
	/**
	 * 
	 * Checks if the cursor is currently targeting the element with this shape.
	 * 
	 * @param x The x coordinate of the left upper corner of the element.
	 * @param y The y coordinate of the left upper corner of the element.
	 * @param width The width of the element.
	 * @param height The height of the element.
	 * @param cursorX The y coordinate of the cursor.
	 * @param cursorY The x coordinate of the cursor.
	 * @return Returns true if the cursor is targeting the element with this shape.
	 */
	public abstract boolean isTargeted(int x, int y, int width, int height, int cursorX, int cursorY);
	
}
