package assets.shaders;

import static org.lwjgl.opengl.GL20.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.*;

import assets.Bindable;
import assets.IBindable;
import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

import static org.lwjgl.opengl.GL11.GL_FALSE;

public class ShaderProgram extends Bindable {
	
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

		@Override
		public String toString() {
			return "location " + location + ": " + type + " " + identifier;
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
	
	//The view projection matrix used for the current pass. It will be stored here to use it in later computation
	protected Matrix44f viewProjectionMatrix;
	
	
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
	 * Passes the material of the model as a uniform to the shader.
	 * 
	 * @param material The material of the model.
	 */
	public void setMaterial(Material material) {
		this.setUniformVector4f("material.color", material.color);
		this.setUniformVector3f("material.emission", material.emission);
		this.setUniformVector3f("material.ambient", material.ambient);
		this.setUniformVector3f("material.diffuse", material.diffuse);
		this.setUniformVector3f("material.specular", material.specular);
		this.setUniform1f("material.shininess", material.shininess);
	}
	
	
	/**
	 * 
	 * Passes the model-, view- and projection matrix to the shader.
	 * 
	 * Every single matrix and the combined MVP-Matrix will be passed to
	 * the shader.
	 * 
	 * @param model The model's model matrix.
	 * @param view The camera's view matrix.
	 * @param projection The projection matrix.
	 */
	public void setModelMatrix(Matrix44f model) {
		//Pass the model matrix to the shader
		this.setUniformMatrix4fv("modelMatrix", model);
		
		//Combine the matrices and pass the result to the shader.
		this.setUniformMatrix4fv("mvpMatrix", viewProjectionMatrix.times(model));
	}
	
	
	/**
	 * 
	 * Sets the values of all the camera related uniform variables
	 * 
	 * @param camera The camera the scene is rendered with.
	 */
	public void setCamera(Camera camera) {
		//Pass the camera's position to the shader.
		this.setUniform3fv("cameraPosition", camera.getPosition().toArray());
		
		//Set the view-projection matrix as a variable to use it again when a model matrix is set.
		this.viewProjectionMatrix = camera.getViewProjectionMatrix();
		
		//Pass the view and projection matrix to the shader.
		this.setUniformMatrix4fv("viewMatrix", camera.getViewMatrix());
		this.setUniformMatrix4fv("projectionMatrix", camera.getProjectionMatrix());
	}
	
	
	/**
	 * 
	 * Sets the values of all the light source related uniform variables
	 * 
	 * @param light The light source
	 * @param lightViewMatrix The matrix that moves the model into the view space of the light source
	 * @param lightProjectionMatrix The projection matrix that is used for shadow mapping
	 * @param shadows Enables or disables trying to read from a shadow map.
	 */	
	public void setLightSource(DirectionalLight light, boolean shadows) {
		this.setUniformVector3f("light.direction", light.getViewDirection());
		
		this.setUniformVector3f("light.color", light.getColor());
		
		this.setAmbientLight(light.getAmbient());
		
		this.setLightViewProjectionMatrix(light.getViewProjectionMatrix());
		
		if (shadows) {
			this.setUniform1i("shadowsActive", 1);
			light.getShadowMap().bind();
		} else {
			this.setUniform1i("shadowsActive", 0);
		}
	}
	
	
	/**
	 * 
	 * Passes the light view projection matrix as an uniform variable to the gpu
	 * 
	 * @param lightViewMatrix The matrix that moves the model into the view space of the light source
	 */
	public void setLightViewProjectionMatrix(Matrix44f lightViewProjectionMatrix) {
		this.setUniformMatrix4fv("lightVPMatrix", lightViewProjectionMatrix);
	}
	
	
	public void setAmbientLight(Vector3f vec) {
		this.setUniform3fv("ambientLight", vec.toArray());
	}
	
	
	/**
	 * 
	 * Sets the values of all the light source related uniform variables.
	 * Sets all shadow related variables to a default value.
	 * 
	 * @param light The light source
	 */
	public void setLightSource(DirectionalLight light) {
		this.setLightSource(light, false);
	}
	
	
	/**
	 * 
	 * Pass an single float to the shader.
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
	 * Pass a Vector3f as a uniform to the shader.
	 * 
	 * @param name The identifier of the uniform variable.
	 * @param vector The vector to be passed to the shader.
	 */
	public void setUniformVector3f(String name, Vector3f vector) {
		glUniform3fv(getUniformLocation(name), vector.toArray());
	}
	
	
	/**
	 * 
	 * Pass a Vector4f as a uniform to the shader.
	 * 
	 * @param name The identifier of the uniform variable.
	 * @param vector The vector to be passed to the shader.
	 */
	public void setUniformVector4f(String name, Vector4f vector) {
		glUniform4fv(getUniformLocation(name), vector.toArray());
	}
	
	
	/**
	 * 
	 * Pass a 4x4 Matrix into the rendering pipeline
	 * 
	 * @param name The name of the uniform variable to address
	 * @param value The value of the uniform variable should have. In this case as an array.
	 */
	public void setUniformMatrix4fv(String name, float[] array) {
		glUniformMatrix4fv(getUniformLocation(name), false, array);
	}
	
	
	/**
	 * 
	 * Pass a 4x4 Matrix into the rendering pipeline
	 * 
	 * @param name The name of the uniform variable to address.
	 * @param value The value of the uniform variable should have.
	 */
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
	public void setUniform3fv(String name, Matrix33f matrix) {
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
	
	
	public void bind() {
		glUseProgram(ID);
	}
	
	public void unbind() {
		glUseProgram(0);
	}


	@Override
	public void delete() {
		glDeleteProgram(ID);
		glDeleteShader(vertShaderID);
		glDeleteProgram(fragShaderID);
	}

}
