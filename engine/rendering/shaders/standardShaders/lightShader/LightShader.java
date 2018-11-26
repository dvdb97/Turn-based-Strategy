package rendering.shaders.standardShaders.lightShader;


import assets.light.DirectionalLight;
import assets.material.Material;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import rendering.shaders.ShaderProgram;
import utils.FileUtils;


public class LightShader extends ShaderProgram {
	
	//A constant for loading the files because I'm lazy to type it 4 times ¯\_(ツ)_/¯
	private static final String path = "Shaders/LightShaders/";

	private LightShader(String vertPath, String fragPath) {
		super(vertPath, fragPath);
	}
	
	
	/*
	 * @param returns a LightShader that does per vertex light computing
	 */
	public static LightShader createPerVertexLightShader() {
		
		String vertSource = FileUtils.loadShaderSourceCode(path + "lightShaderPV.vert");
		String fragSource = FileUtils.loadShaderSourceCode(path + "lightShaderPV.frag");
		
		return new LightShader(vertSource, fragSource);
	}
	
	
	/*
	 * @param returns a LightShader that does per fragment light computing
	 */
	public static LightShader createPerFragmentLightShader() {
		
		String vertSource = FileUtils.loadShaderSourceCode(path + "lightShader.vert");
		String fragSource = FileUtils.loadShaderSourceCode(path + "lightShader.frag");
		
		return new LightShader(vertSource, fragSource);
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
		
		this.setUniformMatrix3fv("cameraPosition", pos.toArray());
		
	}
	
	
	public void setAmbientLight(Vector3f vec) {
		
		this.setUniformMatrix3fv("ambientLight", vec.toArray());
		
	}
	
	
	public void setMaterial(Material mat) {
		
		this.setUniformMatrix3fv("material.emission", mat.emission.toArray());
		
		this.setUniformMatrix3fv("material.ambient", mat.ambient.toArray());
		
		this.setUniformMatrix3fv("material.diffuse", mat.diffuse.toArray());
		
		this.setUniformMatrix3fv("material.specular", mat.specular.toArray());
		
		this.setUniform1f("material.shininess", mat.shininess);
		
	}
	
	
	public void setLightSource(DirectionalLight ls) {
		
		this.setUniformMatrix3fv("light.direction", ls.getLightDirection().toArray());
		
		this.setUniformMatrix3fv("light.color", ls.getColor().toArray());
		
	}

}
