package assets.meshes;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import assets.buffers.VertexBuffer;
import assets.meshes.MeshConst.BufferLayout;
import assets.meshes.boundingBox.BoundingBox;
import assets.meshes.geometry.Vertex;
import assets.models.VertexArrayObject;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.*;

public class Mesh {
	
	//The vertex array object that holds the references to the vertex data
	private VertexArrayObject vao = null;
	
	private BufferLayout layout = null;
	
	private int vertexLayout;
	
	
	private List<Vertex> vertices = null;
	
	private List<Integer> indices = null;
	
	private IntBuffer indexBuffer;
	
	
	private BoundingBox boundingBox;	
	
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
		
		System.out.println(indices.toString());
		
		this.boundingBox = new BoundingBox(vertices);
		
	}
	
	
	/**
	 * 
	 * Constructor that accepts data stored in arrays
	 * 
	 * @param vertices The vertices of the mesh
	 * @param indices An array of triangles in this mesh
	 */
	public Mesh(Vertex[] vertices, int[] indices) {
		
		//TODO: Change it if the most common operations make LinkedLists more efficient
		this.vertices = new ArrayList<Vertex>();
		this.indices = new ArrayList<Integer>();
		
		this.boundingBox = new BoundingBox(vertices, this.vertices);
		
		this.toList(indices);
		
		System.out.println(indices.toString());
		
	}
	
	
	/**
	 * 
	 * Constructor that accepts data stored in arrays
	 * 
	 * @param vertices The vertices of the mesh
	 * @param indices An array of triangles in this mesh
	 */
	public Mesh(List<Vertex> vertices, int[] indices) {
		
		this.vertices = vertices;
		this.indices = new ArrayList<Integer>();
		
		this.boundingBox = new BoundingBox(vertices);
		
		this.toList(indices);
		
		System.out.println(indices.toString());
		
	}
	
	
	public Mesh(FloatBuffer vertexData, int layout) {
		//TODO
	}
	
	
	private void toList(int[] array) {
		
		if (this.indices == null) {
			this.indices = new ArrayList<Integer>();
		}
		
		for (int i : array) {
			this.indices.add(i);
		}
		
	}
	
	
	/**
	 * 
	 * Stores the data of this mesh on the GPU. The way the data is stored
	 * can be specified by the parameters.
	 * 
	 * @param layout The layout in which the data is stored in buffers. 
	 * The data can be stored in interleaved blocks or across multiple buffers 
	 * 
	 * @param vertexLayout Specifies the way the vertex data is being stored. Every 4 Bits describe
	 * the number of values that represent one attribute of the vertex
	 * 
	 * @param flag The way the data will be accessed in future operations. Massively impacts the speed if used correctly.
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
		
		this.indexBuffer = CustomBufferUtils.createIntBuffer(indices);
		
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
		
	}
	
	
	/**
	 * 
	 * TODO
	 * 
	 * @param index
	 */
	public void removeVertex(int index) {
		//TODO		
	}
	
	
	public void render() {
		vao.enableVertexAttribArray();
		
		glDrawElements(drawMode, indexBuffer);
		
		vao.disableVertexAttribArray();
	}
	
}
