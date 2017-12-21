package assets.meshes.geometry;


import math.vectors.Vector2f;
import math.vectors.Vector3f;


public class Vertex extends Vector3f {
	
	/*
	 * An member variable without any purpose except helping me create a super fast 
	 * vertex structuring algorithm!
	 */
	private int structuringIndex;
	
	private float red;
	private float green;
	private float blue;
	private float alpha;
	
	
	private Vector2f texturePositions;
	boolean textured = false;
	
	
	//the normal vector of the vertex is the average of the normals of its surrounding surfaces
	private Vector3f normalVec = null;
	private int numberOfSurfaceNormals = 0;
	
	//************************************** constructor **************************************
	public Vertex(float x, float y, float z, float red, float green, float blue, float alpha) {
		super(x, y, z);
		
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
		
	}
	
	
	public Vertex(float x, float y, float z, Color color) {
		this(x, y, z, color.getA(), color.getB(), color.getC(), color.getD());
	}
	
	
	public Vertex() {
		super(0, 0, 0);
		
		this.red = 0.0f;
		this.green = 0.0f;
		this.blue = 0.0f;
		this.alpha = 1.0f;		
		
	}
	
	//************************************** methods **************************************
	public boolean equals(Vertex vertex, float tolerance) {
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
	
	
	public void setColor(Color color) {
		this.red = color.getA();
		this.green = color.getB();
		this.blue = color.getC();
		this.alpha = color.getD();
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
