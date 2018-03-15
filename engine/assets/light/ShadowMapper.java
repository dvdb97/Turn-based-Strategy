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

import static org.lwjgl.opengl.GL11.*;

public class ShadowMapper {
	
	private static final int SHADOW_MAP_WIDTH = 1024;
	
	private static final int SHADOW_MAP_HEIGHT = 1024;
	
	private static ShadowMappingShader shader;
	
	private static Matrix44f projectionMatrix;
	
	private static FrameBuffer frameBuffer;
	
	private static Texture2D texture;
	
	private static boolean initialized = false;

	
	public static void init(Matrix44f projectionMatrix) {
		
		if(initialized) {
			return;
		}
		
		shader = new ShadowMappingShader();
		
		ShadowMapper.projectionMatrix = projectionMatrix;
		
		texture = Texture2D.generateEmptyTexture(SHADOW_MAP_WIDTH, SHADOW_MAP_HEIGHT);
		
		setUpFrameBuffer();
		
		initialized = true;
		
	}
	
	
	private static void setUpFrameBuffer() {
		frameBuffer = new FrameBuffer();
		frameBuffer.addDepthAttachment(texture);
		frameBuffer.disableColorBuffer();
	}
	
	
	/**
	 * 
	 * @param model 
	 * @param light
	 * @param lightPosition
	 * @param viewMatrix
	 */
	public static Texture2D generateShadowMap(Illuminated_Model model, Matrix44f modelMatrix, LightSource light, Vector3f lightPosition) {
		
		glViewport(0, 0, SHADOW_MAP_WIDTH, SHADOW_MAP_HEIGHT);
		
		frameBuffer.bind();
		
		RenderEngine.clear(GL_DEPTH_BUFFER_BIT);
		
		//TODO: Configure matrices
		
		shader.use();
		shader.prepareForRendering(null);
		
			RenderEngine.render(frameBuffer, model, null);
		
		shader.disable();
		
		frameBuffer.unbind();
		
		return texture;
		
	}

}
