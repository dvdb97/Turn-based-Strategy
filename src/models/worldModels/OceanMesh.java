package models.worldModels;

import assets.models.Element_Model;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class OceanMesh extends Element_Model {

	private float width;

	private float height;
	
	private int widthInTriangles;
	
	private int heightInTriangles;
	
	private float waveRange;
	
	public OceanMesh(float width, float height, int widthInTriangles, int heightInTriangles, float waveRange) {
		super(GL_TRIANGLES);
		
		
	}
	
	
	private void generate() {
		
	}
	

}
