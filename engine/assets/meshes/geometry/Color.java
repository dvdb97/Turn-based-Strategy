package assets.meshes.geometry;


import math.vectors.Vector4f;


public class Color extends Vector4f {
	
	//Predefined colors. Feel free to add more.
	public static final Color RED = new Color(1f, 0f, 0f, 1f);
	
	public static final Color GREEN = new Color(0f, 1f, 0f, 1f);
	
	public static final Color BLUE = new Color(0f, 0f, 1f, 1f);
	
	public static final Color YELLOW = new Color(1f, 1f, 0f, 1f);
	
	public static final Color GREY = new Color(0.5f, 0.5f, 0.5f, 1f);
	

	/**
	 * 
	 * Creates a color.
	 * 
	 * @param red The value of the red color channel in the range from [0-1].
	 * @param green The value of the green color channel in the range from [0-1].
	 * @param blue The value of the blue color channel in the range from [0-1].
	 * @param alpha The value of the alpha channel in the range from [0-1].
	 */
	public Color(float red, float green, float blue, float alpha) {
		super(red, green, blue, alpha);
	}
}
