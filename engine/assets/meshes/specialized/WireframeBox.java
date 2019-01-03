package assets.meshes.specialized;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import assets.meshes.Mesh;
import assets.meshes.geometry.Color;
import assets.scene.Scene;
import assets.shaders.standardShaders.StandardShader;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_LINES;

public class WireframeBox extends Mesh {	
	
	private StandardShader shader;
	
	public WireframeBox() {
		super(GL_LINES);
		
		shader = StandardShader.create();
		
		float[] positions = {
			-1f, 1f, 1f, 1f, 1f, 1f,
			1f, -1f, 1f, -1f, -1f, 1f,
			-1f, 1f, -1f, 1f, 1f, -1f,
			1f, -1f, -1f, -1f, -1f, -1f
		};
		
		int[] indices = {
			//Front face:
			0, 1, 1, 2, 2, 3, 3, 0,
			
			//Back Face:
			4, 5, 5, 6, 6, 7, 7, 4,
			
			//Connections between faces:
			0, 4, 1, 5, 2, 6, 3, 7
		};
		
		this.setPositionData(CustomBufferUtils.createFloatBuffer(positions));
		this.setIndexBuffer(CustomBufferUtils.createIntBuffer(indices));
		this.setMaterial(Material.standard);
	}
	
	
	public WireframeBox(Material material) {
		this();
		
		this.setMaterial(material);
	}
	
	
	public void setColor(Color color) {
		this.getMaterial().color = color;
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
