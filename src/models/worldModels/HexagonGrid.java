package models.worldModels;

import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;
import assets.models.Element_Model;
import math.vectors.Vector3f;
import models.seeds.SuperGrid;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
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
		
		super(GL_LINE_STRIP);
		
		this.superGrid = superGrid;
		
		length = superGrid.getLengthInHexagons();
		width  = superGrid.getWidthInHexagons();
		
		this.colors = colors;
		
		processVerticesAndElementBuffer();
		
	}
	
	//****************************** methods ********************************
	
	private void processVerticesAndElementBuffer() {
		
		vertices = new ArrayList<>(length*width*8);
		
		IntBuffer elementBuffer = extractVectorsFromSuperGrid();
		
		Vertex[] verticesArray = verticesListToArray();
		
		SuperGrid.adjustToTerrainAndSea(verticesArray);
		
		setData(verticesArray, elementBuffer);
		
	}
	
	private IntBuffer extractVectorsFromSuperGrid() {
		
		IntBuffer elementBuffer = BufferUtils.createIntBuffer(length*width*9);
		
		for (int y=0; y<1; y++) {
			for (int x=0; x<1; x++) {
				addHexagon(elementBuffer, x, y);
			}
		}
		
		elementBuffer.flip();
		
		return elementBuffer;
		
	}
	
	/**
	 * this method sets vertices = null
	 * @return an array containing all elements of the list vertices
	 */
	private Vertex[] verticesListToArray() {
		
		Vertex[] array = new Vertex[vertices.size()];
		
		for (int i=0; i<array.length; i++) {
			array[i] = vertices.get(i);
		}
		
		vertices = null;
		
		return array;
		
	}
	
	//********************
	
	private void addHexagon(IntBuffer elementBuffer, int x, int y) {
		
		Vector3f[] hexBorderPositions = superGrid.getHexBorder(y*length + x);
		
		vertices.add(new Vertex(superGrid.getHexCenter(x+y*length), colors[x+y*length]));
		elementBuffer.put((x+y*length)*9);
		
		for (int v=0; v<hexBorderPositions.length; v++) {
			
			vertices.add(new Vertex(hexBorderPositions[v], colors[x+y*length]));
			elementBuffer.put((x+y*length)*9 + 1 + v);
			
		}
		
		vertices.add(new Vertex(hexBorderPositions[0], colors[x+y*length]));
		elementBuffer.put((x+y*length)*9 + 8);
		
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