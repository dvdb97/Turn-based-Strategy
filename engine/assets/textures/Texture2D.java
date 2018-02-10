package assets.textures;


import static org.lwjgl.opengl.GL11.*;
import java.nio.IntBuffer;

import assets.textures.utils.Image;
import assets.textures.utils.ImageLoader;
import utils.CustomBufferUtils;


public class Texture2D extends Texture {	

	
	public Texture2D(String path, int width, int height) {
		super(GL_TEXTURE_2D, width, height);
		
		setImageData(path);
	}
	
	
	public Texture2D(int width, int height) {
		super(GL_TEXTURE_2D, width, height);
	}
	
	
	public void setImageData(String path) {
		bind();
		
		this.setFilter(this.getFilterMode());
		this.setTextureWrap(this.getWrapMode());
		
		IntBuffer buffer = CustomBufferUtils.createIntBuffer(ImageLoader.loadImageDataAsRGBAInt(path));
		
		glTexImage2D(getType(), 0, GL_RGBA, this.getWidth(), this.getHeight(), 0, GL_RGBA, GL_UNSIGNED_INT, buffer);
		
		unbind();
	}

}
