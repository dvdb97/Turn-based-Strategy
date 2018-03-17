package models.worldModels;

import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;
import assets.models.Element_Model;
import math.vectors.Vector3f;
import models.seeds.SuperGrid;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;


public class HexagonBorderGrid extends Element_Model {
	
	private final float Z_SHIFT = 0.02f;
	
	private SuperGrid superGrid;
	
	//dimensions
	private int length;
	private int width;
	
	private ArrayList<Vector3f> vectors;
	
	private Color color;
	
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
		
		vectors = new ArrayList<>((length+1)*2 * (width+1));
		
		elementArrays = new int[length*width][6];
		
		for (int y=0; y<width; y++) {
			for (int x=0; x<length; x++) {
				
				addHexagon(x, y);
				
			}
		}
		
		Vertex[] vertices = new Vertex[vectors.size()];
		
		Vector3f zShift = new Vector3f(0, 0, Z_SHIFT);
		
		for (int v=0; v<vertices.length; v++) {
			
			vertices[v] = new Vertex(vectors.get(v).plusEQ(zShift), color);
			
		}
		
		setData(vertices, elementBuffer);
		
		vectors = null;
	}
	
	
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
		
		this.setElementArrayData(elementBuffer);
		
	}
	
	public void displayAll() {
		
		elementBuffer = BufferUtils.createIntBuffer(length*width*7);
		
		for (int h=0; h<length*width; h++) {
			elementBuffer.put(elementArrays[h]);
			elementBuffer.put(PRI);
		}
		
		elementBuffer.flip();
		
		this.setElementArrayData(elementBuffer);
		
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
	public void onDrawStop() {
		super.onDrawStop();
		
		glDisable(GL_PRIMITIVE_RESTART);
		
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
