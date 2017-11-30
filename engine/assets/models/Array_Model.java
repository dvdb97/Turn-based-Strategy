package assets.models;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import java.nio.FloatBuffer;
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