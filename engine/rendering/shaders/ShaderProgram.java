package rendering.shaders;

import static org.lwjgl.opengl.GL20.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.regex.*;

import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

import static org.lwjgl.opengl.GL11.GL_FALSE;

public class ShaderProgram {
	
	private class Uniform {
		
		final String identifier;
		
		final String type;
		
		final int shader;
		
		final int location;
		
		public Uniform(String identifier, String type, int shader, int location) {
			this.identifier = identifier;
			this.type = type;
			this.location = location;
			this.shader = shader;
		}
		
	}
	
	private class Attribute {
		
		final String identifier;
		
		final String type;
		
		final int location;
		
		public Attribute(String identifier, String type, int location) {
			this.identifier = identifier;
			this.type = type;
			this.location = location;
		}
		
		public int getDataSize() {
			if (type.equals("vec3")) {
				return 3;
			}
			
			if (type.equals("vec4")) {
				return 4;
			}
			
			return 2;
		}
		
	}
	
	
	private static String type = "(vec.|mat.|int|float)";
	
	private static Pattern uniformPattern = Pattern.compile("uniform " + type + " (([A-Za-z0-9]+));");
	
	private static Pattern attributePattern = Pattern.compile("layout.location[ ]?=[ ]?(\\d). in " + type + " (([a-zA-Z0-9]+));");
	
	
	private int ID;
	
	private int vertShaderID;
	private int fragShaderID;
	
	
	//A hashmap we are going to store uniform locations in. Thus we don't have to look it up every time this uniform is used.
	private HashMap<String, Uniform> uniforms = null;
	
	//A hashmap we are going to store attribute locations in. Thus we don't have to look it up every time this uniform is used.
	private HashMap<String, Attribute> attributes = null;
	
	//The layout of the data that a mesh needs to have to match the shaders requirements
	private int layout;
	
	
	public ShaderProgram(String vertSource, String fragSource) {
		init(vertSource, fragSource);
	}
	
	
	/**
	 * 
	 * Parses the code for uniform and attribute values
	 * 
	 * @param vertSource
	 * @param fragSource
	 */
	private void parseCode(String vertSource, String fragSource) {
		parseUniforms(vertSource, fragSource);
		parseAttributes(vertSource);
		parseLayout();
	}
	
	
	/**
	 * 
	 * Stores all the information about the shader's uniform variables in
	 * a hashmap.
	 * 
	 * @param vert
	 * @param frag
	 */
	private void parseUniforms(String vert, String frag) {
		
		this.uniforms = new HashMap<String, Uniform>();
		
		//Extract all the uniforms out of the vertex shader 
		Matcher uniformMatcher = uniformPattern.matcher(vert);
		
		while (uniformMatcher.find()) {
			
			String type = uniformMatcher.group(1);
			
			String identifier = uniformMatcher.group(2);
			
			int location = getUniformLocation(identifier);
			
			uniforms.put(identifier, new Uniform(identifier, type, GL_VERTEX_SHADER, location));
			
		}
		
		
		//Extract all the uniforms out of the fragment shader
		uniformMatcher = uniformPattern.matcher(frag);
		
		while (uniformMatcher.find()) {
			
			String type = uniformMatcher.group(1);
			
			String identifier = uniformMatcher.group(2);
			
			int location = getUniformLocation(identifier);
			
			uniforms.put(identifier, new Uniform(identifier, type, GL_FRAGMENT_SHADER, location));
			
		}
		
	}
	
	
	/**
	 * 
	 * Stores all the information about the incoming attribute values in
	 * a hashmap.
	 * 
	 * @param vert
	 */
	private void parseAttributes(String vert) {
		
		this.attributes = new HashMap<String, Attribute>();
		
		Matcher attributeMatcher = attributePattern.matcher(vert);
		
		while (attributeMatcher.find()) {
			
			int location = Integer.parseInt(attributeMatcher.group(1));
			
			String type = attributeMatcher.group(2);
			
			String identifier = attributeMatcher.group(3);
			
			this.attributes.put(identifier, new Attribute(identifier, type, location));
			
		}
		
	}
	
	
	private void parseLayout() {
		
		Collection<Attribute> attribCollection = attributes.values();
		
		int layout = 0;
		
		for (int i = 0; i < 8; ++i) {
			
			for (Attribute attrib : attribCollection) {
				
				if (attrib.location == i) {
					layout |= attrib.getDataSize() << (i * 4);
				}
				
			}
			
		}
		
		this.layout = layout;
		
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
				
				return;
			}
		}
		
		parseCode(vertSource, fragSource);
		
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
	 * @param array The value for the uniform
	 */
	public void setUniform3fv(String name, float[] array) {
		glUniform3fv(getUniformLocation(name), array);
	}
	
	
	/**
	 * 
	 * Pass a 3x3 Matrix into the rendering pipeline
	 * 
	 * @param name The name of the uniform variable to address
	 * @param matrix The value for the uniform 
	 */
	public void setUnifrom3fv(String name, Matrix33f matrix) {
		setUniform3fv(name, matrix.toArray());
	}
	
	
	/**
	 * 
	 * Gets the uniform location in this shader programm
	 * 
	 * @param name The name of this uniform variable
	 * @return Returns the uniform's location
	 */
	public int getUniformLocation(String name) {
		
		if (uniforms == null) {
			return 0;
		}
		
		if (!uniforms.containsKey(name)) {
			return glGetUniformLocation(ID, name);
		}
		
		return uniforms.get(name).location;
				
	}
	
	
	/**
	 * 
	 * Gets the uniform's type 
	 * 
	 * @param name The name of the uniform variable
	 * @return Returns the type of the uniform as a String; Returns an empty String if the uniform doesn't exist.
	 */
	public String getUniformType(String name) {
		
		if (!uniforms.containsKey(name)) {
			return "";
		}
		
		return uniforms.get(name).type;
		
	}
	
	
	/**
	 * 
	 * Gets the attribute's location
	 * 	
	 * @param name The name of the attribute variable
	 * @return Returns the attribute's location
	 */
	public int getAttributeLocation(String name) {
		
		if (!attributes.containsKey(name)) {
			return -1;
		}
		
		return attributes.get(name).location;
		
	}
	
	
	/**
	 * 
	 * Gets the attribute's type 
	 * 
	 * @param name The name of the attribute variable
	 * @return Returns the type of the attribute as a String; Returns an empty String if the uniform doesn't exist.
	 */
	public String getAttributeType(String name) {
		
		if (!attributes.containsKey(name)) {
			return "";
		}
		
		return attributes.get(name).type;
		
	}
	
	
	/**
	 * 
	 * @return Returns the data layout required for this shader
	 */
	public int getLayout() {
		return layout;
	}
	
	
	public void use() {
		glUseProgram(ID);
	}
	
	public void disable() {
		glUseProgram(0);
	}

}
