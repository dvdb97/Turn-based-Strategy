package models.worldModels;

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
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import assets.meshes.geometry.Vertex;
import assets.models.Element_Model;
import math.vectors.Vector3f;
import utils.CustomBufferUtils;

public class HexagonBorderGrid extends Element_Model{
	
	private int elr;			//edge length relation = edgeLengthHexagons / edgeLengthTriangles
	
	private int xOffset, yOffset;
	
	private final int PRI = -1;					//primitive restart index
	
	private int hexLength, hexWidth;			//length and width measured in for a hexagon grid relevant vertices
	
	private IntBuffer indexBuffer;
	
	private FloatBuffer posBuffer;
	
	private Vertex[] vertices;
	
	private List<Integer> hexagonCenterIndices;
	
	
	//********************************** constructor ************************************
	
	/**
	 * 
	 * @param triangleGrid TriangleGrid this hexagonal grid is based on
	 * @param halfXOffset the xOffset divided by two, to avoid odd numbers
	 * @param halfYOffset the yOffset divided by two, to avoid odd numbers
	 * @param log2EdgeLengthRelation the base 2 logarithm of the edge length relation, to make sure it's a power of 2
	 * , edgeLengthRelation = edge length of hexagons / edge length of triangles
	 */
	public HexagonBorderGrid(TriangleGrid triangleGrid, int halfXOffset, int halfYOffset, int log2EdgeLengthRelation) {
		
		super(GL_LINE_LOOP);
		//TODO: maybe log2EdgeLengthRelation < 1
		if (log2EdgeLengthRelation < 0 || triangleGrid == null || xOffset < 0 || yOffset < 0) {
			throw new IllegalArgumentException();
		}
		
		this.xOffset = halfXOffset;
		this.yOffset = halfYOffset;
		this.elr = (int)Math.pow(2, log2EdgeLengthRelation);
		
		hexLength =  (triangleGrid.getLength() - 2 * xOffset - 1) / elr + 1;
		hexWidth  = ((triangleGrid.getWidth()  - 2 * yOffset    ) / elr - 1)*2/3 + 1;
		
		FloatBuffer posBuffer = processVertices(triangleGrid);
		IntBuffer elementBuffer = createElementBuffer();
		FloatBuffer colBuffer = createColBuffer();
		
		this.setVertexPositionData(posBuffer, 3, GL_STATIC_DRAW);
		this.setElementArrayData(elementBuffer);
		this.setVertexColorData(colBuffer, 4, GL_STATIC_DRAW);
		
	}
	
	
	//********************************** prime methods ***********************************
	private FloatBuffer processVertices(TriangleGrid triangleGrid) {
		
		int triGridVertLength = triangleGrid.getLength();
		
		Vector3f[] triGridPos = triangleGrid.getPosArray();
		
		float delta = 0.005f*elr;
		
		for (int i=0; i<triGridPos.length; i++) {
			float c;
			float d = triGridPos[i].getC();
			if (d < 0) {
				c = delta;
			} else {
				c = d + delta;
			}
			triGridPos[i].setC(c);
			
		}
		
		FloatBuffer posBuffer = BufferUtils.createFloatBuffer(hexLength*hexWidth*3);
		
		hexagonCenterIndices = new ArrayList<Integer>((hexLength / 2 - 1) * (hexWidth - 1));
		
		for (int y=0; y<hexWidth; y++) {
			
			if (y%2 == 0) {
				for (int x=0; x<hexLength-1; x++) {
					
					if (x%2 == 0) {
						posBuffer.put(triGridPos[(xOffset + x*elr) + (yOffset + y*elr*3/2 + elr/2)*triGridVertLength].toArray());
					} else {
						posBuffer.put(triGridPos[(xOffset + x*elr) + (yOffset + y*elr*3/2        )*triGridVertLength].toArray());
						hexagonCenterIndices.add((xOffset + x*elr) + (yOffset + y*elr*3/2 + elr  )*triGridVertLength);
					}
						
				}
				int x=hexLength-1;
				if (x%2 == 0) {
					posBuffer.put(triGridPos[(xOffset + x*elr) + (yOffset + y*elr*3/2 + elr/2)*triGridVertLength].toArray());
				} else {
					posBuffer.put(triGridPos[(xOffset + x*elr) + (yOffset + y*elr*3/2        )*triGridVertLength].toArray());
				}
				
				
			} else { //(y%2 == 1)
				
				//int x=0;
				
				posBuffer.put(triGridPos[(xOffset + 0*elr) + (yOffset + y*elr*3/2        )*triGridVertLength].toArray());
				
				for (int x=1; x<hexLength-1; x++) {
					
					if (x%2 == 0) {
						posBuffer.put(triGridPos[(xOffset + x*elr) + (yOffset + y*elr*3/2        )*triGridVertLength].toArray());
						hexagonCenterIndices.add((xOffset + x*elr) + (yOffset + y*elr*3/2 + elr  )*triGridVertLength);
					} else {
						posBuffer.put(triGridPos[(xOffset + x*elr) + (yOffset + y*elr*3/2 + elr/2)*triGridVertLength].toArray());
					}
					
				}
				int x=hexLength-1;
				if (x%2 == 0) {
					posBuffer.put(triGridPos[(xOffset + x*elr) + (yOffset + y*elr*3/2        )*triGridVertLength].toArray());
				} else {
					posBuffer.put(triGridPos[(xOffset + x*elr) + (yOffset + y*elr*3/2 + elr/2)*triGridVertLength].toArray());
				}
				
			}
			
		}
		
		this.posBuffer = posBuffer;
		
		posBuffer.flip();
		
		return posBuffer;
	}
	
