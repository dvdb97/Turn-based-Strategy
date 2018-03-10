package rendering.shapes.implemented;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import math.vectors.Vector3f;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;
import utils.CustomBufferUtils;

public class GUIQuad extends GUIShape {	
	
	private static final float[] data = {
		0.0f,  0.0f, -1.0f,
		1.0f,  0.0f, -1.0f,
		0.0f, -1.0f, -1.0f,
		1.0f, -1.0f, -1.0f		
	};


	private static final float[] texData = {
		0.0f, 0.0f,
		1.0f, 0.0f,	
		0.0f, 1.0f,
		1.0f, 1.0f
	};
	
	
	private static final int[] indices = {
		0, 1, 3,
		0, 2, 3
	};
	
	
	public GUIQuad() {
		super();
		
		this.loadData();
	}
	
	
	@Override
	public FloatBuffer getPositionData() {
		return CustomBufferUtils.createFloatBuffer(data);
	}

	@Override
	public FloatBuffer getTexPosData() {
		return CustomBufferUtils.createFloatBuffer(texData);
	}

	@Override
	public IntBuffer getIndexData() {
		return CustomBufferUtils.createIntBuffer(indices);
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
