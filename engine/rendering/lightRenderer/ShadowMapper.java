package rendering.lightRenderer;

import assets.cameras.Camera;
import assets.light.LightSource;
import assets.light.ShadowMap;
import assets.models.Illuminated_Model;
import assets.textures.Texture2D;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import rendering.RenderEngine;
import rendering.framebuffers.FrameBuffer;
import rendering.matrices.projectionMatrices.ProjectionMatrix;
import rendering.shaders.standardShaders.shadowMapping.ShadowMappingShader;

public class ShadowMapper {
	
	private static Matrix44f projectionMatrix;
	
	private static ShadowMappingShader shader;
	
	private static FrameBuffer frameBuffer;
	
	private static ShadowMap capsuledShadowMap;
	
	private static Texture2D shadowMap;	
	
	
	public static void init(int windowWidth, int windowHeight) {
		
		shader = ShadowMappingShader.generateShader();
		
		projectionMatrix = ProjectionMatrix.generateOrthographicProjectionMatrix((float)windowWidth / (float)windowHeight, 8.0f);
		
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
		
		setUniformVariables(modelMatrix, light, camera);
		
		//TODO: Only works for closed objects
		RenderEngine.cullFrontFace();
		
		RenderEngine.render(model, null);
		
		RenderEngine.cullBackFace();
		
		shader.disable();
		
		frameBuffer.unbind();
		
		capsuledShadowMap.setLightProjectionMatrix(projectionMatrix);
		capsuledShadowMap.setShadowMap(shadowMap);
		
		return capsuledShadowMap;
		
	}
	
	
	private static void setUniformVariables(Matrix44f modelMatrix, LightSource light, Camera camera) {
		
		Matrix44f viewMatrix = Camera.generateViewMatrixB(light.getDirection().times(3f), light.getDirection().negatedCopy(), new Vector3f(0f, 1f, 0f));		
		
		shader.setUniformMatrix4fv("modelMatrix", modelMatrix);
		shader.setUniformMatrix4fv("viewMatrix", viewMatrix);
		shader.setUniformMatrix4fv("projectionMatrix", projectionMatrix);
		
		capsuledShadowMap.setLightViewMatrix(viewMatrix);
		
	}

}