package models.worldModels;

import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;
import assets.models.Element_Model;
import math.vectors.Vector3f;
import models.seeds.SuperGrid;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;


public class HexagonGrid extends Element_Model {
	
	private SuperGrid superGrid;
	
	//dimensions
	private int length, width;
	
	private ArrayList<Vertex> vertices;
	
	//others
	private Color[] colors;
	
	private static final int PRI = -1;
	
	//***************************** constructor *****************************
	
	public HexagonGrid(SuperGrid superGrid, Color[] colors) {
		
		super(GL_TRIANGLE_FAN);
		
		this.superGrid = superGrid;
		
		length = superGrid.getLengthInHexagons();
		width  = superGrid.getWidthInHexagons();
		
		this.colors = colors;
		
		processVerticesAndElementBuffer();
		
	}
	
	//****************************** methods ********************************
	
	private void processVerticesAndElementBuffer() {
		
		vertices = new ArrayList<>(length*width*7);
		
		IntBuffer elementBuffer = extractVectorsFromSuperGrid();
		
		SuperGrid.adjustToTerrainAndSea(vertices);
		
		vertices = null;
		
		setData(vertices, elementBuffer);
		
	}
	
	private IntBuffer extractVectorsFromSuperGrid() {
		
		IntBuffer elementBuffer = BufferUtils.createIntBuffer(length*width*8);
		
		for (int y=0; y<width; y++) {
			for (int x=0; x<length; x++) {
				addHexagon(elementBuffer, x, y);
			}
		}
		
		return elementBuffer;
		
	}
	
	//********************
	
	private void addHexagon(IntBuffer elementBuffer, int x, int y) {
		
		Vector3f[] hexBorderPositions = superGrid.getHexBorder(y*length + x);
		
		vertices.add(superGrid.getHexCenter(x+y*length));
		elementBuffer.put((x+y*length)*7);
		
		for (int v=0; v<hexBorderPositions.length; v++) {
			
			vertices.add(hexBorderPositions[v]);
			elementBuffer.put((x+y*length)*7 + v);
			
		}
		
		elementBuffer.put(PRI);
		
	}
	
	
	//****************************** update *********************************
	
	private void updateColor() {
		
		float[] colorsF = new float[colors.length*4];
		
		for (int i=0; i<colors.length; i++) {
			
			colorsF[i*4 + 0] = colors[i].getA();
			colorsF[i*4 + 1] = colors[i].getB();
			colorsF[i*4 + 2] = colors[i].getC();
			colorsF[i*4 + 3] = colors[i].getD();
			
		}
		
		FloatBuffer colorDataBuffer = CustomBufferUtils.createFloatBuffer(colorsF);
		
		setVertexColorData(colorDataBuffer, 4, GL_STATIC_DRAW);
		
	}
	
	
	//****************************** get & set ******************************
	
	
	public void setColor(int index, Color color) {
		
		colors[index] = color;
		updateColor();
		
	}
	
	public void setColor(Color[] colors) {
		
		for (int i=0; i<colors.length; i++) {
			this.colors[i] = colors[i];
		}
		updateColor();
	}
	
	public void setColor(int[] indices, Color color) {
		
		for (int i=0; i<indices.length; i++) {
			
			colors[indices[i]] = color;
			
		}
		updateColor();
	}
	
}