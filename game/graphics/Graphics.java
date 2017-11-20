package graphics;

import lwlal.Matrix44f;
import rendering.shaders.standardShaders.lightShader.LightShader;
import shaders.StandardShader;

public class Graphics {
	
	//Shaders:
	private static LightShader lightShader;
	
	private static StandardShader shader;
	
	
	//*********************** Matrices ***********************
	
	//View Matrix: Defined by the camera position for this specific player
	private static Matrix44f viewMatrix;
	
	//Projection Matrix: Won't be changed
	private static Matrix44f projectionMatrix;
	
	//The combination of view and projection matrix. Will be computed once to save computations
	private static Matrix44f vpMatrix;

	
	//*********************** init ***********************
	
	
	public static void init(/* Settings for quality, etc. */) {
		
		lightShader = LightShader.createPerFragmentLightShader();
		
		shader = new StandardShader();
		
	}
	
	
	public static void renderIlluminatedModels() {
		
		
		
	}
	
	
	public static void renderModels() {
		
		
		
	}
	

}
