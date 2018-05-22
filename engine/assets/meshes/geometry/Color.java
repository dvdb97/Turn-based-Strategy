package assets.meshes.geometry;

import math.vectors.Vector4f;

//TODO: shaders accept a color only as vector4f 

public class Color {
	
	private float red;
	private float green;
	private float blue;
	private float alpha;
	
	
	public Color(float red, float green, float blue, float alpha) {
		super();
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public Color copyOf() {
		return new Color(red, green, blue, alpha);
	}
	
	public Vector4f toVector4f() {
		return new Vector4f(red, green, blue, alpha);
	}
	
	/**
	 * @return the red
	 */
	public float getRed() {
		return red;
	}


	/**
	 * @return the green
	 */
	public float getGreen() {
		return green;
	}


	/**
	 * @return the blue
	 */
	public float getBlue() {
		return blue;
	}


	/**
	 * @return the alpha
	 */
	public float getAlpha() {
		return alpha;
	}
	
	
}
