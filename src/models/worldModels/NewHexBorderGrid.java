package models.worldModels;

import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;
import assets.models.Element_Model;
import math.vectors.Vector3f;
import models.seeds.SuperGrid;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;

public class NewHexBorderGrid extends Element_Model {
	
	SuperGrid superGrid;
	
	//dimensions
	private int length;
	private int width;
	
	private ArrayList<Vector3f> vectors;
	
	private Color color;
	
	private IntBuffer elementBuffer;
	
	//TODO: implement an array that contains every hexagon's element buffer, helpful for section "display"
	private int[][] elementArrays;
	
	private static final int PRI = -1;
	
	
	//******************** constructor ***************************
	
	public NewHexBorderGrid(SuperGrid superGrid, Color color) {
		
		super(GL_LINE_LOOP);
		
		this.superGrid = superGrid;
		
		length = superGrid.getLengthInHexagons();
		width  = superGrid.getWidthInHexagons();
		
		this.color = color;
		
		processPositionsAndElementBuffer();
		
	}
	
	
	//********************
	
	
	private void processPositionsAndElementBuffer() {
		
		vectors = new ArrayList<>((length+1)*2 * (width+1));
		
		elementBuffer = BufferUtils.createIntBuffer(length*width*7);
		
		for (int x=0; x<length; x++) {
			for (int y=0; y<width; y++) {
				
				addHexagon(x, y);
				
			}
		}
		
		Vertex[] vertices = new Vertex[vectors.size()];
		
		for (int v=0; v<vertices.length; v++) {
			
			vertices[v] = new Vertex(vectors.get(v), color);
			
		}
		
		setData(vertices, elementBuffer);
		
	}
	
	
	private void addHexagon(int x, int y) {
		
		Vector3f[] hexBorderPositions = superGrid.getHexBorder(y*length + x);
		Vector3f vec;
		
		for (int v=0; v<hexBorderPositions.length; v++) {
			
			vec = hexBorderPositions[v];
			
			if (vectors.contains(vec)) {
				vectors.add(vec);
			}
			
			elementBuffer.put(vectors.indexOf(vec));
			
			
		}
		
		elementBuffer.put(PRI);
		
	}
	
	
	//******************** display *******************************
	
	public void display(int index) {
		
		
	}
	
	public void displayAll() {
		
		
		
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