package assets.meshes.instanced;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.scene.Scene;
import assets.shaders.standardShaders.lightShader.LightShader;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class Instancer3D extends Instancer {
	
	private LightShader shader;

	public Instancer3D(int numInstances) {
		this(numInstances, GL_TRIANGLES);
	}
	
	public Instancer3D(int numInstances, int drawMode) {
		super(numInstances, drawMode);
		
		this.shader = LightShader.createInstancedRenderingShader();
		useDefaultFinalColor();
		useMaterialColor();
	}

	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {		
		shader.bind();
		
		shader.setUniformMatrix4fv("globalMMatrix", transformable.getTransformationMatrix());
		shader.setCamera(camera);
		shader.setViewProjectionMatrix(camera.getViewProjectionMatrix());
		shader.setLightSource(light, getMaterial().castShadows);
		shader.setMaterial(getMaterial());
		shader.setUniformSubroutines();
		
		if (getTexture() != null)
			shader.bindTexture("material.texture", getTexture());
	}

	@Override
	protected void onDrawEnd(Camera camera, DirectionalLight light) {
		shader.unbind();		
	}

	@Override
	protected void onDrawStart(Scene scene) {
		this.onDrawStart(scene.getCamera(), scene.getLightSource());		
	}

	@Override
	protected void onDrawEnd(Scene scene) {
		this.onDrawEnd(scene.getCamera(), scene.getLightSource());
	}
	
	public void useToonShading() {
		shader.bind();
		shader.useToonShading();
		shader.unbind();
	}
	
	
	public void useDefaultFinalColor() {
		shader.bind();
		shader.useDefaultFinalColorFunction();
		shader.unbind();
	}
	
	
	public void useTextureColor() {
		shader.bind();
		shader.useTextureColor();
		shader.unbind();
	}
	
	
	public void useMaterialColor() {
		shader.bind();
		shader.useMaterialColor();
		shader.unbind();
	}
	
	
	public void useAttributeColor() {
		shader.bind();
		shader.useAttribColor();
		shader.unbind();
	}


	@Override
	public void delete() {
		shader.delete();
		super.delete();
	}

}
