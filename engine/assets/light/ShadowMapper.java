package assets.light;

import assets.cameras.Camera;
import assets.cameras.CameraOperator;
import assets.models.Illuminated_Model;
import assets.textures.Texture2D;
import math.matrices.Matrix44f;
import math.vectors.Vector2i;
import math.vectors.Vector3f;
import rendering.RenderEngine;
import rendering.framebuffers.FrameBuffer;
import rendering.matrices.ViewMatrix;
import rendering.shaders.ShaderLoader;
import rendering.shaders.ShaderProgram;

import static org.lwjgl.opengl.GL11.*;

public class ShadowMapper {
	
	private static final int SHADOW_MAP_WIDTH = 1024;
	
	private static final int SHADOW_MAP_HEIGHT = 1024;
	
	private static ShaderProgram shader;
	
	
	private static Matrix44f projectionMatrix;
	
	private static FrameBuffer frameBuffer;
	
	private static Texture2D texture;
	
	private static boolean initialized = false;

	
	public static void init(Matrix44f projectionMatrix) {
		
		if(initialized) {
			return;
		}
		
		shader = ShaderLoader.loadShader("Shaders/ShadowMappingShader/ShadowMappingShader.vert", "Shaders/ShadowMappingShader/ShadowMappingShader.frag");
		
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
	 * @param cameraPos The position of the regular camera. TODO: Replace it with an actual reference to a camera
	 */
	public static Texture2D generateShadowMap(Illuminated_Model model, Matrix44f modelMatrix, LightSource light, Vector3f cameraPos) {
		
		Vector2i viewportPos = RenderEngine.getViewPortPosition();
		Vector2i viewportSize = RenderEngine.getViewportSize();
		
		RenderEngine.setViewport(0, 0, SHADOW_MAP_WIDTH, SHADOW_MAP_HEIGHT);

		frameBuffer.bind();
		
		texture.bind();
		
		RenderEngine.clear(GL_DEPTH_BUFFER_BIT);
		
		shader.use();
		shader.setUniformMatrix4fv("modelMatrix", modelMatrix);
		shader.setUniformMatrix4fv("viewMatrix", light.getLightViewMatrix());
		shader.setUniformMatrix4fv("projectionMatrix", projectionMatrix);
		
		RenderEngine.render(model, null);
		
		shader.disable();
		
		texture.unbind();
		
		frameBuffer.unbind();
		
		RenderEngine.setViewport(viewportPos, viewportSize);
		
		return texture;
		
	}
	
	
	public static Matrix44f getProjectionmatrix() {
		return projectionMatrix;
	}

}
