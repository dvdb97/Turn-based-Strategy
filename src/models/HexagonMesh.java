package models;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;

import assets.models.Element_Model;

public class HexagonMesh extends Element_Model {
	
	private int edgeLengthRelation;		//relation = edgeLengthHexagons / edgeLengthTriangles
	
	private int xOffset, yOffset;
	
	private int PRI;					//primitive restart index
	
	//********************************** constructor ************************************
	public HexagonMesh(TriangleMesh triangleMesh) {
		
		super(GL_TRIANGLES);
		
		processVertices(triangleMesh);
		
	}
	
	
	//********************************** prime method ***********************************
	public void processVertices(TriangleMesh triangleMesh) {
		
		
		
	}
	
	
	//***********************************************************************************
	public void onDrawStart() {
		super.onDrawStart();
		
		glEnable(GL_PRIMITIVE_RESTART);
		glPrimitiveRestartIndex(PRI);
	}
	
	
	public void onDrawStop() {
		super.onDrawStop();
		
		glDisable(GL_PRIMITIVE_RESTART);
		
	}
	
	
}
