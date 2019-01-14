package assets.meshes;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import assets.scene.Scene;
import assets.shaders.standardShaders.SpriteShader;
import assets.textures.Texture;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class Mesh2D extends Mesh {
	
	private SpriteShader shader;

	public Mesh2D() {
		this(GL_TRIANGLES);
	}
	
	
	public Mesh2D(int drawMode) {
		super(drawMode);
		
		shader = SpriteShader.create();
		useMaterialColor();
	}
	
	
	public Mesh2D(Material material, Texture texture) {
		this();
		
		this.setMaterial(material);
		this.setTexture(texture);
	}
	
	
	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		shader.bind();
		shader.setMVPMatrix(getTransformable().getTransformationMatrix());
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
		this.onDrawStart(null, null);	
	}

	@Override
	protected void onDrawEnd(Scene scene) {
		this.onDrawEnd(null, null);		
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
