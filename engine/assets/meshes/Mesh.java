package assets.meshes;

import java.util.ArrayList;
import java.util.LinkedList;

import assets.meshes.boundingBox.BoundingBox;
import assets.meshes.geometry.VertexLegacy;
import assets.models.VertexArrayObject;

import static org.lwjgl.opengl.GL11.*;

public class Mesh {
	
	//The vertex array object that holds the references to the vertex data
	private VertexArrayObject vao;
	
	private LinkedList<VertexLegacy> vertices;
	
	private LinkedList<Integer> indices;
	
	private BoundingBox boundingBox;
	
	private int drawMode;
	
	private Mesh() {
		vertices = new LinkedList<VertexLegacy>();
		indices = new LinkedList<Integer>();
		boundingBox = new BoundingBox(vertices);
	}
	
	
	public Mesh(LinkedList<VertexLegacy> vertices, LinkedList<Integer> indices, int drawMode) {
		this.vertices = vertices;
		this.indices = indices;
		this.drawMode = drawMode;
	}
	
	
	public Mesh(ArrayList<VertexLegacy> vertices, ArrayList<Integer> indices) {
		this();
		
		for (VertexLegacy vertex : vertices) {
			this.vertices.add(vertex);
		}
		
		for (int i : indices) {
			this.indices.add(i);
		}
		
		
	}
	
	
	public Mesh(LinkedList<VertexLegacy> vertices, LinkedList<Integer> indices) {
		this(vertices, indices, GL_TRIANGLES);
	}
	
	
	public Mesh(LinkedList<VertexLegacy> vertices, int[] indices) {
		
		this.vertices = vertices;
		this.indices = new LinkedList<Integer>();
		
		for(int index : indices) {
			this.indices.add(index);
		}
		
	}
	
	
	public Mesh(VertexLegacy[] vertices, int[] indices, int drawMode) {
		this();
		
		for(VertexLegacy vertex : vertices) {
			this.vertices.add(vertex);
		}
		
		for(int index : indices) {
			this.indices.add(index);
		}
		
	}
	

}
