package models.meeples;

import java.nio.FloatBuffer;

import assets.material.Material;
import assets.meshes.Mesh3D;
import assets.meshes.Transformable;
import assets.meshes.geometry.Color;
import math.vectors.Vector3f;
import utils.ColorPalette;
import utils.CustomBufferUtils;

import static utils.Const.SQRT3;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;

public class StreetModel extends Mesh3D {
	
	public StreetModel(Transformable gameBoard, Color color) {
		super(GL_TRIANGLE_STRIP, new Material(color, Vector3f.ZERO, new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(0.2f, 0.2f, 0.2f), 256f));
		
		float h = 0.3f;
		
		float[] posData = new float[] {0f,           0f,    0f,
		                               SQRT3/6f,    -1f/6f, 0f,
		                               5f*SQRT3/6f, -1f/6f, 0f,
		                               SQRT3,        0f,    0f,
		                               5f*SQRT3/6f,  1f/6f, 0f,
		                               SQRT3/6f,     1f/6f, 0f,
		                               0f,           0f,    h,
		                               SQRT3/6f,    -1f/6f, h,
		                               5f*SQRT3/6f, -1f/6f, h,
		                               SQRT3,        0f,    h,
		                               5f*SQRT3/6f,  1f/6f, h,
		                               SQRT3/6f,     1f/6f, h};
		
		FloatBuffer posBuffer = CustomBufferUtils.createFloatBuffer(posData);
		
		setPositionData(posBuffer);
		setIndexBuffer(CustomBufferUtils.createIntBuffer(new int[] {0,5,1,5,1,4,1,4,2,4,2,3,
		                                                            6,11,7,11,7,10,7,10,8,10,8,9,
		                                                            0,1,7,1,7,2,7,2,8,2,8,3,8,3,9,3,9,4,9,4,10,4,10,5,10,5,11,5,11,0,11,0,6,0,6,7}));
		
		transformable.setParent(gameBoard);
	}
	
}