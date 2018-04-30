package assets.meshes;

import java.util.ArrayList;
import java.util.LinkedList;

import assets.meshes.boundingBox.BoundingBox;
import assets.meshes.geometry.Vertex;
import assets.models.VertexArrayObject;

import static org.lwjgl.opengl.GL11.*;

public class Mesh {
	
	private VertexArrayObject vao;
	
	private LinkedList<Vertex> vertices;
	
	private LinkedList<Integer> indices;
	
	private BoundingBox boundingBox;
	
	private int drawMode;
	
	private Mesh() {
		vertices = new LinkedList<Vertex>();
		indices = new LinkedList<Integer>();
		boundingBox = new BoundingBox(vertices);
	}
	
	
	public Mesh(LinkedList<Vertex> vertices, LinkedList<Integer> indices, int drawMode) {
		this.vertices = vertices;
		this.indices = indices;
		this.drawMode = drawMode;
	}
	
	
	public Mesh(ArrayList<Vertex> vertices, ArrayList<Integer> indices) {
		this();
		
		Integer[] i = (Integer[])indices.toArray();
	}
	
	
	public Mesh(LinkedList<Vertex> vertices, LinkedList<Integer> indices) {
		this(vertices, indices, GL_TRIANGLES);
	}
	
	
	public Mesh(LinkedList<Vertex> vertices, int[] indices) {
		
		this.vertices = vertices;
		this.indices = new LinkedList<Integer>();
		
		for(int index : indices) {
			this.indices.add(index);
		}
		
	}
	
	
	public Mesh(Vertex[] vertices, int[] indices, int drawMode) {
		this();
		
		for(Vertex vertex : vertices) {
			this.vertices.add(vertex);
		}
		
		for(int index : indices) {
			this.indices.add(index);
		}
		
	}
	

}
