package assets.light;

import assets.Bindable;
import assets.cameras.Camera;
import assets.meshes.Model;
import assets.shaders.standardShaders.shadowMapping.ShadowMappingShader;
import assets.textures.Texture2D;
import rendering.framebuffers.FrameBuffer;

public class ShadowMap implements Bindable {	

	private static ShadowMappingShader shader;
	
	private Texture2D depthTexture;
	
	private FrameBuffer frameBuffer;
	

	/**
	 * 
	 * @param width The width of the depth texture.
	 * @param height The height of the depth texture.
	 */
	public ShadowMap(int width, int height) {
		this.frameBuffer = new FrameBuffer();
		this.depthTexture = Texture2D.generateDepthTexture(width, height);
		
		frameBuffer.addDepthAttachment(depthTexture);
		frameBuffer.disableDrawToColorBuffer();
		frameBuffer.disableReadFromColorBuffer();
		
		if (shader == null)
			shader = ShadowMappingShader.generateShader();
	}
	
	
	public void startRenderPass(DirectionalLight light, Camera camera) {
		shader.use();
		shader.prepareForRendering(light.getViewMatrix(), light.getProjectionMatrix());
	}
	
	
	public void render(Model model) {
		shader.setModelMatrix(model.getTransformable().getTransformationMatrix());
		
		model.renderToShadowMap();
	}
	
	
	public void endRenderPass() {
		shader.disable();
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
