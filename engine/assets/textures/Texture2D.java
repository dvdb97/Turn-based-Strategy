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
		
		this.setFilter(filter);
		this.setTextureWrap(wrapMode);
		
		glTexImage2D(getType(), 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image.getImageDataAsByteBuffer());
		
		glGenerateMipmap(getType());
		
		unbind();
	}
	
	
	public void renderToTexture() {
		
	}

}
