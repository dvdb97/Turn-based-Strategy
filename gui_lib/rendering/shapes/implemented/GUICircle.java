package rendering.shapes.implemented;

import rendering.shapes.GUIShape;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.PI;

import org.lwjgl.BufferUtils;

import math.vectors.Vector2f;

public class GUICircle extends GUIShape {

	public GUICircle(int numVertices) {
		super(GL_TRIANGLE_FAN);
		
		FloatBuffer posData = BufferUtils.createFloatBuffer((numVertices + 1) * 3);
		FloatBuffer texCoords = BufferUtils.createFloatBuffer((numVertices + 1) * 2);
		IntBuffer indices = BufferUtils.createIntBuffer(numVertices + 2);
		
		posData.put(0.5f).put(-0.5f);
		indices.put(0);
		
		for (int i = 0; i < numVertices; ++i) {
			posData.put(0.5f + (float)cos(i * (PI / numVertices))).put(-0.5f + (float)sin(i * (PI / numVertices))).put(-1f);
			indices.put(i + 1);
		}
		
		indices.put(1);
		
		posData.flip();
		indices.flip();
		
		setPositionData(posData);
		setIndexBuffer(indices);
	}

	@Override
	public boolean isHit(float cursorX, float cursorY) {
		return new Vector2f(cursorX + 0.5f, cursorY - 0.5f).norm() <= 1f;
	}
	
}