	private IntBuffer createElementBuffer() {
		
		IntBuffer elementBuffer = BufferUtils.createIntBuffer((hexLength / 2 - 1) * (hexWidth - 1) * 7 );
		
		for (int y=0; y<hexWidth-1; y++) {
			
			if(y%2 == 0) {
				for (int x=0; x<hexLength/2 - 1; x++) {
					
					elementBuffer.put(getHexagonIndexArray(x, y, 0));
					elementBuffer.put(PRI);
					
				}
			} else {	// y%2 == 1
				for (int x=0; x<hexLength/2 - 1; x++) {
					
					elementBuffer.put(getHexagonIndexArray(x, y, 1));
					elementBuffer.put(PRI);
					
				}
			}
			
		}
		
		this.indexBuffer = elementBuffer;
		
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
	
	
	//********************************** util methods *********************************************
	public int[] getHexagonIndexArray(int x, int y, int yMod2) {
		
		return new int[] {	0           + 2*x + hexLength*y + yMod2,
							1           + 2*x + hexLength*y + yMod2,
							2           + 2*x + hexLength*y + yMod2,
							hexLength+2 + 2*x + hexLength*y + yMod2,
							hexLength+1 + 2*x + hexLength*y + yMod2,
							hexLength   + 2*x + hexLength*y + yMod2
		};
		
	}
	
	//********************************** other stuff **********************************************
	
	public Vector3f[] getVertices() {
		
		Vector3f[] vertices = new Vector3f[posBuffer.capacity() / 3];
		for (int i=0; i<vertices.length; i++) {
			vertices[i] = new Vector3f(posBuffer.get(3*i), posBuffer.get(3*i+1), posBuffer.get(3*i+2));
		}
		
		return vertices;
	}
	
	/** 
	 * the vertex of a hexagon's center has an index in the vertex-array of the triangle grid
	 * @return an array containing all these indices
	 */
	public int[] getHexCenterIndices() {
		
		int[] hexCenterIndices = new int[hexagonCenterIndices.size()];
		for (int i=0; i<hexCenterIndices.length; i++) {
			hexCenterIndices[i] = hexagonCenterIndices.get(i);
		}
		return hexCenterIndices;
		
	}
	
	//Compute a center point for every vertex. Will be used to compute the selectedTile
	public Vector3f[] getCenterVertices() {
		
		Vector3f[] centerVertices = new Vector3f[indexBuffer.capacity() / 7];
		
		float x = 0f;
		float y = 0f;
		float z = 0f;
		
		for (int i = 0; i < indexBuffer.capacity(); ++i) {
			
			int vertexIndex = indexBuffer.get(i);
			
			if(vertexIndex == PRI) {
				
				centerVertices[i / 7] = new Vector3f(x / 6, y / 6, z / 6);
				
				x = 0f;
				y = 0f;
				z = 0f;
				
				continue;
				
			}
			
			x += posBuffer.get(vertexIndex * 3);
			y += posBuffer.get(vertexIndex * 3 + 1);
			z += posBuffer.get(vertexIndex * 3 + 2);
			
		}
		
		return centerVertices;
		
	}
	
	
	private int[] getIndexArrayByID(int index) {
		
		if (index < 0) {
			return getIndexArrayByID(0);
		}
		
		
		if (index >= hexLength * hexWidth) {
			return getIndexArrayByID(hexLength * hexWidth - 1);
		}
		
		
		int[] indices = new int[7];
		
		int positionInElementArray = index * 7;
		
		
		for (int i = 0; i < 7; ++i) {
			
			indices[i] = indexBuffer.get(positionInElementArray + i);
			
		}
		
		
		return indices;
		
	}
	
	
	public void display(int index) {
		
		IntBuffer elementBuffer = CustomBufferUtils.createIntBuffer(getIndexArrayByID(index));
		
		this.setElementArrayData(elementBuffer);
		
	}
	
	
	public void display(int[] tiles) {
		
		IntBuffer elementBuffer = BufferUtils.createIntBuffer(tiles.length * 7);
		
		for (int index : tiles) {
			
			elementBuffer.put(getIndexArrayByID(index));
			
		}
		
		elementBuffer.flip();
		
		this.setElementArrayData(elementBuffer);
		
	}
	
	
	public void displayAll() {
		this.setElementArrayData(this.indexBuffer);
	}
	
	
	
	public void onDrawStart() {
		super.onDrawStart();
		
		glLineWidth(0.1f);
		
		glEnable(GL_PRIMITIVE_RESTART);
		glPrimitiveRestartIndex(PRI);
	}
	
	
	public void onDrawStop() {
		super.onDrawStop();
		
		glDisable(GL_PRIMITIVE_RESTART);
		
	}

}
