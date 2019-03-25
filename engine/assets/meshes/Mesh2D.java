package assets.meshes;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import assets.scene.Scene;
import assets.shaders.standardShaders.SpriteShader;
import assets.textures.Texture;
import math.vectors.Vector4f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class Mesh2D extends Mesh {
	
	private SpriteShader shader;

	public Mesh2D() {
		this(GL_TRIANGLES);
		useMaterialColor();
	}
	
	
	public Mesh2D(int drawMode) {
		super(drawMode);
		
		loadShader();
		useMaterialColor();
	}
	
	
	public Mesh2D(Material material, Texture texture) {
		this();
		
		this.setMaterial(material);
		this.setTexture(texture);
	}
	
	
	protected void loadShader() {
		shader = SpriteShader.create();
	}
	
	
	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		shader.bind();
		
		shader.setMVPMatrix(camera.getViewProjectionMatrix().times(transformable.getTransformationMatrix()));
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
