package assets.light;

import assets.Bindable;
import assets.meshes.Model;
import assets.shaders.standardShaders.shadowMapping.ShadowMappingShader;
import assets.textures.Texture2D;
import interaction.Window;
import rendering.framebuffers.FrameBuffer;

import static org.lwjgl.opengl.GL11.*;


public class DepthBuffer implements Bindable {	

	private static ShadowMappingShader shader;
	
	private Texture2D depthTexture;
	
	private FrameBuffer frameBuffer;
	

	/**
	 * 
	 * @param width The width of the depth texture.
	 * @param height The height of the depth texture.
	 */
	public DepthBuffer(int width, int height) {
		this.frameBuffer = new FrameBuffer();
		this.depthTexture = Texture2D.generateDepthTexture(width, height);
		
		frameBuffer.addDepthAttachment(depthTexture);
		frameBuffer.disableDrawToColorBuffer();
		frameBuffer.disableReadFromColorBuffer();
		
		if (shader == null)
			shader = ShadowMappingShader.generateShader();
	}
	
	
	/**
	 * 
	 * Prepares the shadow map for rendering models to it.
	 * 
	 * @param light The light source we are computing shadows for.
	 */
	public void startRenderPass(DirectionalLight light) {
		shader.bind();
		shader.setCamera(light);
		frameBuffer.bind();
		glViewport(0, 0, depthTexture.getWidth(), depthTexture.getHeight());
		frameBuffer.clear();
		depthTexture.bind();
		
		glCullFace(GL_FRONT);
	}
	
	
	/**
	 * 
	 * @param model The model that is rendered to the shadow map.
	 */
	public void passToDepthBuffer(Model model) {
		//If the model isn't supposed to cast shadows, skip it.
		if (!model.getMaterial().castShadows)
			return;
		
		//Pass the model matrix to the shader.
		shader.setModelMatrix(model.getTransformable().getTransformationMatrix());
		
		//Render the model to the shadow map.
		model.passToShadowMap();
	}
	
	
	/**
	 * 
	 * Renders multiple models to the shadow map.
	 * 
	 * @param models The models to be rendered to the shadow map.
	 * @param light The light source we are computing shadows for.
	 */
	public void passToDepthBuffer(Model[] models, DirectionalLight light) {
		startRenderPass(light);
		
		for (Model model : models) {
			passToDepthBuffer(model);
		}
		
		endRenderPass();
	}
	
	
	/**
	 * Ends the current render pass.
	 */
	public void endRenderPass() {
		glCullFace(GL_BACK);
		
		depthTexture.unbind();
		glViewport(0, 0, Window.main.getWidth(), Window.main.getHeight());
		frameBuffer.unbind();
		shader.unbind();
	}
	
	
	/**
	 * 
	 * @return Returns the Shadow Map's depth texture.
	 */
	public Texture2D getDepthTexture() {
		return depthTexture;
	}
	
	
	@Override
	public void bind() {
		depthTexture.bind();
	}
	

	@Override
	public void unbind() {
		depthTexture.unbind();		
	}
	

	@Override
	public void delete() {
		frameBuffer.delete();
		depthTexture.delete();
	}
		
}
