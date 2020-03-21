package assets.shaders;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL40.*;

import java.util.HashMap;
import java.util.regex.*;

import assets.Bindable;
import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import assets.textures.Texture;
import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

import static org.lwjgl.opengl.GL11.GL_FALSE;


/**
 * Exception that will be thrown on attempt to set a uniform
 * that doesn't exist.
 */
class UniformNotFoundException extends RuntimeException {}


public class ShaderProgram extends Bindable {
	
	public static final int VERTEX_SHADER = GL_VERTEX_SHADER;
	public static final int FRAGMENT_SHADER = GL_FRAGMENT_SHADER;
	
	private static String type = "(vec.|mat.|int|float)";
	
	private static Pattern uniformPattern = Pattern.compile("uniform " + type + " (([A-Za-z0-9]+));");
	private static Pattern attributePattern = Pattern.compile("layout.location[ ]?=[ ]?(\\d). in " + type + " (([a-zA-Z0-9]+));");
	
	private int ID;
	
	private int vertShaderID;
	private int fragShaderID;
	
	
	//A hashmap in which we are going to store uniform locations in.
	private HashMap<String, Integer> uniforms = null;
	
	//A hashmap in which we are going to store attribute locations in.
	private HashMap<String, Integer> attributes = null;
	
	//A hashmap in which we will store the corresponding texture targets for each uniform sampler.
	private HashMap<String, Integer> texTargets = null;
	private int usedTextureUnits = 0;
	
	//A hashmap in which we are going to store subroutine uniform locations in.
	private HashMap<String, Integer> subroutineUniforms = null;
	
	//A hashmap in which we will store the ids of the subroutine functions.
	private HashMap<String, Integer> subroutineFunctions = null;
	
	//Arrays that maps all subroutine uniforms with the assigned subroutines.
	private int[] fragSubroutines;
	private int[] vertSubroutines;
	
	//The view projection matrix used for the current pass. It will be stored here to use it in later computation
	protected Matrix44f viewProjectionMatrix;
	
	
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
				
