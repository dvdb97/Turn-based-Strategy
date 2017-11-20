package rendering.shaders.standardShaders.lightShader;

import java.util.LinkedList;

import assets.light.LightSource;
import assets.material.Material;
import assets.material.StandardMaterial;
import lwlal.Matrix44f;
import lwlal.Vector3f;
import rendering.shaders.ShaderProgram;

public class LightShader extends ShaderProgram {
	
	//A constant for loading the files because I'm lazy to type it 4 times ¯\_(ツ)_/¯
	private static final String path = "Shaders/LightShaders/";

	
	//The list of LightSources to be rendered next render call
	private LinkedList<LightSource> lightSources = new LinkedList<LightSource>();
	
	private LightSource lightSource = new LightSource(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(1.0f, 1.0f, 1.0f));
	
	
	private StandardMaterial standardMaterial = new StandardMaterial();
	
	
	private LightShader(String vertPath, String fragPath) {
		super(vertPath, fragPath);
	}
	
	
	/*
	 * @param returns a LightShader that does per vertex light computing
	 */
	public static LightShader createPerVertexLightShader() {
		return new LightShader(path + "lightShaderPV.vert", path + "lightShaderPV.frag");
	}
	
	
	/*
	 * @param returns a LightShader that does per fragment light computing
	 */
	public static LightShader createPerFragmentLightShader() {
		return new LightShader(path + "lightShader.vert", path + "lightShader.frag");
	}
	
	
	
	public void prepareForRendering(Matrix44f modelMatrix, Matrix44f viewMatrix, Matrix44f projMatrix, Vector3f cameraPosition, LightSource light, Material mat) {
		
		this.setModelMatrix(modelMatrix);
		this.setViewMatrix(viewMatrix);
		this.setProjectionMatrix(projMatrix);
		
		this.setCameraPosition(cameraPosition);
		
		if (light == null) {
			this.setLightSource(this.lightSource);
		} else {
			this.lightSource = light;
		
			this.setLightSource(light);
		}
		
		
		if (mat == null) {
			this.setMaterial(standardMaterial);
		} else{
			this.setMaterial(mat);	
		}		
	}
	
	
	//*********************************** uniform setting *********************************
	
	
	public void setModelMatrix(Matrix44f modelMatrix) {
		
		this.setUniformMatrix4fv("modelMatrix", modelMatrix.toArray());
		
	}
	
	
	public void setViewMatrix(Matrix44f viewMatrix) {
		
		this.setUniformMatrix4fv("viewMatrix", viewMatrix.toArray());
		
	}
	
	
	public void setProjectionMatrix(Matrix44f projectionMatrix) {
		
		this.setUniformMatrix4fv("projectionMatrix", projectionMatrix.toArray());
		
	}
	
	
	public void setCameraPosition(Vector3f pos) {
		
		this.setUniform3fv("cameraPosition", pos.toArray());
		
	}
	
	
	public void setMaterial(Material mat) {
		
		this.setUniform3fv("material.emission", mat.emission.toArray());
		
		this.setUniform3fv("material.ambient", mat.ambient.toArray());
		
		this.setUniform3fv("material.diffuse", mat.diffuse.toArray());
		
		this.setUniform3fv("material.specular", mat.specular.toArray());
		
		this.setUniform1f("material.shininess", mat.shininess);
		
	}
	
	
	public void setLightSource(LightSource ls) {
		
		this.setUniform3fv("light.direction", ls.getDirection().toArray());
		
		this.setUniform3fv("light.color", ls.getColor().toArray());
		
	}

}
