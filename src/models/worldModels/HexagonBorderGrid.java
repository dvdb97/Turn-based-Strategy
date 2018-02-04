package models.worldModels;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;


import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;
import assets.models.Element_Model;
import math.vectors.Vector3f;
import utils.CustomBufferUtils;

public class HexagonBorderGrid extends Element_Model {
	
	private int length, width;
	
	private int elr;			//edge length relation = edgeLengthHexagons / edgeLengthTriangles
	
	private int xOffset, yOffset;
	
	private final int PRI = -1;					//primitive restart index
	
	private int hexLength, hexWidth;			//length and width measured in for a hexagon grid relevant vertices
	
	private int triGridVertLength;
	
	private IntBuffer indexBuffer;
	
	private List<Vertex> vertices;
	
	private List<Integer> hexagonCenterIndices;
	
	private final Color color = new Color(0.2f, 0.2f, 0.2f, 1);
	
	//********************************** constructor ************************************
	
	/**
	 * 
	 * @param triangleGrid TriangleGrid this hexagonal grid is based on
	 * @param xOffset
	 * @param yOffset 
	 * @param edgeLengthRelation edge length of hexagons / edge length of triangles
	 */
	public HexagonBorderGrid(TriangleGrid triangleGrid, int xOffset, int yOffset, int edgeLengthRelation) {
		
		super(GL_LINE_LOOP);
		
		if (edgeLengthRelation < 1 || triangleGrid == null || xOffset < 0 || yOffset < 0) {
			throw new IllegalArgumentException();
		}
		
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.elr = edgeLengthRelation;
		
		triGridVertLength = triangleGrid.getLength();
		
		hexLength =  (triGridVertLength - 2 * xOffset - 1) / elr + 1;
		hexWidth  = ((triangleGrid.getWidth()  - 2 * yOffset    ) / elr - 1)*2/3 + 1;
		
		length = hexLength / 2 - 1;
		width  = hexWidth -1;

		vertices = new ArrayList<>(hexLength*hexWidth);
		
		processVertices(triangleGrid);
		processHexCenterIndices();
		createIndexBuffer();
		
		//TODO: Element_Model should accept a list of vertices instead of just arrays
		setData(vertexListToArray(), indexBuffer);
		
	}
	
	
	//********************************** prime methods ***********************************
	
	private void processVertices(TriangleGrid triangleGrid) {
		
		Vertex[] triGridVertices = prepareTriGridVertexArray(triangleGrid);
		
		for (int y=0; y<hexWidth; y++) {
			
			for (int x=0; x<hexLength; x++) {
				
				if (x%2 == y%2) {   //(x%2 == 0 && y%2 == 0) || (x%2 == 1 && y%2 == 1)
					
					vertices.add(triGridVertices[(xOffset + x*elr) + (yOffset + y*elr*3/2 + elr/2)*triGridVertLength]);
					
				} else {            //(x%2 == 1 && y%2 == 0) || (x%2 == 0 && y%2 == 1)
					
					vertices.add(triGridVertices[(xOffset + x*elr) + (yOffset + y*elr*3/2        )*triGridVertLength]);
				}
				
			}
			
		}
		
	}
	
	
	
	private void processHexCenterIndices() {
		
		hexagonCenterIndices = new ArrayList<Integer>(length * width);
		
		for (int y=0; y<hexWidth; y++) {
			
			for (int x=1; x<hexLength-1; x++) {
				
				if (x%2 != y%2) {
					hexagonCenterIndices.add((xOffset + x*elr) + (yOffset + y*elr*3/2 + elr  )*triGridVertLength);
				}
				
			}
			
		}
		
	}
	
	
	
	private void createIndexBuffer() {
		
		indexBuffer = BufferUtils.createIntBuffer(length * width * 7 );
		
		for (int y=0; y<width; y++) {
			
			for (int x=0; x<length; x++) {
				
				indexBuffer.put(getHexagonIndexArray(x, y, y%2));
				indexBuffer.put(PRI);
				
			}
			
		}
		
		indexBuffer.flip();
		
	}	
	
	//********************************** util methods *********************************************
	
	private Vertex[] prepareTriGridVertexArray(TriangleGrid triangleGrid) {
		
		Vector3f[] triGridPos = triangleGrid.getPosArray();
		Vertex[] triGridVertices = new Vertex[triGridPos.length];
		
		float delta = 0.005f*elr;
		
		for (int i=0; i<triGridVertices.length; i++) {
			
			triGridVertices[i] = new Vertex(triGridPos[i], color);
			
			float c;
			float d = triGridVertices[i].getC();
			if (d < 0) {
				c = delta;
			} else {
				c = d + delta;
			}
			triGridVertices[i].setC(c);
			
		}
		
		return triGridVertices;
		
	}
	
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
		
		Vector3f[] posData = new Vector3f[vertices.size()];
		
		for (int i=0; i<posData.length; i++) {
			posData[i] = vertices.get(i).getPosition();
		}
		
		return posData;
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
		
	//***************************** display... ***************************************
	
	
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
	
	
	
	//*********************************************************************************
	
	@Override
	public void onDrawStart() {
		super.onDrawStart();
		
		glLineWidth(0.1f);
		
		glEnable(GL_PRIMITIVE_RESTART);
		glPrimitiveRestartIndex(PRI);
	}
	
	@Override
	public void onDrawStop() {
		super.onDrawStop();
		
		glDisable(GL_PRIMITIVE_RESTART);
		
	}
	
	
	private Vertex[] vertexListToArray() {
		
		Vertex[] array = new Vertex[vertices.size()];
		
		for (int v=0; v<array.length; v++) {
			array[v] = vertices.get(v);
		}
		
		return array;
		
	}
	
	
	private int[] getIndexArrayByID(int index) {
		
		if (index < 0) {
			return getIndexArrayByID(0);
		}
		
		
		if (index >= indexBuffer.capacity()/7) {
			return getIndexArrayByID(indexBuffer.capacity()/7 - 1);
		}
		
		int[] indices = new int[7];
		
		int positionInElementArray = index * 7;
		
		
		for (int i = 0; i < 7; ++i) {
			
			indices[i] = indexBuffer.get(positionInElementArray + i);
			
		}
		
		
		return indices;
		
	}
	
	//************************************ get *****************************
	
	/**
	 * @return the length of this grid in hexagons
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * @return the width of this grid in hexagons
	 */
	public int getWidth() {
		return width;
	}
	
}
