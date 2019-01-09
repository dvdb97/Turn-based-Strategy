package assets.meshes.specialized;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import assets.meshes.Mesh;
import assets.scene.Scene;
import assets.shaders.standardShaders.CircleShader;
import utils.CustomBufferUtils;

public class Circle extends Mesh {

	private CircleShader shader;
	
	public Circle(Material material) {
		shader = CircleShader.create();
		
		float[] pos = {
			-1f, 1f, 0f, 1f, 1f, 0f,
			-1f, -1f, 0f, 1f, -1f, 0f
		};
		
		float[] texPos = {
			0f, 1f, 1f, 1f,
			0f, 0f, 1f, 0f
		};
		
		int[] indices = {
			0, 1, 3, 3, 2, 0
		};
		
		this.setPositionData(CustomBufferUtils.createFloatBuffer(pos), 3);
		this.setTexCoordData(CustomBufferUtils.createFloatBuffer(texPos), 2);
		this.setIndexBuffer(CustomBufferUtils.createIntBuffer(indices));
		this.setMaterial(material);
		useMaterialColor();
	}
	
	
	public Circle() {
		this(Material.standard);
	}
	
	
	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		shader.bind();
		shader.setCamera(camera);
		shader.setModelMatrix(getTransformable().getTransformationMatrix());
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

}
