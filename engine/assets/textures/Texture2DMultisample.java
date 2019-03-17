package assets.textures;

import static org.lwjgl.opengl.GL32.*;

import java.nio.ByteBuffer;

public class Texture2DMultisample extends Texture {
	
	private int numSamples;
	
	public Texture2DMultisample(int width, int height, int numSamples) {
		super(GL_TEXTURE_2D_MULTISAMPLE);
		this.numSamples = numSamples;
		
		this.setWidth(width);
		this.setHeight(height);
		
		allocateStorage();
	}
	

	private void allocateStorage() {
		bind();
		glTexImage2DMultisample(getType(), numSamples, GL_RGBA, getWidth(), getHeight(), false);
		unbind();
	}

}
