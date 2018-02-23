package assets.light;

import assets.models.Illuminated_Model;
import assets.textures.Texture2D;
import graphics.matrices.TransformationMatrix;
import interaction.Window;
import math.matrices.Matrix44f;
import math.matrices.advanced.MatrixInversion44f;
import math.vectors.Vector3f;
import rendering.RenderEngine;
import rendering.framebuffers.FrameBuffer;
import rendering.matrices.projectionMatrices.ProjectionMatrix;
import rendering.shaders.standardShaders.shadowShader.ShadowMappingShader;

public class ShadowMapper {
	
	private static ShadowMappingShader shader;
	
	private static Matrix44f projectionMatrix;
	
	private static FrameBuffer frameBuffer;
	
	private static Texture2D texture;
	
	private static boolean initialized = false;

	
	public static void init(int width, int height, ProjectionMatrix matrix) {
		
		if(initialized) {
			return;
		}
		
		shader = new ShadowMappingShader();
		
		projectionMatrix = matrix;
		
		texture = new Texture2D(width, height);
		
		setUpFrameBuffer();
		
		initialized = true;
		
	}
	
	
	public static void init(Window window, ProjectionMatrix matrix) {
		
		init(window.getWidth(), window.getHeight(), matrix);
		
	}
	
	
	private static void setUpFrameBuffer() {
		frameBuffer = new FrameBuffer();
		frameBuffer.addDepthAttachment(texture);
		frameBuffer.disableColorBuffer();
		frameBuffer.unbind();
	}
	
	
	/**
	 * 
	 * @param model 
	 * @param light
	 * @param lightPosition
	 * @param viewMatrix
	 */
	public static Texture2D generateShadowMap(Illuminated_Model model, Matrix44f modelMatrix, LightSource light, Vector3f lightPosition) {
		
		TransformationMatrix lightViewMatrix = new TransformationMatrix(lightPosition.negatedCopy(), light.getDirection().negatedCopy(), 1.0f);
		
		Matrix44f mvpMatrix = lightViewMatrix.times(modelMatrix);
		
		shader.use();
		
		shader.prepareForRendering(mvpMatrix);
		
		RenderEngine.render(frameBuffer, model, null);
		
		shader.disable();
		
		return texture;
		
	}

}
