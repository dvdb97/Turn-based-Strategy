package assets.light;

import java.util.List;

import assets.Bindable;
import assets.cameras.Camera;
import assets.meshes.Mesh;
import assets.textures.Texture2D;
import interaction.Window;
import rendering.framebuffers.FrameBuffer;

public class CascadedShadowMap implements Bindable {
	
	private int numCascades;
	
	private FrameBuffer frameBuffer;
	
	private Texture2D[] depthTextures;
	
	
	public CascadedShadowMap(int numCascades) {
		this.numCascades = numCascades;
		this.depthTextures = new Texture2D[numCascades];
		this.frameBuffer = new FrameBuffer();
		
		for (int i = 0; i < numCascades; ++i) {
			depthTextures[i] = Texture2D.generateDepthTexture(Window.main.getWidth(), Window.main.getHeight());
		}
		
		frameBuffer.addDepthAttachment(depthTextures[0]);
		
		frameBuffer.disableDrawToColorBuffer();
		frameBuffer.disableReadFromColorBuffer();
	}
	
	
	public void render(List<Mesh> meshes, Camera camera) {
		
	}
	
	
	public int getNumberOfCascades() {
		return numCascades;
	}


	@Override
	public void bind() {
		
	}


	@Override
	public void unbind() {
		
	}


	@Override
	public void delete() {
		frameBuffer.delete();
		
		for (Texture2D texture : depthTextures)
			texture.delete();
	}

}
