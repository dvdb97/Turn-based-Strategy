package rendering.lightRenderer;

import assets.light.DirectionalLight;
import assets.light.ShadowMap;
import assets.models.Illuminated_Model;
import assets.shaders.standardShaders.shadowMapping.ShadowMappingShader;
import assets.textures.Texture2D;
import math.matrices.Matrix44f;
import rendering.RenderEngine;
import rendering.framebuffers.FrameBuffer;
import rendering.matrices.projectionMatrices.ProjectionMatrix;

public class ShadowMapper {
	
	private static Matrix44f projectionMatrix;
	
	private static ShadowMappingShader shader;
	
	private static FrameBuffer frameBuffer;
	
	private static ShadowMap capsuledShadowMap;
	
	private static Texture2D shadowMap;	
	
	
	public static void init(int windowWidth, int windowHeight) {
		
		shader = ShadowMappingShader.generateShader();
		
		projectionMatrix = ProjectionMatrix.generateOrthographicProjectionMatrix((float)windowWidth / (float)windowHeight, 1.0f);
		
		createShadowMap(windowWidth, windowHeight);
		
	}
	
	
	private static void createShadowMap(int width, int height) {
		frameBuffer = new FrameBuffer();
		shadowMap = Texture2D.generateEmptyTexture(width, height);
		frameBuffer.addDepthAttachment(shadowMap);
		frameBuffer.disableColorBuffer();
		
		capsuledShadowMap = new ShadowMap(null, null, null);
	}
	
	
	
	public static ShadowMap generateShadowMap(Illuminated_Model model, Matrix44f modelMatrix, DirectionalLight light) {
		
		frameBuffer.bind();
		
		frameBuffer.clear();
		
		shader.use();
		
		setUniformVariables(modelMatrix, light);
		
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
	
	
	private static void setUniformVariables(Matrix44f modelMatrix, DirectionalLight light) {		
		
		Matrix44f viewMatrix = light.getViewMatrix();
		
		System.out.println("Light Direction:");
		light.getViewDirection().print();
		System.out.println("view matrix: ");
		viewMatrix.print();
		
		
		shader.setUniformMatrix4fv("modelMatrix", modelMatrix);
		shader.setUniformMatrix4fv("viewMatrix", viewMatrix);
		shader.setUniformMatrix4fv("projectionMatrix", projectionMatrix);
		
		capsuledShadowMap.setLightViewMatrix(viewMatrix);
		
	}

}
