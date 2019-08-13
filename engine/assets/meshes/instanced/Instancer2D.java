package assets.meshes.instanced;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.scene.Scene;
import assets.shaders.standardShaders.SpriteShader;

public class Instancer2D extends Instancer {

	private SpriteShader shader;
	
	public Instancer2D(int numInstances, int drawMode) {
		super(numInstances, drawMode);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		shader.bind();
		
		shader.setUniformMatrix4fv("globalMMatrix", transformable.getTransformationMatrix());
		shader.setViewProjectionMatrix(camera.getViewProjectionMatrix());
		shader.setMaterial(getMaterial());
		shader.setUniformSubroutines();
		
		if (getTexture() != null)
			shader.setMaterialTexture(getTexture());
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
