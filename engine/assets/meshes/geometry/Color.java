package assets.meshes.geometry;

import math.vectors.Vector4f;

//TODO: shaders accept colors only in form of Vektor4f

public class Color {
	
	public static final Color RED = new Color(1f, 0f, 0f, 0f);
	public static final Color WHITE = new Color(1f, 1f, 1f, 1f);
	
	private float red;
	private float green;
	private float blue;
	private float alpha;
	
	//**************************** constructor ******************************
	
	public Color(float red, float green, float blue, float alpha) {
		
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public Color(int red, int green, int blue, int alpha) {
		
		this.red = red/255f;
		this.green = green/255f;
		this.blue = blue/255f;
		this.alpha = alpha/255f;
	}
	
	
	
	//***********************************************************************
	
	public Color copyOf() {
		return new Color(red, green, blue, alpha);
	}
	
	public Vector4f toVector4f() {
		return new Vector4f(red, green, blue, alpha);
	}
	
	public void print() {
		System.out.println("RGB: " + (int)(red*255) +" "+ (int)(green*255) +" "+ (int)(blue*255) +" "+ (int)(alpha*255));
	}
	
	//****************************    getter    ******************************
	
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
	
	//***********************************
	
	/**
	 * @return the red
	 */
	public int getRedInt() {
		return (int)(red*255);
	}


	/**
	 * @return the green
	 */
	public int getGreenInt() {
		return (int)(green*255);
	}


	/**
	 * @return the blue
	 */
	public int getBlueInt() {
		return (int)(blue*255);
	}


	/**
	 * @return the alpha
	 */
	public int getAlphaInt() {
		return (int)(alpha*255);
	}

}
