package assets.shaders;

import fontRendering.rendering.shader.TextRenderingShader;
import math.matrices.Matrix44f;
import math.vectors.Vector4f;

public class ShaderManager {
	
	private static boolean initialized = false;
	
	//TODO: Maybe change the name
	private static ShaderProgram shader;
	
	private static ShaderProgram shaderColorAsU;
	
	private static ShaderProgram texturedMeshShader;
	
	private static TextRenderingShader fontShader;
	
	//More shaders that are needed
	
	public static void init(/* TODO: Graphics quality */) {
		
		shader = ShaderLoader.loadShader("Shaders/StandardShaders/shader.vert", "Shaders/StandardShaders/shader.frag");
		
		shaderColorAsU = ShaderLoader.loadShader("Shaders/StandardShaders/shaderColorAsU.vert", "Shaders/StandardShaders/shaderColorAsU.frag");
		
		texturedMeshShader = ShaderLoader.loadShader("Shaders/StandardShaders/shaderTexturedMesh.vert", "Shaders/StandardShaders/shaderTexturedMesh.frag");
		
		fontShader = new TextRenderingShader();
		
		initialized = true;
		
	}
	
	
	public static void useShader(Matrix44f mMatrix, Matrix44f vMatrix, Matrix44f pMatrix, boolean colorAsUniform, Vector4f color) {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
		
		Matrix44f mvpMatrix = pMatrix.times(vMatrix).times(mMatrix);
		
		
		
		if (!colorAsUniform) {
						
			shader.bind();
			
			shader.setUniformMatrix4fv("mvpMatrix", mvpMatrix.toArray());
			
		} else {
			
			shaderColorAsU.bind();
			
			shaderColorAsU.setUniformMatrix4fv("mvpMatrix", mvpMatrix.toArray());	
			shaderColorAsU.setUniformVector4f("u_Color", color);
			
		}
		
		
		
	}
	
	
	public static void disableShader() {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
		shader.unbind();
		
	}
	
	
	public static void useTexturedMeshShaders(Matrix44f mvpMatrix) {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
		texturedMeshShader.bind();
		texturedMeshShader.setUniformMatrix4fv("u_mvpMatrix", mvpMatrix);
		
	}
	
	
	public static void disableTexturedMeshShader() {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
		texturedMeshShader.unbind();
		
	}
	
	
	public static void useFontShader(Matrix44f mvpMatrix, boolean textured) {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
<<<<<<< HEAD:engine/assets/shaders/ShaderManager.java
		fontShader.bind();
=======
		fontShader.use();
		
		if (textured) {
			fontShader.setUniform1i("u_textured", 1);
		} else {
			fontShader.setUniform1i("u_textured", 0);
		}
>>>>>>> master:src/graphics/shaders/ShaderManager.java
		fontShader.prepareForRendering(mvpMatrix);
		
	}
	
	
	public static void disableFontShader() {
		
		if (!initialized) {
			
			System.err.println("ShaderManager hasn't been initialized yet!");
			
			return;
			
		}
		
		fontShader.unbind();
	}

}
