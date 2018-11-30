package models.worldModels;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import assets.meshes.Mesh;
import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;
import math.vectors.Vector3f;
import models.seeds.SuperGrid;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;


public class HexagonBorderGrid extends Mesh {
		
	private SuperGrid superGrid;
	
	//dimensions
	private int length;
	private int width;
	
	private ArrayList<Vector3f> vectors;
	
	private Color color;
	
	private List<Vertex> vertices;
	private IntBuffer elementBuffer;
	private int[][] elementArrays;
	
	private static final int PRI = -1;
	
	
	//******************** constructor ***************************
	
	public HexagonBorderGrid(SuperGrid superGrid, Color color) {
		
		super(GL_LINE_LOOP);
		
		this.superGrid = superGrid;
		
		length = superGrid.getLengthInHexagons();
		width  = superGrid.getWidthInHexagons();
		
		this.color = color;
		
		processVerticesAndElementBuffer();
		
	}
	
	
	//********************
	
	
	private void processVerticesAndElementBuffer() {

		Vertex[] triGridVertices = prepareTriGridVertexArray(triangleGrid);
		vectors = new ArrayList<>((length+1)*2 * (width+1));
		
		extractVectorsFromSuperGrid();
		
		Vertex[] vertices = createVertices();
		
		SuperGrid.adjustToTerrainAndSea(vertices);
		
		vectors = null;
		
		setData(vertices, elementBuffer);
		
	}
	
	private void extractVectorsFromSuperGrid() {
				
		elementArrays = new int[length*width][6];
		
		for (int y=0; y<width; y++) {
			for (int x=0; x<length; x++) {
				addHexagon(x, y);
			}
		}
	}

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
	}

	private Vertex[] createVertices() {
		
		Vertex[] vertices = new Vertex[vectors.size()];
		
		for (int v=0; v<vertices.length; v++) {
			vertices[v] = new Vertex(vectors.get(v), color);
		}
		
		return vertices;
		
	}
	
	//********************
	
	private void addHexagon(int x, int y) {
		
		Vector3f[] hexBorderPositions = superGrid.getHexBorder(y*length + x);
		Vector3f vec;
		
		for (int v=0; v<hexBorderPositions.length; v++) {
			
			vec = hexBorderPositions[v];
			
			if (!vectors.contains(vec)) {
				vectors.add(vec);
			}
			
			elementArrays[y*length + x][v] = vectors.indexOf(vec);
			
			
		}
		
	}
	
	
	//******************** display *******************************
	
	public void display(int index) {
		
		elementBuffer = CustomBufferUtils.createIntBuffer(elementArrays[index]);
		
		this.setIndexBuffer(elementBuffer);
		
	}
	
	public void displayAll() {
		
		elementBuffer = BufferUtils.createIntBuffer(length*width*7);
		
		for (int h=0; h<length*width; h++) {
			elementBuffer.put(elementArrays[h]);
			elementBuffer.put(PRI);
		}
		
		elementBuffer.flip();
		
		this.setIndexBuffer(elementBuffer);
		
	}
	
	
	//******************** others *********************************
	
	@Override
	public void onDrawStart() {
		super.onDrawStart();
		
		glLineWidth(0.1f);
		
		glEnable(GL_PRIMITIVE_RESTART);
		glPrimitiveRestartIndex(PRI);
	}
	
	@Override
	public void onDrawEnd() {
		super.onDrawEnd();
		
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
