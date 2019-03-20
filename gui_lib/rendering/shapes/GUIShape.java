package rendering.shapes;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.meshes.Mesh;
import assets.meshes.Mesh2D;
import assets.meshes.geometry.Color;
import assets.scene.Scene;
import assets.shaders.standardShaders.SpriteShader;
import assets.textures.Texture;
import dataType.GUIElementMatrix;
import math.matrices.Matrix44f;

public abstract class GUIShape extends Mesh {
	
	private SpriteShader shader;

	public GUIShape() {
		this(GL_TRIANGLES);
	}
	
	
	public GUIShape(int drawMode) {
		super(drawMode);
		
		shader = SpriteShader.create();
		useTextureColor();	
	}
	
	
	public void render(Texture texture) {
		this.setTexture(texture);
		this.useTextureColor();
		
		onDrawStart(null);
		this.render();		
		onDrawEnd(null);
	}
	
	
	public void render(Texture texture, GUIElementMatrix matrix) {
		this.setTexture(texture);
		this.useTextureColor();		
		
		onDrawStart(null, null, matrix.toMatrix44f());
		this.render();
		onDrawEnd(null, null);
	}
	
	
	public void render(Color color) {
		this.getMaterial().color = color;
		this.useMaterialColor();
		
		onDrawStart(null);
		this.render();
		onDrawEnd(null);
	}
	
	
	public void render(Color color, GUIElementMatrix matrix) {
		this.getMaterial().color = color;
		this.useMaterialColor();
		
		onDrawStart(null, null, matrix.toMatrix44f());
		this.render();
		onDrawEnd(null, null);
	}
	
	
	protected void onDrawStart(Camera camera, DirectionalLight light, Matrix44f transformationMatrix) {
		shader.bind();
		shader.setMVPMatrix(transformationMatrix);
		shader.setMaterial(getMaterial());
		shader.setUniformSubroutines();
		
		if (getTexture() != null)
			shader.setMaterialTexture(getTexture());
	}
	

	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		this.onDrawStart(camera, light, transformable.getTransformationMatrix());
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
	
	
	public abstract boolean isHit(float cursorX, float cursorY);
	
	
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
