package shaders;

import java.util.ArrayList;

import assets.light.LightSource;
import assets.material.Material;
import lwlal.Matrix44f;
import rendering.shaders.ShaderProgram;

public class StandardShader {
	
	public static final int TEXTURED_RENDERING = 0;
	public static final int COLORED_RENDERING = 1;
	
	
	private ArrayList<ShaderProgram> shaders = new ArrayList<ShaderProgram>();
	
	private ArrayList<LightSource> lightSources = new ArrayList<LightSource>();
	
	
	private Matrix44f modelMatrix;
	
	private Matrix44f projectionMatrix;
	
	private Matrix44f viewMatrix;
	
	
	public StandardShader() {
		
		shaders.add(new ShaderProgram("texShader.vert", "texShader.frag"));
		
		shaders.add(new ShaderProgram("colShader.vert", "colShader.frag"));
		
	}
	
	
	//Adds a light source to this shader and returns an integer as a pointer to the light source
	public int addLightSource(LightSource light) {
		lightSources.add(light);
		
		return lightSources.indexOf(light);
	}
	
	
	public void removeLight(int pointer) {
		
		lightSources.remove(pointer);
	
	}
	
	
	public void setModelMatrix(Matrix44f matrix) {
		
		this.modelMatrix = modelMatrix;
		
	}
	
	
	public void setProjectionMatrix(Matrix44f matrix) {
		
		this.projectionMatrix = matrix;
	
	}
	
	
	public void setViewMatrix(Matrix44f matrix) {
		
		this.viewMatrix = matrix;
		
	}
	
	
	public void setMaterialShading(Material mat) {
		
	}
	
	
	public void onRenderStart() {
		
	}
	
	
	public void onRenderStop() {
		
	}
	
	
	private void setLights() {
		
	}

}
