package assets.meshes.specialized;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.meshes.Mesh;
import assets.scene.Scene;
import assets.shaders.ShaderLoader;
import assets.textures.Texture;
import utils.CustomBufferUtils;

public class Plane extends Mesh {
	
	private static final String path = "Shaders/StandardShaders/shaderTexturedMesh";
	
	public Plane(Texture texture) {
		super(ShaderLoader.loadShader(path + ".vert", path + ".frag"), null);
		
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
	}
	
	
	public Plane() {
		this(null);
	}


	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		// TODO Auto-generated method stub
		super.onDrawStart(camera, light);
	}


	@Override
	protected void onDrawEnd(Camera camera, DirectionalLight light) {
		// TODO Auto-generated method stub
		super.onDrawEnd(camera, light);
	}


	@Override
	protected void onDrawStart(Scene scene) {
		// TODO Auto-generated method stub
		super.onDrawStart(scene);
	}


	@Override
	protected void onDrawEnd(Scene scene) {
		// TODO Auto-generated method stub
		super.onDrawEnd(scene);
	}

}
