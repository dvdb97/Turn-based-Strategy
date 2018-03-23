package assets.cameras;

import assets.models.Array_Model;
import assets.models.Element_Model;
import math.matrices.Matrix44f;
import rendering.RenderEngine;
import rendering.shaders.ShaderManager;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class CameraDebug {
	
	private static Array_Model cameraModel;	
	
	public static void init() {
		
		cameraModel = new Array_Model(GL_LINE);
		
		float[] posData = {
			-1.0f,  0.0f,  0.0f,
			 1.0f,  0.0f,  0.0f,
			 0.0f, -1.0f,  0.0f,
			 0.0f,  1.0f,  0.0f,
			 0.0f,  0.0f, -1.0f,
			 0.0f,  0.0f,  1.0f
		};
		
		
		float[] colorData = {
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
		};
		
		cameraModel.setVertexPositionData(CustomBufferUtils.createFloatBuffer(posData), 3, GL_STATIC_DRAW);
		cameraModel.setVertexColorData(CustomBufferUtils.createFloatBuffer(colorData), 3, GL_STATIC_DRAW);
		
	}
	
	
	public static void display(Camera camera) {
		ShaderManager.useShader(camera.getInvertedViewMatrix(), new Matrix44f(), new Matrix44f(), false, null);
		
		RenderEngine.render(cameraModel, null);
		
		ShaderManager.disableShader();		
	}
	

}
