package assets.meshes.prefabs;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.meshes.Mesh;
import assets.meshes.geometry.Color;
import assets.shaders.ShaderLoader;
import assets.shaders.ShaderProgram;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_LINES;

public class WireframeBox extends Mesh {	
	
	public WireframeBox() {
		super(GL_LINES);
		
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
		
		String path = "Shaders/StandardShaders/uniformColor";
		ShaderProgram shader = ShaderLoader.loadShader(path + ".vert", path + ".frag");
		this.setShader(shader);
	}
	
	
	public void setColor(Color color) {
		this.getMaterial().color = color;
	}
	

	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		getShader().bind();
		getShader().setCamera(camera);
		getShader().setModelMatrix(getTransformable().getTransformationMatrix());
		getShader().setUniformVector4f("u_Color", getMaterial().color);
	}

	@Override
	protected void onDrawEnd() {
		getShader().unbind();
	}

}
