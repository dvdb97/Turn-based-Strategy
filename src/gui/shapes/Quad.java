package gui.shapes;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import rendering.shapes.GUIShape;
import utils.CustomBufferUtils;

public class Quad extends GUIShape  {
	
	private final float[] positionData = {
		0f, 1f,
		1f, 1f,
		0f, 0f,
		1f, 0f
	};
	
	
	private final int[] indexArray = {
		0, 1, 3,
		0, 2, 3
	};
	
	
	private final float[] texturePositions = {
		0f, 0f,
		1f, 0f,
		0f, 1f,
		1f, 1f		
	};
	
	
	public Quad() {
		
	}
	

	@Override
	public FloatBuffer getPositionData() {
		return CustomBufferUtils.createFloatBuffer(positionData);
	}

	@Override
	public FloatBuffer getTexPosData() {
		return CustomBufferUtils.createFloatBuffer(texturePositions);
	}

	@Override
	public IntBuffer getIndexData() {
		return CustomBufferUtils.createIntBuffer(indexArray);
	}

	@Override
	public boolean isHit(float cursorX, float cursorY) {
		
		if (cursorX < 0f || cursorX > 1f) {
			return false;
		}
		
		if (cursorY < 0f || cursorY > 1f) {
			return false;
		}
		
		return true;
		
	}

}
