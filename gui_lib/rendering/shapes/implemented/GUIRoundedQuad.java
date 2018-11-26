package rendering.shapes.implemented;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import rendering.shapes.GUIShape;
import utils.CustomBufferUtils;

public class GUIRoundedQuad extends GUIShape {
	
	
	private float cornerEdgeLength;
	
	
	public GUIRoundedQuad(float cornerEdgeLength) {
		super();
		
		this.cornerEdgeLength = cornerEdgeLength;
		
		loadData();		
	}
	

	@Override
	public FloatBuffer getPositionData() {
		
		float a = cornerEdgeLength;
		float b = 1.0f - cornerEdgeLength;
		
		float[] positionData = {
			a   , -0.0f, -1.0f, //0
			0.0f, -a   , -1.0f, //1
			a   , -a   , -1.0f, //2
			
			b   , -0.0f, -1.0f, //3
			0.0f, -a   , -1.0f, //4
			b   , -a   , -1.0f, //5
			
			a   , -1.0f, -1.0f, //6
			0.0f, -b   , -1.0f, //7
			a   , -b   , -1.0f, //8
			
			b   , -1.0f, -1.0f, //9
			1.0f, -b   , -1.0f, //10
			b   , -b   , -1.0f  //11
		};
		
		return CustomBufferUtils.createFloatBuffer(positionData);
	}
	

	@Override
	public FloatBuffer getTexPosData() {
		
		float a = cornerEdgeLength;
		float b = 1.0f - cornerEdgeLength;
		
		float[] texPosData = {
			a   , 0.0f, 0.9f, //0
			0.0f, a   , 0.9f, //1
			a   , a   , 0.9f, //2
			
			b   , 0.0f, 0.9f, //3
			0.0f, a   , 0.9f, //4
			b   , a   , 0.9f, //5
			
			a   , 1.0f, 0.9f, //6
			0.0f, b   , 0.9f, //7
			a   , b   , 0.9f, //8
			
			b   , 1.0f, 0.9f, //9
			1.0f, b   , 0.9f, //10
			b   , b   , 0.9f  //11
		};
		
		return CustomBufferUtils.createFloatBuffer(texPosData);
	}
	

	@Override
	public IntBuffer getIndexData() {
		
		int[] indexArray = {
			0, 1, 2,
			0, 3, 2,
			2, 3, 4,
			3, 4, 5,
			1, 2, 7,
			2, 7, 8,
			2, 4, 8,
			4, 8, 11,
			4, 5, 11,
			5, 11, 10,
			7, 8, 6,
			8, 11, 6,
			11, 6, 9,
			11, 10, 9
		};
		
		return CustomBufferUtils.createIntBuffer(indexArray);
	}
	

	@Override
	public boolean isHit(float cursorX, float cursorY) {
		
		float a = cornerEdgeLength;
		float b = 1.0f - cornerEdgeLength;
		
		if (cursorX < 0.0f || cursorX > 1.0f) {
			return false;
		}
		
		if (cursorY < 0.0f || cursorY > 1.0f) {
			return false;
		}
				
		return true;
	}

}
