package assets.meshes.specialized;

import static org.lwjgl.opengl.GL11.GL_LINES;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.meshes.Mesh;
import assets.scene.Scene;
import assets.shaders.standardShaders.StandardShader;

public class WireframeMesh extends Mesh {
	
	private StandardShader shader;
	
	public WireframeMesh() {
		super(GL_LINES);
		
		shader = StandardShader.create();
	}

	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		shader.bind();
		shader.setMaterial(getMaterial());
		shader.setCamera(camera);
		shader.setModelMatrix(getTransformable().getTransformationMatrix());
		shader.setUniformSubroutines();
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
	
}
