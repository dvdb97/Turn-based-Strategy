package assets.meshes;

import java.nio.FloatBuffer;
import java.util.LinkedList;

import org.lwjgl.BufferUtils;

import assets.buffers.Buffer;
import assets.buffers.VertexBuffer;
import assets.material.Material;
import assets.material.StandardMaterial;
import assets.meshes.boundingBox.BoundingBox;
import assets.meshes.geometry.Vertex;
import assets.models.VertexArrayObject;

import static org.lwjgl.opengl.GL11.*;

public class Mesh {
	
	public static enum BufferLayout {
		
		//All the data needed for one vertex is stored at the same location. E.g.: ((PPP)(CCCC)(TT)(NNN))((PPP)(CCCC)(TT)(NNN)) 
		INTERLEAVED, 
		
		//All the data that represents one vertex attribute is stored at the same location. E.g.: ((PPP)(PPP)(PPP))((CCCC)(CCCC)(CCCC))...
		BATCHED, 
		
		//There are multiple buffers, one for each attribute
		MULTIPLE_BUFFERS
		
	}
	
	
	//The vertex array object that holds the references to the vertex data
	private VertexArrayObject vao;
	
	private BufferLayout layout;
	
	private LinkedList<Vertex> vertices;
	
	private LinkedList<Integer> indices;
	
	private int drawMode;
	
	
	private BoundingBox boundingBox;
	
	public final Transformable transformable;
	
	public final Material material;
	
	
	public Mesh(LinkedList<Vertex> vertices, LinkedList<Integer> indices, BufferLayout layout) {
		this.vertices = vertices;
		this.indices = indices;
		
		this.drawMode = GL_TRIANGLES;
		
		
		boundingBox = new BoundingBox(vertices);
		
		transformable = new Transformable();
		
		material = new StandardMaterial();
		
	}
	
	
	private void storeData(LinkedList<Vertex> vertices, LinkedList<Integer> indices, BufferLayout layout) {
		
		if (layout == BufferLayout.INTERLEAVED) {
			
			FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.getLast().getDataSize());
			
			for (Vertex vertex : vertices) {
				vertex.toDataBundle(buffer);
			}
			
			buffer.flip();
			
			VertexBuffer vertexBuffer = new VertexBuffer();
			
			vertexBuffer.setBufferStorage(buffer, Buffer.DYNAMIC_STORAGE);
			
		}
		
	}
	
	
	private void storeData(Vertex[] vertices, int[] indices, BufferLayout layout) {
		
		
	}

}
