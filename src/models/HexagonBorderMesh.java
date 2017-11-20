package models;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import assets.models.Element_Model;
import lwlal.Vector3f;

public class HexagonBorderMesh extends Element_Model{
	
	private int elr;			//edge length relation = edgeLengthHexagons / edgeLengthTriangles
	
	private int xOffset, yOffset;
	
	private final int PRI = -1;					//primitive restart index
	
	private int hexLength, hexWidth;			//length and width measured in vertices, that are relevant for a hexagon mesh
	
	//********************************** constructor ************************************
	
	/**
	 * 
	 * @param triangleMesh TriangleMesh this hexagonal mesh is based on
	 * @param halfXOffset the xOffset divided by two, to avoid odd numbers
	 * @param halfYOffset the yOffset divided by two, to avoid odd numbers
	 * @param log2EdgeLengthRelation the base 2 logarithm of the edge length relation, to make sure it's a power of 2
	 * , edgeLengthRelation = edge length of hexagons / edge length of triangles
	 */
	public HexagonBorderMesh(TriangleMesh triangleMesh, int halfXOffset, int halfYOffset, int log2EdgeLengthRelation) {
		
		super(GL_LINE_LOOP);
		//TODO: maybe log2EdgeLengthRelation < 1
		if (log2EdgeLengthRelation < 0 || triangleMesh == null || xOffset < 0 || yOffset < 0) {
			throw new IllegalArgumentException();
		}
		
		this.xOffset = halfXOffset;
		this.yOffset = halfYOffset;
		this.elr = (int)Math.pow(2, log2EdgeLengthRelation);
		
		hexLength = (triangleMesh.getLength() - 2 * xOffset + elr - 1) / elr;
		hexWidth  = (triangleMesh.getWidth()  - 2 * yOffset + elr -1 - elr/2) / elr;
		
		FloatBuffer posBuffer = processVertices(triangleMesh);
		IntBuffer elementBuffer = createElementBuffer();
		FloatBuffer colBuffer = createColBuffer();
		
		this.setVertexPositionData(posBuffer, 3, GL_STATIC_DRAW);
		this.setElementArrayData(elementBuffer);
		this.setVertexColorData(colBuffer, 4, GL_STATIC_DRAW);
		
	}
	
	
	//********************************** prime method ***********************************
	private FloatBuffer processVertices(TriangleMesh triangleMesh) {
		
		int triMeshVertLength = triangleMesh.getLength();
		
		Vector3f[] triMeshPos = triangleMesh.getPosArray();
		
		for (int i=0; i<triMeshPos.length; i++) {
			float c;
			float d = triMeshPos[i].getC();
			if (d < 0) {
				c = 0.01f;
			} else {
				c = d + 0.01f;
			}
			triMeshPos[i].setC(c);
			
		}
		
		FloatBuffer posBuffer = BufferUtils.createFloatBuffer(hexLength*hexWidth*2);
		
		for (int y=0; y<hexWidth; y++) {
			
			if (y%3 == 0) {
				for (int x=0; x<hexLength; x++) {
					
					if (x%2 == 0) {
						posBuffer.put(triMeshPos[elr*(x+(2*y+1)*hexLength)].toArray());
					} else {
						posBuffer.put(triMeshPos[elr*(x+2*y*hexLength)].toArray());
					}
				}
			} else if (y%3 == 2) {
				for (int x=0; x<hexLength; x++) {
					
					if (x%2 == 0) {
						posBuffer.put(triMeshPos[elr*(x+(2*y-1)*hexLength)].toArray());
					} else {
						posBuffer.put(triMeshPos[elr*(x+(2*y)*hexLength)].toArray());
					}
				}
			}
		}
		
		posBuffer.flip();
		
		return posBuffer;
	}
	
	private IntBuffer createElementBuffer() {
		
		IntBuffer elementBuffer = BufferUtils.createIntBuffer( hexLength * hexWidth * 7 );
		
		for (int y=0; y<hexWidth*2/3-1; y++) {
			
			if(y%2 == 0) {
				for (int x=0; x<hexLength/2 - 1; x++) {
					
					elementBuffer.put(new int[] {	0           + 2*x + hexLength*y,
													1           + 2*x + hexLength*y,
													2           + 2*x + hexLength*y,
													hexLength+2 + 2*x + hexLength*y,
													hexLength+1 + 2*x + hexLength*y,
													hexLength   + 2*x + hexLength*y,
													PRI
												});
					
				}
			} else {	// y%2 == 1
				for (int x=0; x<hexLength/2 - 1; x++) {
					
					elementBuffer.put(new int[] {	0           + 2*x + hexLength*y + 1,
													1           + 2*x + hexLength*y + 1,
													2           + 2*x + hexLength*y + 1,
													hexLength+2 + 2*x + hexLength*y + 1,
													hexLength+1 + 2*x + hexLength*y + 1,
													hexLength   + 2*x + hexLength*y + 1,
													PRI
												});
					
				}
			}
			
		}
		
		
		elementBuffer.flip();
		
		return elementBuffer;
		
	}
	
	private FloatBuffer createColBuffer() {
		
		FloatBuffer colBuffer = BufferUtils.createFloatBuffer(hexLength*hexWidth*4);
		
		for (int y=0; y<hexWidth; y++) {
			for (int x=0; x<hexLength; x++) {
				
				colBuffer.put(new float[] {0.2f, 0.2f, 0.2f, 1});
				
			}
		}
		
		colBuffer.flip();
		
		return colBuffer;
	}
	
	
	//********************************** other stuff **********************************************
	
	
	public void onDrawStart() {
		super.onDrawStart();
		
		glLineWidth(2.0f);
		
		glEnable(GL_PRIMITIVE_RESTART);
		glPrimitiveRestartIndex(PRI);
	}
	
	
	public void onDrawStop() {
		super.onDrawStop();
		
		glDisable(GL_PRIMITIVE_RESTART);
		
	}

}
