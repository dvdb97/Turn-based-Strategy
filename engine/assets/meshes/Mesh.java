package assets.meshes;

import java.util.ArrayList;
import java.util.LinkedList;

import assets.meshes.boundingBox.BoundingBox;
import assets.meshes.geometry.Vertex;
import assets.meshes.geometry.VertexLegacy;
import assets.models.VertexArrayObject;

import static org.lwjgl.opengl.GL11.*;

public class Mesh {
	
	//The vertex array object that holds the references to the vertex data
	private VertexArrayObject vao;
	
	private LinkedList<Vertex> vertices;
	
	private LinkedList<Integer> indices;
	
	private BoundingBox boundingBox;
	
	private int drawMode;
	
	
	public Mesh(LinkedList<Vertex> vertices, LinkedList<Integer> indices) {
		this.vertices = vertices;
		this.indices = indices;
		this.drawMode = GL_TRIANGLES;
		boundingBox = new BoundingBox();
	}
	

}
