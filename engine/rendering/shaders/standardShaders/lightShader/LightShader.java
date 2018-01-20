package rendering.shaders.standardShaders.lightShader;


import java.util.LinkedList;

import assets.light.LightSource;
import assets.material.Material;
import assets.material.StandardMaterial;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import rendering.shaders.ShaderProgram;
import utils.FileUtils;


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
	
	
	
	public void prepareForRendering(Matrix44f modelMatrix, Matrix44f viewMatrix, Matrix44f projMatrix, Vector3f cameraPosition, LightSource light, Vector3f ambient, Material mat) {
		
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
		
		setAmbientLight(ambient);
		
		
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
	
	
	public void setLightSource(LightSource ls) {
		
		this.setUniformMatrix3fv("light.direction", ls.getDirection().toArray());
		
		this.setUniformMatrix3fv("light.color", ls.getColor().toArray());
		
	}

}
