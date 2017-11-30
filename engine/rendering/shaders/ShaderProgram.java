package rendering.shaders;


import utils.FileUtils;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.GL_FALSE;


public class ShaderProgram {
	
	int ID;
	
	int vertShaderID;
	int fragShaderID;
	
	
	public ShaderProgram(String vertPath, String fragPath) {
		init(vertPath, fragPath);
	}
	
	
	public void init(String vertPath, String fragPath) {
		
		ID = glCreateProgram();
		
		vertShaderID = glCreateShader(GL_VERTEX_SHADER);
		fragShaderID = glCreateShader(GL_FRAGMENT_SHADER);
		
		String vertSource = FileUtils.loadShaderSourceCode(vertPath);
		String fragSource = FileUtils.loadShaderSourceCode(fragPath);
		
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
	
	public void compileShader(String sourceCode, int id) {
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

	public void setUniform1f(String name, float value) {
		glUniform1f(getUniformLocation(name), value);
	}

	/*public void setUniform3f(String name, Vector values) {
		glUniform3f(getUniformLocation(name), values.x, values.y, values.z);
	}*/
	
	public void setUniform1i(String name, int value) {
		glUniform1i(getUniformLocation(name), value);
	}
	
	public void setUniformfv(String name, float[] array) {
		glUniform1fv(getUniformLocation(name), array);
	}
	
	public void setUniformMatrix4fv(String name, float[] array) {
		glUniformMatrix4fv(getUniformLocation(name), false, array);
	}
	
	public void setUniform3fv(String name, float[] array) {
		glUniform3fv(getUniformLocation(name), array);
	}
	
	public int getUniformLocation(String name) {
		return glGetUniformLocation(ID, name);
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
