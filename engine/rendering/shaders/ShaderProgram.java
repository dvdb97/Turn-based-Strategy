package rendering.shaders;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;

import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

import static org.lwjgl.opengl.GL11.GL_FALSE;

public class ShaderProgram {
	
	private int ID;
	
	private int vertShaderID;
	private int fragShaderID;
	
	
	//A hashmap we are going to store uniform locations in. Thus we don't have to look it up every time this uniform is used.
	private HashMap<String, Integer> uniformLocationMap = new HashMap<String, Integer>();
	
	
	public ShaderProgram(String vertSource, String fragSource) {
		init(vertSource, fragSource);
	}
	
	
	public void init(String vertSource, String fragSource) {
		
		ID = glCreateProgram();
		
		vertShaderID = glCreateShader(GL_VERTEX_SHADER);
		fragShaderID = glCreateShader(GL_FRAGMENT_SHADER);
		
		compileShader(vertSource, vertShaderID);
		compileShader(fragSource, fragShaderID);
		
		glAttachShader(ID, vertShaderID);
		glAttachShader(ID, fragShaderID);
		
		glLinkProgram(ID);
		
		if (glGetProgrami(ID, GL_LINK_STATUS) == GL_FALSE) {
			System.err.println("Failed to link shader!");
			
			int logLength = glGetProgrami(ID, GL_INFO_LOG_LENGTH);
			if (logLength > 1) {
				System.out.println(glGetProgramInfoLog(ID, logLength));
			}
		}
		
	}
	
	private void compileShader(String sourceCode, int id) {
		glShaderSource(id, sourceCode);
		glCompileShader(id);
		
		if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile Shader " + glGetShaderi(id, GL_SHADER_TYPE) + "!");
			//get InfoLog
			int logLength = glGetShaderi(id, GL_INFO_LOG_LENGTH);
			System.out.println("logLength = " + logLength);
			if (logLength > 1) {
				System.out.println(glGetShaderInfoLog(id, logLength));
			}
		}
	}

	
	/**
	 * 
	 * Pass an single float into the rendering pipeline
	 * 
	 * @param name The name of the uniform variable to address
	 * @param value The value of the uniform variable should have
	 */
	public void setUniform1f(String name, float value) {
		glUniform1f(getUniformLocation(name), value);
	}
	
	
	/**
	 * 
	 * Pass an single integer into the rendering pipeline
	 * 
	 * @param name The name of the uniform variable to address
	 * @param value The value of the uniform variable should have
	 */
	public void setUniform1i(String name, int value) {
		glUniform1i(getUniformLocation(name), value);
	}
	
	
	/**
	 * 
	 * Pass a vector into the rendering pipeline
	 * 
	 * @param name The name of the uniform variable to address
	 * @param value The value of the uniform variable should have
	 */
	public void setUniformVector(String name, float[] array) {
		glUniform4fv(getUniformLocation(name), array);
	}
	
	public void setUniformVector3f(String name, Vector3f vector) {
		setUniformVector(name, vector.toArray());
	}
	
	public void setUniformVector4f(String name, Vector4f vector) {
		setUniformVector(name, vector.toArray());
	}
	
	
	/**
	 * 
	 * Pass a 4x4 Matrix into the rendering pipeline
	 * 
	 * @param name The name of the uniform variable to address
	 * @param value The value of the uniform variable should have
	 */
	public void setUniformMatrix4fv(String name, float[] array) {
		glUniformMatrix4fv(getUniformLocation(name), false, array);
	}
	
	public void setUniformMatrix4fv(String name, Matrix44f matrix) {
		setUniformMatrix4fv(name, matrix.toArray());
	}
	
	
	/**
	 * 
	 * Pass a 3x3 Matrix into the rendering pipeline
	 * 
	 * @param name The name of the uniform variable to address
	 * @param value The value of the uniform variable should have
	 */
	public void setUniformMatrix3fv(String name, float[] array) {
		glUniform3fv(getUniformLocation(name), array);
	}
	
	public void setUniformMatrix3fv(String name, Matrix33f matrix) {
		setUniformMatrix3fv(name, matrix.toArray());
	}
	
	
	public int getUniformLocation(String name) {
		
		if (!uniformLocationMap.containsKey(name)) {
			uniformLocationMap.put(name, glGetUniformLocation(ID, name));
		}
		
		return uniformLocationMap.get(name);
				
	}
	
	public int getAttributeLocation(String name) {
		return glGetAttribLocation(ID, name);
	}
	
	
	public void use() {
		glUseProgram(ID);
	}
	
	public void disable() {
		glUseProgram(0);
	}

}
