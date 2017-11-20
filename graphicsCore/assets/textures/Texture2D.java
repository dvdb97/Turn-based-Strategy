package assets.textures;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL42.*;
import assets.textures.utils.Image;
import assets.textures.utils.ImageLoader;

public class Texture2D extends Texture {	
	
	private int width;
	
	private int height;
	
	private int mipmapLevels;
	
	
	public Texture2D(String path, int mipmapLevels, int filter, int wrapMode) {
		super(GL_TEXTURE_2D);
		
		setImageData(path, mipmapLevels, filter, wrapMode);
	}
	
	
	public Texture2D(int width, int height) {
		super(GL_TEXTURE_2D);
	}
	
	
	public void setImageData(String imagePath, int mipmapLevels, int filter, int wrapMode) {
		bind();
		
		if (mipmapLevels < 1) {
			mipmapLevels = 1;
		}		
		
		Image image = ImageLoader.loadImageRGBA(imagePath);
		
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.mipmapLevels = mipmapLevels;
		
		glTexStorage2D(getType(), this.mipmapLevels, GL_RGBA8, width, height);
		
		this.setFilter(filter);
		this.setTextureWrap(wrapMode);
		
		glTexSubImage2D(getType(), 0, 0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, image.getImageDataAsByteBuffer());
		
		if (mipmapLevels > 1) {
			glGenerateMipmap(getType());
		}
		
		unbind();
	}
	
	
	public void bind() {
		glBindTexture(getType(), getID());
	}
	
	
	public void unbind() {
		glBindTexture(getType(), 0);
	}

}
