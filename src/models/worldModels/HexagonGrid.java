package models.worldModels;

import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;
import assets.models.Element_Model;
import mapModes.MapMode;
import math.vectors.Vector3f;
import models.seeds.SuperGrid;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;


public class HexagonGrid extends Element_Model {
	
	private SuperGrid superGrid;
	
	//dimensions
	private int length, width;
	
	private Vertex[] vertices;
	
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
		
		vertices = new Vertex[length*width*7];
		
		IntBuffer elementBuffer = extractVectorsFromSuperGrid();
		
		SuperGrid.adjustToTerrainAndSea(vertices);
		
		setData(vertices, elementBuffer);
		
	}
	
	private IntBuffer extractVectorsFromSuperGrid() {
		
		IntBuffer elementBuffer = BufferUtils.createIntBuffer(length*width*9);
		
		for (int y=0; y<width; y++) {
			for (int x=0; x<length; x++) {
				addHexagon(elementBuffer, x + y*length);
			}
		}
		
		elementBuffer.flip();
		
		return elementBuffer;
		
	}
	
	
	//********************
	
	private void addHexagon(IntBuffer elementBuffer, int i) {
		
		Vector3f[] hexBorderPositions = superGrid.getHexBorder(i);
		
		vertices[i*7] = new Vertex(superGrid.getHexCenter(i), colors[i]);
		elementBuffer.put(i*7);
		
		for (int v=0; v<hexBorderPositions.length; v++) {
			
			vertices[i*7 + v+1] = new Vertex(hexBorderPositions[v], colors[i]);
			elementBuffer.put(i*7 + v+1);

		}
		
		elementBuffer.put(i*7 + 1);

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
	
	public void setColor(MapMode mapMode) {
		
		for (int i=0; i<length*width; i++) {
			this.colors[i] = mapMode.get(i);
		}
		updateColor();
	}
	
	public void setColor(int[] indices, Color color) {
		
		for (int i=0; i<indices.length; i++) {
			
			colors[indices[i]] = color;
			
		}
		updateColor();
	}
	
	
	//************************************ draw start stop ***************************
	
	@Override
	public void onDrawStart() {
		super.onDrawStart();
		
		glEnable(GL_PRIMITIVE_RESTART);
		glPrimitiveRestartIndex(PRI);
	}
	
	@Override
	public void onDrawStop() {
		super.onDrawStop();
		
		glDisable(GL_PRIMITIVE_RESTART);
		
	}
	
	
}