package assets.meshes.prefabs;

import static org.lwjgl.opengl.GL11.*;

import java.nio.IntBuffer;

import assets.IDeletable;
import assets.buffers.Buffer;
import assets.buffers.VertexBuffer;
import assets.meshes.geometry.Vertex;
import assets.models.VertexArrayObject;
import math.vectors.Vector3f;
import utils.CustomBufferUtils;


public class Box implements IDeletable {
	
	private VertexBuffer vbo;
	
	private VertexArrayObject vao;
	
	private IntBuffer indexBuffer;
	
	
	public Box() {
		
		float[] positions = {
			-1f, 1f, 1f, 1f, 1f, 1f,
			1f, -1f, 1f, -1f, -1f, 1f,
			-1f, 1f, -1f, 1f, 1f, -1f,
			1f, -1f, -1f, -1f, -1f, -1f
		};
		
		vbo = new VertexBuffer(CustomBufferUtils.createFloatBuffer(positions));		
		
		vao = new VertexArrayObject();
		vao.setVertexAttributePointer(vbo, 0, 3, 0, 0);
		
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


	@Override
	public void delete() {
		vbo.delete();
		vao.delete();
	}

}
