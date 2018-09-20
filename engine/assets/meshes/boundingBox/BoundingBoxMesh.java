package assets.meshes.boundingBox;

import static org.lwjgl.opengl.GL11.*;

import java.nio.IntBuffer;

import assets.buffers.Buffer;
import assets.buffers.VertexBuffer;
import assets.meshes.geometry.Vertex;
import assets.models.VertexArrayObject;
import math.vectors.Vector3f;
import utils.CustomBufferUtils;


public class BoundingBoxMesh {
	
	private VertexBuffer vbo;
	
	private VertexArrayObject vao;
	
	private IntBuffer indexBuffer;
	
	
	public BoundingBoxMesh() {
		vbo = new VertexBuffer();
		
		Vertex[] vertices = {
			//Front face:	
			new Vertex(new Vector3f(-1f, 1f, 1f)),
			new Vertex(new Vector3f(1f, 1f, 1f)),
			new Vertex(new Vector3f(1f, -1f, 1f)),
			new Vertex(new Vector3f(-1f, -1f, 1f)),
			
			//Back face:
			new Vertex(new Vector3f(-1f, 1f, -1f)),
			new Vertex(new Vector3f(1f, 1f, -1f)),
			new Vertex(new Vector3f(1f, -1f, -1f)),
			new Vertex(new Vector3f(-1f, -1f, -1f))
		};
		
		vbo.storeDataInterleaved(vertices, Buffer.DYNAMIC_STORAGE);
		
		vao = new VertexArrayObject();
		
		vao.setVertexAttributePointers(vbo, vertices[0].getDataLayout());
		
		int[] indices = {
			//Front face:
			0, 1, 1, 2, 2, 3, 3, 0,
			
			//Back Face:
			4, 5, 5, 6, 6, 7, 7, 4,
			
			//Connections between faces:
			0, 4, 1, 5, 2, 6, 3, 7
		};
		
		indexBuffer = CustomBufferUtils.createIntBuffer(indices);
	}
	
	
	public void render() {
		vao.enableVertexAttribArray();
		
		glDrawElements(GL_LINES, indexBuffer);
		
		vao.disableVertexAttribArray();
	}

}
