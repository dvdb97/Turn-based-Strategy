package assets.models;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import assets.meshes.geometry.VertexLegacy;
import assets.models.abstractModels.Model;


//TODO: Probably make it abstract! Make onDraw() abstract again!

public class Array_Model extends Model {
	
	private int vertexCount;
	
	public Array_Model(int drawMode) {
		super(drawMode);
	}
	
	//****************************** Data Management ******************************
	
	
	public void setVertexPositionData(FloatBuffer positionDataBuffer, int size, int accessibility) {
		vertexCount = positionDataBuffer.capacity() / size;
		
		super.setVertexPositionData(positionDataBuffer, size, accessibility);
	}
	
	
	public void setData(VertexLegacy[] vertices) {
		
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
			this.setVertexNormalData(posBuffer, 3, GL_STATIC_DRAW);
		}
		
	}
	
	
	//****************************** Getters & Setters ******************************
	
	public int getVertexCount() {
		return vertexCount;
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
		
		glDrawArrays(getDrawMode(), 0, getVertexCount());
		
	}

}