package assets.models;


import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import assets.meshes.geometry.Vertex;
import assets.models.abstractModels.Model;
import utils.CustomBufferUtils;


/*
 * This model is drawn by using the command glDrawElements()
 */
public class Element_Model extends Model {	
	
	
	private IntBuffer elementBuffer;
	
	
	public Element_Model(int drawMode) {
		super(drawMode);
	}
	
	
	//****************************** Data Management ******************************
	
	
	public void setData(Vertex[] vertices, IntBuffer indexBuffer) {
		
		FloatBuffer posBuffer = BufferUtils.createFloatBuffer(vertices.length * 3);
		FloatBuffer colBuffer = BufferUtils.createFloatBuffer(vertices.length * 4);
		FloatBuffer texPosBuffer = BufferUtils.createFloatBuffer(vertices.length * 3);
		FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(vertices.length * 3);
		
		
		boolean textured = vertices[0].isTextured();
		boolean normals = vertices[0].getNormal() != null;
		
		
		for (int i = 0; i < vertices.length; ++i) {
			
			posBuffer.put(vertices[i].getPositionData());
			
			if (textured) {
				texPosBuffer.put(vertices[i].getTexturePositions().toArray());
			} else {
				colBuffer.put(vertices[i].getColorData());
			}
			
			if (normals) {
				normalBuffer.put(vertices[i].getNormal().toArray());
			}
			
		}
		
		
		this.setElementArrayData(indexBuffer);
		
		
		posBuffer.flip();
		this.setVertexPositionData(posBuffer, 3, GL_STATIC_DRAW);
		
		if (textured) {
			texPosBuffer.flip();
			this.setVertexTexturePositionData(texPosBuffer, 3, GL_STATIC_DRAW);
		} else {
			colBuffer.flip();
			this.setVertexColorData(colBuffer, 4, GL_STATIC_DRAW);
		}
		
		
		if (normals) {
			normalBuffer.flip();
			this.setVertexPositionData(posBuffer, 3, GL_STATIC_DRAW);
		}
		
	}
	
	
	public void setData(Vertex[] vertices, int[] indexArray) {
		
		IntBuffer indexBuffer = CustomBufferUtils.createIntBuffer(indexArray);
		
		this.setData(vertices, indexBuffer);
		
	}
	
	
	public void setElementArrayData(IntBuffer indexBuffer) {
		elementBuffer = indexBuffer;
	}
	
	
	//****************************** Getters & Setters ******************************
	
	
	public IntBuffer getElementBuffer() {
		return elementBuffer;
	}	
		
	
	//****************************** Additional functions ******************************
	
	
	public void delete() {
		super.delete();
		
		glDeleteVertexArrays(getVaoID());
		
		System.out.println(this.toString() + " was deleted!");
	}

	
	@Override
	public void onDrawStart() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onDrawStop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		glDrawElements(getDrawMode(), getElementBuffer());		
	}
}