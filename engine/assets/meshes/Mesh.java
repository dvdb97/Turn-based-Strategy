package assets.meshes;

import java.nio.FloatBuffer;
import java.util.LinkedList;

import assets.material.Material;
import assets.material.StandardMaterial;
import assets.meshes.MeshConst.BufferLayout;
import assets.meshes.boundingBox.BoundingBox;
import assets.meshes.geometry.Vertex;
import assets.models.VertexArrayObject;
import rendering.shaders.ShaderProgram;

import static org.lwjgl.opengl.GL11.*;

public class Mesh implements IRenderable {	
	
	//The vertex array object that holds the references to the vertex data
	private VertexArrayObject vao;
	
	private BufferLayout layout;
	
	
	private LinkedList<Vertex> vertices;
	
	private LinkedList<Integer> indices;
	
	private int drawMode;
	
	
	private BoundingBox boundingBox;
	
	public final Transformable transform;
	
	public final ShaderProgram shader;
	
	public final Material material;
	
	
	/**
	 * 
	 * Default constructor
	 * 
	 * @param drawMode Specifies what will be rendered (e.g. GL_TRIANGLES or GL_LINES)
	 * @param layout The layout in which the vertex data should be stored
	 * @param shader The shader that will be used to render this model
	 */
	public Mesh(int drawMode, BufferLayout layout, ShaderProgram shader) {
		
		this.drawMode = GL_TRIANGLES;
		
		transform = new Transformable();
		
		material = new StandardMaterial();
		
		this.shader = shader;
		
	}
	
	
	public Mesh(BufferLayout layout, LinkedList<Vertex> vertices, LinkedList<Integer> indices, ShaderProgram shader) {
		this(GL_TRIANGLES, layout, shader);
		
		this.vertices = vertices;
		this.indices = indices;
		
		boundingBox = new BoundingBox(vertices);
		
		this.vao = new VertexArrayObject(layout);
		
	}
	
	
	public void replaceVertex(int index, Vertex newVertex) {
		
		if (vao.getVertexSize() != newVertex.getDataSize()) {
			return;
		}
		
		
		
	}
	
	
	public void replaceColor(int offset, int range, FloatBuffer data) {
		
	}
	


	@Override
	public void render() {
		shader.use();
		
		vao.enableVertexAttribArray();
		
		//glDrawElements(drawMode, indices);
		
		vao.disableVertexAttribArray();
		
		shader.disable();		
	}

}
