package assets.meshes;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import assets.scene.Scene;
import assets.shaders.standardShaders.lightShader.LightShader;
import assets.textures.Texture;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class Mesh3D extends Mesh {

	private LightShader shader;
	
	
	public Mesh3D() {
		this(GL_TRIANGLES);
	}
	
	
	public Mesh3D(int drawMode) {
		super(drawMode);
		
		shader = LightShader.createPerFragmentLightShader();
		useMaterialColor();
		useDefaultFinalColor();
	}
	
	
	public Mesh3D(Material material) {
		this();
		this.setMaterial(material);
	}
	
	
	public Mesh3D(Material material, Texture texture) {
		this(GL_TRIANGLES);
		
		this.setMaterial(material);
		this.setTexture(texture);
	}
	
	
	public Mesh3D(int drawMode, Material material, Texture texture) {
		this(drawMode);
		
		this.setMaterial(material);
		this.setTexture(texture);
	}


	/**
	 * A function that sets all uniform variables to render this mesh.
	 * If this function wasn't overwritten, it will use a default light shader
	 * that uses the material's color as the mesh's color.
	 * 
	 * @param camera The camera that is used to look at the mesh.
	 * @param light The light source that is used to render this mesh.
	 */
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		shader.bind();
		shader.setCamera(camera);
		shader.setLightSource(light, getMaterial().castShadows);
		shader.setMaterial(getMaterial());
		shader.setModelMatrix(getTransformable().getTransformationMatrix());
		shader.setUniformSubroutines();
		
		if (getTexture() != null) shader.bindTexture("material.texture", getTexture());
	}
	
	
	/**
	 * 
	 * Clean up after rendering.
	 * 
	 * @param camera The camera that is used to look at the mesh.
	 * @param light The light source that is used to render this mesh.
	 */
	protected void onDrawEnd(Camera camera, DirectionalLight light) {		
		shader.unbind();
	}
	
	
	/**
	 * 
	 * A function that sets all uniform variables to render this mesh.
	 * If this function wasn't overwritten, it will use a default light shader
	 * that uses the material's color as the mesh's color.
	 * 
	 * @param scene The scene that is currently being rendered.
	 */
	protected void onDrawStart(Scene scene) {
		this.onDrawStart(scene.getCamera(), scene.getLightSource());
	}
	
	
	/**
	 * 
	 * Clean up after rendering.
	 * 
	 * @param scene The scene that is currently being rendered.
	 */
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
