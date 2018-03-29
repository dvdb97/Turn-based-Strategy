package rendering.lightRenderer;

import assets.cameras.Camera;
import assets.cameras.CameraOperator;
import assets.light.LightSource;
import assets.light.ShadowMap;
import assets.models.Illuminated_Model;
import assets.textures.Texture2D;
import math.matrices.Matrix44f;
import rendering.RenderEngine;
import rendering.framebuffers.FrameBuffer;
import rendering.matrices.projectionMatrices.ProjectionMatrix;
import rendering.shaders.standardShaders.shadowMapping.ShadowMappingShader;

public class ShadowMapper {
	
	private static CameraOperator lightCamera;
	
	private static Matrix44f projectionMatrix;
	
	private static ShadowMappingShader shader;
	
	private static FrameBuffer frameBuffer;
	
	private static ShadowMap capsuledShadowMap;
	
	private static Texture2D shadowMap;					
	
	
	public static void init(int windowWidth, int windowHeight) {
		
		shader = ShadowMappingShader.generateShader();
		
		projectionMatrix = ProjectionMatrix.generateOrthographicProjectionMatrix((float)windowWidth / (float)windowHeight, 10.0f);
		
		lightCamera = new CameraOperator();

		createShadowMap(windowWidth, windowHeight);
		
	}
	
	
	private static void createShadowMap(int width, int height) {
		frameBuffer = new FrameBuffer();
		shadowMap = Texture2D.generateEmptyTexture(width, height);
		frameBuffer.addDepthAttachment(shadowMap);
		frameBuffer.disableColorBuffer();
		
		capsuledShadowMap = new ShadowMap(null, null, null);
	}
	
	
	
	public static ShadowMap generateShadowMap(Illuminated_Model model, Matrix44f modelMatrix, LightSource light, Camera camera) {
		
		frameBuffer.bind();
		
		frameBuffer.clear();
		
		shader.use();
		
		setUniformVariables(model, modelMatrix, light, camera);
		
		RenderEngine.render(model, null);
		
		shader.disable();
		
		frameBuffer.unbind();
		
		
		capsuledShadowMap.setLightViewMatrix(lightCamera.getViewMatrix());
		capsuledShadowMap.setLightProjectionMatrix(projectionMatrix);
		capsuledShadowMap.setShadowMap(shadowMap);
		
		return capsuledShadowMap;
		
	}
	
	
	private static void setUniformVariables(Illuminated_Model model, Matrix44f modelMatrix, LightSource light, Camera camera) {
		
		adjustLightViewCamera(light);
		
		shader.setUniformMatrix4fv("modelMatrix", modelMatrix);
		shader.setUniformMatrix4fv("viewMatrix", lightCamera.getViewMatrix());
		shader.setUniformMatrix4fv("projectionMatrix", projectionMatrix);
		
	}
	
	
	private static void adjustLightViewCamera(LightSource light) {
		lightCamera.lookAt(light.getDirection().negatedCopy().times(5f), light.getDirection());
	}

}
