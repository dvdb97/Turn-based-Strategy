package graphics.shaders;

import assets.light.LightSource;
import assets.material.Material;
import assets.meshes.geometry.Color;
import fontRendering.rendering.shader.TextRenderingShader;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import rendering.shaders.ShaderLoader;
import rendering.shaders.ShaderProgram;
import rendering.shaders.standardShaders.lightShader.LightShader;

public class ShaderManager {
	
	private static boolean initialized = false;
	
	private static LightShader lightShader;
	
	//TODO: Maybe change the name
	private static ShaderProgram shader;
	
	private static ShaderProgram shaderColorAsU;
	
	private static ShaderProgram texturedMeshShader;
	
	private static TextRenderingShader fontShader;
	
	//More shaders that are needed
	
	public static void init(/* TODO: Graphics quality */) {
		
		lightShader = LightShader.createPerFragmentLightShader();
		
		shader = ShaderLoader.loadShader("Shaders/StandardShaders/shader.vert", "Shaders/StandardShaders/shader.frag");
		
		shaderColorAsU = ShaderLoader.loadShader("Shaders/StandardShaders/shaderColorAsU.vert", "Shaders/StandardShaders/shaderColorAsU.frag");
		
		texturedMeshShader = ShaderLoader.loadShader("Shaders/StandardShaders/shaderTexturedMesh.vert", "Shaders/StandardShaders/shaderTexturedMesh.frag");
		
		fontShader = new TextRenderingShader();
		
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
	
	
	public static void useShader(Matrix44f mMatrix, Matrix44f vMatrix, Matrix44f pMatrix, boolean colorAsUniform, Vector4f color) {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
		
		Matrix44f mvpMatrix = pMatrix.times(vMatrix).times(mMatrix);
		
		
		
		if (!colorAsUniform) {
						
			shader.use();
			
			shader.setUniformMatrix4fv("mvpMatrix", mvpMatrix.toArray());
			
		} else {
			
			shaderColorAsU.use();
			
			shaderColorAsU.setUniformMatrix4fv("mvpMatrix", mvpMatrix.toArray());	
			shaderColorAsU.setUniformVector4f("u_Color", color);
			
		}
		
		
		
	}
	
	
	public static void disableShader() {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
		shader.disable();
		
	}
	
	
	public static void useTexturedMeshShaders(Matrix44f mvpMatrix) {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
		texturedMeshShader.use();
		texturedMeshShader.setUniformMatrix4fv("u_mvpMatrix", mvpMatrix);
		
	}
	
	
	public static void disableTexturedMeshShader() {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
		texturedMeshShader.disable();
		
	}
	
	
	public static void useFontShader(Matrix44f mvpMatrix, boolean textured) {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
		fontShader.use();
		
		if (textured) {
			fontShader.setUniform1i("u_textured", 1);
		} else {
			fontShader.setUniform1i("u_textured", 0);
		}
		fontShader.prepareForRendering(mvpMatrix);
		
	}
	
	
	public static void disableFontShader() {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
		fontShader.disable();
	}

}
