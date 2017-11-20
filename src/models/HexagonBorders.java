package models;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;
import assets.models.Element_Model;
import terrain.Generator;
import utils.Const;

public class HexagonBorders extends Element_Model {
	
	
	private float hexagonRadius;
	
	private int widthInHexagons;
	
	private int heightInHexagons;
	
	
	private Vertex[] vertices;
	
	private int primitiveRestartIndex = -1;
	
	//TODO: Temporary
	private Color color = new Color(1.0f, 1.0f, 0.0f, 1.0f);
	
	
	IntBuffer showAllIndexBuffer;
	

	//****************************** Constructors *************************************
	
	
	public HexagonBorders(float hexagonRadius, int widthInHexagons, int heightInHexagons, Generator generator) {
		super(GL_LINE_LOOP);
		
		
		this.hexagonRadius = hexagonRadius;
		
		this.widthInHexagons = widthInHexagons;
		
		this.heightInHexagons = heightInHexagons;
		
		vertices = new Vertex[widthInHexagons * heightInHexagons * 6];
		
		generateHexagonBorders();
		
	}
	
	
	//****************************** Data generation *************************************
	
	
	private void generateHexagonBorders() {
		
		for (int row = 0; row < heightInHexagons; ++row) {
			
			for (int col = 0; col < widthInHexagons; ++col) {
				
				generateHexagonBorder(row, col);
				
			}
			
		}
		
		extractVertexData();
		
	}
	
	
	private void generateHexagonBorder(int row, int col) {	
		
		
		float centerX = Const.SQRT3 * hexagonRadius * (0.5f + col + ((float)(row % 2) / 2));
		float centerY = hexagonRadius * (1.5f * row + 1);
		
		
		for (int corner = 0; corner < 6;  ++corner) {
			
			int currentVertexIndex = row * (widthInHexagons * 6) + (col * 6) + corner;
			
			float x = (float)(Math.sin(corner * (Math.PI / 3)) * hexagonRadius + centerX);
			float y = (float)(Math.cos(corner * (Math.PI / 3)) * hexagonRadius + centerY);	
			
			//TODO: Adapt the height of the vertex to the height of the map at this position!
			this.vertices[currentVertexIndex] = new Vertex(x, y, 0.0f, color);
			
		}
		
	}
	
	
	private void extractVertexData() {
		
		FloatBuffer posBuffer = BufferUtils.createFloatBuffer(vertices.length * 6 * 3);
		
		FloatBuffer colBuffer = BufferUtils.createFloatBuffer(vertices.length * 6 * 4);
		
		//Six indices per hexagon + 1 primitive restart index
		showAllIndexBuffer = BufferUtils.createIntBuffer(vertices.length * 7);
		int currentIndex = 0;
		
		
		for (Vertex vertex : vertices) {
		
			posBuffer.put(vertex.getPositionData());
			
			colBuffer.put(vertex.getColorData());
			
			if (currentIndex % 6 == 0 && currentIndex != 0) {
				showAllIndexBuffer.put(primitiveRestartIndex);	
			}
			
			showAllIndexBuffer.put(currentIndex++);
			
		}
		
		
		posBuffer.flip();
		
		colBuffer.flip();
		
		showAllIndexBuffer.flip();
		
		
		this.setVertexPositionData(posBuffer, 3, GL_STATIC_DRAW);
		
		this.setVertexColorData(colBuffer, 4, GL_STATIC_DRAW);
		
		this.setElementArrayData(showAllIndexBuffer);
		
	}
	
	
	//****************************** Interfaces *************************************
	
	
	private int[] generateIndices(int hexIndex) {
		
		int[] indices = new int[6];
		
		int firstIndex = hexIndex * 6;
		
		for (int i = 0; i < 6; ++i) {
			indices[i] = firstIndex++;
		}
		
		return indices;
		
	}
	
	
	//Shows the border of the hexagon with the given index
	public void show(int hexIndex) {
		IntBuffer elementBuffer = BufferUtils.createIntBuffer(6);
		
		int firstIndex = hexIndex * 6;
		
		for (int i = 0; i < 6; ++i) {
			elementBuffer.put(firstIndex++);
		}
		
		elementBuffer.flip();
		
		this.setElementArrayData(elementBuffer);
		
	}
	
	
	//Shows the border of the hexagon at the given position
	public void show(int col, int row) {
		
	}
	
	
	public void show(int[] hexIndices) {
		
		IntBuffer elementBuffer = BufferUtils.createIntBuffer(hexIndices.length * 7);
		
		for (int i = 0; i < hexIndices.length; ++i) {
			
			elementBuffer.put(generateIndices(hexIndices[i]));
			
			elementBuffer.put(primitiveRestartIndex);
			
		}
		
		elementBuffer.flip();
		
		this.setElementArrayData(elementBuffer);
		
	}
	
	
	//****************************** Override *************************************
	
	@Override
	public void onDrawStart() {
		super.onDrawStart();
		
		glLineWidth(0.1f);
		
		glEnable(GL_PRIMITIVE_RESTART);
		glPrimitiveRestartIndex(primitiveRestartIndex);
	}
	
	@Override
	public void onDrawStop() {
		super.onDrawStop();
		
		glDisable(GL_PRIMITIVE_RESTART);
		
	}	

}