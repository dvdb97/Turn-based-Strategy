package assets.meshes;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.BufferUtils;

import assets.buffers.Buffer;
import assets.buffers.VertexBuffer;
import assets.material.Material;
import assets.material.StandardMaterial;
import assets.meshes.boundingBox.BoundingBox;
import assets.meshes.geometry.Vertex;
import assets.models.VertexArrayObject;
import rendering.shaders.ShaderProgram;

import static org.lwjgl.opengl.GL11.*;

public class Mesh implements IRenderable {	
	
	//The vertex array object that holds the references to the vertex data
	private VertexArrayObject vao;
	
	
	private LinkedList<Vertex> vertices;
	
	private LinkedList<Integer> indices;
	
	private int drawMode;
	
	
	private BoundingBox boundingBox;
	
	public final Transformable transformable;
	
	public final Material material;
	
	
	public Mesh(LinkedList<Vertex> vertices, LinkedList<Integer> indices, ShaderProgram shader) {
		this.vertices = vertices;
		this.indices = indices;
		
		this.drawMode = GL_TRIANGLES;
		
		
		boundingBox = new BoundingBox(vertices);
		
		transformable = new Transformable();
		
		material = new StandardMaterial();
		
		
		
	}


	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

}
