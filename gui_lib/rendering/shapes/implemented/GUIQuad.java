package rendering.shapes.implemented;

import rendering.shapes.GUIShape;
import utils.CustomBufferUtils;

public class GUIQuad extends GUIShape {	
	
	public GUIQuad() {
		super();
		
		float[] posData = {
			0.0f,  0.0f, -1.0f,
			1.0f,  0.0f, -1.0f,
			0.0f, -1.0f, -1.0f,
			1.0f, -1.0f, -1.0f		
		};
		
		float[] texCoords = {
			0.0f, 0.0f,
			1.0f, 0.0f,	
			0.0f, 1.0f,
			1.0f, 1.0f	
		};
		
		int[] indices = {
			0, 1, 3,
			0, 2, 3	
		};
		
		setPositionData(CustomBufferUtils.createFloatBuffer(posData));
		setTexCoordData(CustomBufferUtils.createFloatBuffer(texCoords), 2);
		setIndexBuffer(CustomBufferUtils.createIntBuffer(indices));
	}
	

	@Override
	public boolean isHit(float cursorX, float cursorY) {
		
		if (cursorX < 0f || cursorX > 1f) {
			return false;
		}
		
		if (cursorY > 0f || cursorY < -1f) {
			return false;
		}
		
		return true;
		
	}

}
