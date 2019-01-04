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
		float a = cornerEdgeLength;
		float b = 1.0f - cornerEdgeLength;
		
		float[] posData = {
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
		
		float[] texCoords = {
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
		
		
		int[] indices = {
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
		
		setPositionData(CustomBufferUtils.createFloatBuffer(posData));
		setTexCoordData(CustomBufferUtils.createFloatBuffer(texCoords), 2);
		setIndexBuffer(CustomBufferUtils.createIntBuffer(indices));
	}
	

	@Override
	public boolean isHit(float cursorX, float cursorY) {
		
		if (cursorX < 0.0f || cursorX > 1.0f) {
			return false;
		}
		
		if (cursorY < 0.0f || cursorY > 1.0f) {
			return false;
		}
				
		return true;
	}

}
