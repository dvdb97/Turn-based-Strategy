package rendering.lightRenderer;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.light.ShadowMap;
import assets.models.Illuminated_Model;
import graphics.matrices.Matrices;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import rendering.RenderEngine;
import rendering.shaders.standardShaders.lightShader.LightShader;
import testing.TextureRenderer;

public class LightRenderer {
	
	private static LightShader lightShader;
	
	private static ShadowMap shadowMap;
	
	
	public static void init(int windowWidth, int windowHeight) {
		
		ShadowMapper.init(windowWidth, windowHeight);
		
		lightShader = LightShader.createPerFragmentLightShader();
		
		shadowMap = new ShadowMap(null, new Matrix44f(), new Matrix44f());
		
	}
	
	
	public static void render(Illuminated_Model model, Matrix44f modelMatrix, DirectionalLight light, Vector3f ambientLight, Camera camera, boolean shadows) {
		
		if (shadows) {
			shadowMap = ShadowMapper.generateShadowMap(model, modelMatrix, light);
		
			TextureRenderer.render(shadowMap.getShadowMap());
		}
		
		lightShader.use();
		
		lightShader.setMVPMatrix(modelMatrix, camera.getViewMatrix(), Matrices.getPerspectiveProjectionMatrix());
		
		lightShader.setCameraPosition(camera.getPosition());
		
		lightShader.setMaterial(model.getMaterial());
		
		lightShader.setAmbientLight(ambientLight);
		
		lightShader.setLightSource(light, shadowMap.getLightViewMatrix(), shadowMap.getLightProjectionMatrix(), shadows);
		
		if (shadows)
			shadowMap.getShadowMap().bind();
		
		RenderEngine.render(model, null);
		
		if (shadows)
			shadowMap.getShadowMap().unbind();
		
		lightShader.disable();
		
	}
	
	
	
	

}
