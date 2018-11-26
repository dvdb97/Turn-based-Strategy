package assets.meshes.geometry;

import math.vectors.Vector2f;
import math.vectors.Vector3f;


public class VertexLegacy extends Vector3f {
	
	/*
	 * An member variable without any purpose except helping me create a super fast 
	 * vertex structuring algorithm!
	 */
	private int structuringIndex;
	
	//better save in this in form of Color
	private float red;
	private float green;
	private float blue;
	private float alpha;
	
	
	private Vector2f texturePositions;
	boolean textured;
	
	
	//the normal vector of the vertex is the average of the normals of its surrounding surfaces
	private Vector3f normalVec;
	private int numberOfSurfaceNormals;
	
	//************************************** constructor **************************************
	
	
	public VertexLegacy(float x, float y, float z) {
		super(x, y, z);
	}
	
	
	public VertexLegacy(Vector3f position) {
		super(position.getA(), position.getB(), position.getC());
	}
	
	
	public VertexLegacy(float x, float y, float z, float red, float green, float blue, float alpha) {
		super(x, y, z);
		
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
		
		normalVec = null;
		textured = false;
		numberOfSurfaceNormals = 0;
		
	}
	
	
	public VertexLegacy(float x, float y, float z, Color color) {
		this(x, y, z, color.getA(), color.getB(), color.getC(), color.getD());
	}
	
	
	public VertexLegacy(Vector3f position, Color color) {
		this(position.getA(), position.getB(), position.getC(), color);
	}
	
	
	public VertexLegacy(Vector3f position, Vector2f texPos) {
		this(position, new Color(0f, 0f, 0f, 0f));
		
		this.setTexturePositions(texPos);
	}
	
	
	public VertexLegacy() {
		super(0, 0, 0);
		
		this.red = 0.0f;
		this.green = 0.0f;
		this.blue = 0.0f;
		this.alpha = 1.0f;		
		
	}
	
	//************************************** methods **************************************
	public boolean equals(VertexLegacy vertex, float tolerance) {
		if(Math.abs(this.getA() - vertex.getA()) > tolerance){
			return false;
		}
		
		if (Math.abs(this.getB() - vertex.getB()) > tolerance) {
			return false;
		}
		
		if (Math.abs(this.getC() - vertex.getC()) > tolerance) {
			return false;
		}
		
		return true;		
	}
	
	
	public void addSurfaceNormal(Vector3f surfaceNormal) {
		
		if (this.normalVec == null) {
			this.normalVec = new Vector3f(0f, 0f, 0f);
		}
		
		this.normalVec = this.normalVec.timesEQ(numberOfSurfaceNormals);
		
		this.normalVec = this.normalVec.plus(surfaceNormal);
		
		this.normalVec = this.normalVec.timesEQ(1.0f / (float)++numberOfSurfaceNormals);
		
	}
	
	
	
	//************************************** getter **************************************
	public Vector3f getNormal() {
		return normalVec;
	}
	
	
	public void setStructuringIndex(int index) {
		structuringIndex = index;
	}
	
	
	public int getStructuringIndex() {
		return structuringIndex;
	}


	public float getCheckSum() {
		return getA() + getB() + getC();
	}
	
	public Vector3f getPosition() {
		return new Vector3f(getA(), getB(), getC());
	}
	
	public float[] getPositionData() {
		float[] data = {
			getA(), getB(), getC()
		};
		
		return data;
	}
	
	
	public float[] getColorData() {
		float[] data = {
			getRed(), getGreen(), getBlue(), getAlpha()
		};
		
		
		return data;
	}
	
	
	public float getRed() {
		return red;
	}


	public float getGreen() {
		return green;
	}


	public float getBlue() {
		return blue;
	}


	public float getAlpha() {
		return alpha;
	}
	
	//*************************************** setter ************************************
	
	
	public void setColor(float r, float g, float b, float a) {
		
		red   = r;
		green = g;
		blue  = b;
		alpha = a;
		
	}
		public void setColor(Color color) {
		
		setColor(color.getA(), color.getB(), color.getC(), color.getD());
		
	}
	
		/**
		 * alpha value is set to 1
		 */
	public void setColor(float r, float g, float b) {
		setColor(r, g, b, 1);
	}
	
	public void setTexturePositions(Vector2f texPos) {
		this.texturePositions = texPos;
		
		textured = true;
	}
	
	
	public Vector2f getTexturePositions() {
		return texturePositions;
	}
	
	
	public boolean isTextured() {
		return textured;
	}
	
	
	//********************************************** print ******************************************
	public void printColor() {
		System.out.println(getRed() + " | " + getGreen() + " | " + getBlue() + " | " + getAlpha());
	}

}
