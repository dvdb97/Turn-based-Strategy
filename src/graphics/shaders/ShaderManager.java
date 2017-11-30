package graphics.shaders;

import assets.light.LightSource;
import assets.material.Material;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import rendering.shaders.ShaderProgram;
import rendering.shaders.standardShaders.lightShader.LightShader;

public class ShaderManager {
	
	private static boolean initialized = false;
	
	private static LightShader lightShader;
	
	//TODO: Maybe change the name
	private static ShaderProgram shader;
	
	//More shaders that are needed
	
	public static void init(/* TODO: Graphics quality */) {
		
		lightShader = LightShader.createPerVertexLightShader();
		
		shader = new ShaderProgram("Shaders/shader.vert", "Shaders/shader.frag");
		
		initialized = true;
		
	}
	
	
	public static LightShader getLightShader() {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return null;
		}
		
		return lightShader;
		
	}
	
	
	public static void useLightShader(Matrix44f mMatrix, Matrix44f vMatrix, Matrix44f pMatrix, Vector3f camPos, LightSource light, Vector3f ambient, Material mat) {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
		lightShader.use();
		lightShader.prepareForRendering(mMatrix, vMatrix, pMatrix, camPos, light, ambient, mat);
		
	}
	
	
	public static void disableLightShader() {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
		lightShader.disable();
		
	}
	
	
	public static void useShader(Matrix44f mMatrix, Matrix44f vMatrix, Matrix44f pMatrix) {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
		//TODO: Might be in the wrong order
		Matrix44f mvpMatrix = pMatrix.times(vMatrix).times(mMatrix);
		
		shader.use();
		
		shader.setUniformMatrix4fv("mvpMatrix", mvpMatrix.toArray());
		
	}
	
	
	public static void disableShader() {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
		shader.disable();
		
	}

}
