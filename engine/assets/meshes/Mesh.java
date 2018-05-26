package assets.meshes;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import assets.buffers.VertexBuffer;
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
	private VertexArrayObject vao = null;
	
	private BufferLayout layout = null;
	
	private int vertexLayout;
	
	
	private List<Vertex> vertices = null;
	
	private List<Integer> indices = null;
	
	private int drawMode = GL_TRIANGLES;
	
	
	/**
	 * 
	 * Default constructor
	 * 
	 * @param vertices The vertices of the mesh
	 * @param indices A list of triangles in this mesh
	 */
	public Mesh(List<Vertex> vertices, List<Integer> indices) {
		
		this.vertices = vertices;
		this.indices = indices;
		
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * @param layout
	 * @param vertexLayout
	 * @param flag
	 */
	public void storeOnGPU(BufferLayout layout, int vertexLayout, int flag) {
				
		if (vao != null) {
			System.err.println("This mesh is already stored on the gpu!");
			
			return;
		}
		
		this.vao = new VertexArrayObject();
		
		this.layout = layout;
		
		this.vertexLayout = vertexLayout;
		
		
		if (layout == BufferLayout.INTERLEAVED) {
			
			VertexBuffer buffer = new VertexBuffer();
			
			buffer.storeDataInterleaved(vertices, vertexLayout, flag);
			
			vao.setVertexAttributePointers(buffer, vertexLayout);
			
		}
		
		else if (layout == BufferLayout.BLOCKWISE) {
			
			VertexBuffer buffer = new VertexBuffer();
			
			buffer.storeDataBlockwise(vertices, vertexLayout, flag);
			
			vao.setVertexAttributePointers(buffer, vertices.size(), vertexLayout);
			
		}
		
		else if (layout == BufferLayout.MULTIPLE_BUFFERS) {
			
			VertexBuffer[] buffers = VertexBuffer.storeDataMultipleBuffers(vertices, vertexLayout, flag);
			
			vao.setVertexAttributePointers(buffers, vertexLayout);
			
		}
		
	}
	
	
	/**
	 * 
	 * TODO
	 * 
	 * @param index
	 * @param newVertex
	 */
	public void replaceVertex(int index, Vertex newVertex) {
		
		if (vao.getVertexSize() != newVertex.getDataSize()) {
			return;
		}
		
		//TODO
		
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * @param offset
	 * @param range
	 * @param data
	 */
	public void replacePosition(int offset, int range, FloatBuffer data) {
		
	}
	
	
	/**
	 * 
	 * TODO
	 * 
	 * @param offset
	 * @param range
	 * @param data
	 */
	public void replaceColor(int offset, int range, FloatBuffer data) {
		
	}	

	
	/**
	 * 
	 * TODO
	 * 
	 * @param offset
	 * @param range
	 * @param data
	 */
	public void replaceTexCoords(int offset, int range, FloatBuffer data) {
		
	}
	
	
	/**
	 * 
	 * TODO
	 * 
	 * @param offset
	 * @param range
	 * @param data
	 */
	public void replaceNormals(int offset, int range, FloatBuffer data) {
		
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
