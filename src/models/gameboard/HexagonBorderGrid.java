package models.gameboard;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import assets.meshes.Mesh;
import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;
import assets.scene.Scene;
import assets.shaders.ShaderLoader;
import assets.shaders.ShaderProgram;
import assets.shaders.standardShaders.StandardShader;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import models.seeds.SuperGrid;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;


public class HexagonBorderGrid extends Mesh {
	
	private StandardShader shader;
	
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
	
	public HexagonBorderGrid(SuperGrid superGrid, Material material) {
		super(GL_LINE_LOOP);
		this.setMaterial(material);
		
		this.shader = StandardShader.create();
		
		this.superGrid = superGrid;
		
		length = superGrid.getLengthInHexagons();
		width  = superGrid.getWidthInHexagons();
		
		this.color = material.color;
		
		processVerticesAndElementBuffer();
		
	}
	
	
	//********************
	
	
	private void processVerticesAndElementBuffer() {
		
		vectors = new ArrayList<>((length+1)*2 * (width+1));
		
		extractVectorsFromSuperGrid();
		
		Vertex[] vertices = createVertices();
		
		SuperGrid.adjustToTerrainAndSea(vertices);
		
		vectors = null;
		
		setData(vertices, elementBuffer, false);
	}
	
	private void extractVectorsFromSuperGrid() {
				
		elementArrays = new int[length*width][6];
		
		for (int y=0; y<width; y++) {
			for (int x=0; x<length; x++) {
				addHexagon(x, y);
			}
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
	public void onDrawStart(Scene scene) {
		this.onDrawStart(scene.getCamera(), scene.getLightSource());
	}
	
	
	public void render(Scene scene, Color color) {
		this.getMaterial().color = color;
		render(scene);
		getMaterial().color = this.color;
	}
	
	
	@Override
	public void onDrawEnd(Scene scene) {
		this.onDrawEnd(scene.getCamera(), scene.getLightSource());
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


	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		shader.bind();
		shader.setCamera(camera);
		shader.setModelMatrix(this.getTransformable().getTransformationMatrix());
		shader.setMaterial(getMaterial());
		glLineWidth(0.1f);
		
		glEnable(GL_PRIMITIVE_RESTART);
		glPrimitiveRestartIndex(PRI);
	}
	
	
	@Override
	protected void onDrawEnd(Camera camera, DirectionalLight light) {
		glDisable(GL_PRIMITIVE_RESTART);
	}
	
}