				return;
			}
		}
		
		bind();
		parseCode(vertSource, fragSource);
		unbind();
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
	 * Parses the code for uniform and attribute values
	 * 
	 * @param vertSource
	 * @param fragSource
	 */
	private void parseCode(String vertSource, String fragSource) {
		parseUniforms(vertSource, fragSource);
		parseAttributes(vertSource);
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
		//Set up all look-up tables for uniform and attribute locations.
		this.uniforms = new HashMap<String, Integer>();
		this.texTargets = new HashMap<String, Integer>();
		this.subroutineUniforms = new HashMap<String, Integer>();
		this.subroutineFunctions = new HashMap<String, Integer>();
		
		//The values set to all subroutine uniforms.
		this.vertSubroutines = new int[getActiveSubroutineUniforms(VERTEX_SHADER)];
		this.fragSubroutines = new int[getActiveSubroutineUniforms(FRAGMENT_SHADER)];
		
		//Extract all the uniforms out of the vertex shader 
		Matcher uniformMatcher = uniformPattern.matcher(vert);
		
		while (uniformMatcher.find()) {
			String identifier = uniformMatcher.group(2);
			int location = getUniformLocation(identifier);
			uniforms.put(identifier, location);
		}
		
		//Extract all the uniforms out of the fragment shader
		uniformMatcher = uniformPattern.matcher(frag);
		
		while (uniformMatcher.find()) {
			String identifier = uniformMatcher.group(2);
			int location = getUniformLocation(identifier);
			uniforms.put(identifier, location);
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
		
		this.attributes = new HashMap<String, Integer>();
		
		Matcher attributeMatcher = attributePattern.matcher(vert);
		
		while (attributeMatcher.find()) {
			int location = Integer.parseInt(attributeMatcher.group(1));
			String identifier = attributeMatcher.group(3);
			this.attributes.put(identifier, location);
		}
		
	}

	
	/**
	 * 
	 * Passes the material of the model as a uniform to the shader.
	 * 
	 * @param material The material of the model.
	 */
	public void setMaterial(Material material) {
		this.setUniformVector4f("material.color", material.color.toVector4f());
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
	
	
	public void setViewMatrix(Matrix44f view) {
		this.setUniformMatrix4fv("viewMatrix", view);
	}
	
	
	public void setProjectionMatrix(Matrix44f projection) {
		this.setUniformMatrix4fv("projectionMatrix", projection);
	}
	
	
	public void setViewProjectionMatrix(Matrix44f vpMatrix) {
		this.setUniformMatrix4fv("vpMatrix", vpMatrix);
	}
	
	
	/**
	 * 
	 * Passes the MVPMatrix as an uniform to the shader. 
	 * 
	 * @param matrix The matrix to use as the MVPMatrix.
	 */
	public void setMVPMatrix(Matrix44f matrix) {
		this.setUniformMatrix4fv("mvpMatrix", matrix);
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
		this.setViewMatrix(camera.getViewMatrix());
		this.setProjectionMatrix(camera.getViewProjectionMatrix());
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
			bindTexture("shadowMap", light.getShadowMap().getDepthTexture());
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
	 * Assigns a subroutine to a subroutine uniform.
	 * 
	 * @param uniform The name of the subroutine uniform.
	 * @param subroutine The name of the subroutine function.
	 * @param stage The shader stage.
	 */
	public void setUniformSubroutine(String uniform, String subroutine, int stage) {
		if (stage == VERTEX_SHADER) {
			vertSubroutines[getSubroutineUniformLocation(uniform, stage)] = getSubroutineIndex(subroutine, stage);
		} else {
			fragSubroutines[getSubroutineUniformLocation(uniform, stage)] = getSubroutineIndex(subroutine, stage);
		}
	}
	
	
	/**
	 * Uplaods all subroutine settings to the gpu.
	 */
	public void setUniformSubroutines() {
		glUniformSubroutinesuiv(VERTEX_SHADER, vertSubroutines);
		glUniformSubroutinesuiv(FRAGMENT_SHADER, fragSubroutines);
	}
	
	
	/**
	 * 
	 * @param stage The shader stage.
	 * @return Returns the number of active subroutine uniforms in the specified shader stage.
	 */
	public int getActiveSubroutineUniforms(int stage) {		
		return glGetProgramStagei(ID, stage, GL_ACTIVE_SUBROUTINE_UNIFORMS);
	}
	
	
	/**
	 * 
	 * Returns the index associated with the shader subroutine
	 * with the given name.
	 * 
	 * @param name The name of the subroutine
	 * @param stage The shader stage.
	 * @return Returns the index of the subroutine.
	 */
	public int getSubroutineIndex(String name, int stage) {
		if (!subroutineFunctions.containsKey(name)) {
			int index = glGetSubroutineIndex(ID, stage, name);
			
			if (index == -1) {
				System.err.println("ERROR L455: The subroutine " + name + " doesn't exist!");
				
				return index;
			}
			
			subroutineFunctions.put(name, index);
		}
		
		return subroutineFunctions.get(name);
	}
	
	
	/**
	 * 
	 * Returns the subroutine uniform location associated with the
	 * given name.
	 * 
	 * @param name The name of the subroutine uniform.
	 * @param stage The shader stage.
	 * @return Returns the location of the subroutine uniform.
	 */
	public int getSubroutineUniformLocation(String name, int stage) {
		if (!subroutineUniforms.containsKey(name)) {
			int index = glGetSubroutineUniformLocation(ID, stage, name);
			
			if (index == -1) {
				System.err.println("ERROR L481: The subroutine uniform " + name + " doesn't exist!");
				
				return index;
			}
			
			subroutineUniforms.put(name, index);
		}
		
		return subroutineUniforms.get(name);
	}

	
	/**
	 * 
	 * Binds a texture to a specific target.
	 * 
	 * @param name The name of the texture target (e.g. the name of the uniform sampler).
	 * @param texture The texture to bind to the target.
	 */
	public void bindTexture(String name, Texture texture) {
		if (!texTargets.containsKey(name)) {
			this.texTargets.put(name, usedTextureUnits);
			++usedTextureUnits;
		}
		
		setUniform1i(name, texTargets.get(name));
		glActiveTexture(GL_TEXTURE0 + texTargets.get(name));
		texture.bind();
	}
	
	
	/**
	 * 
	 * Gets the uniform location in this shader programm
	 * 
	 * @param name The name of this uniform variable
	 * @return Returns the uniform's location
	 */
	public int getUniformLocation(String name) {		
		if (!uniforms.containsKey(name)) {
			int index = glGetUniformLocation(ID, name);
			
			if (index == -1) {				
				return index;
			}
			
			this.uniforms.put(name, index);
		}
		
		return uniforms.get(name);	
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
			System.err.println("ERROR L550: The attribute " + name + " doesn't exist!");
			
			return -1;
		}
		
		return attributes.get(name);
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
