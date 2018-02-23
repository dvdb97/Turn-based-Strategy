package assets.textures;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT16;

import java.nio.ByteBuffer;

import assets.textures.utils.Image;
import assets.textures.utils.ImageLoader;


public class Texture2D extends Texture {	
	
	
	/**
	 * Generates a 2D Texture without any of the properties specified
	 */
	public Texture2D() {
		super(GL_TEXTURE_2D);
	}
	

	/**
	 * Generates a 2D texture by loading the data of an image file
	 * 
	 * @param path The path of the image file
	 */
	public Texture2D(String path) {
		super(GL_TEXTURE_2D);
		
		setImageData(path);
	}
	
	
	/**
	 * Generates a 2D texture by loading the data of an image file. It also sets 
	 * the number of mipmap levels.
	 * 
	 * @param path
	 * @param mipmapLevels
	 */
	public Texture2D(String path, int mipmapLevels) {
		super(GL_TEXTURE_2D);
		
		this.setMipMapLevels(mipmapLevels);
		
		setImageData(path);
	}
	
	
	/**
	 * Generates an empty 2D texture for storing depth values with the given width and height.
	 * 
	 * @param width The width of the texture
	 * @param height The height of the texture
	 */
	public Texture2D(int width, int height) {
		super(GL_TEXTURE_2D, width, height);
		
		generateEmptyTexture();
	}
	
	
	public void generateEmptyTexture() {
		
		bind();
		
		glTexImage2D(getType(), 0, GL_DEPTH_COMPONENT16, this.getWidth(), this.getHeight(), 0, GL_DEPTH_COMPONENT, GL_UNSIGNED_BYTE, 0);
		
		generateMipMapLevels();
		
		this.setFilter(this.getFilterMode());
		this.setTextureWrap(this.getWrapMode());
		
		unbind();
		
	}
	
	
	public void setImageData(String path) {
		bind();

		Image image = ImageLoader.loadImageRGBA(path);
		
		this.setWidth(image.getWidth());
		this.setHeight(image.getHeight());
		
		ByteBuffer buffer = image.getImageDataAsByteBuffer();
		
		glTexImage2D(getType(), 0, GL_RGBA, this.getWidth(), this.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		generateMipMapLevels();
		
		this.setFilter(this.getFilterMode());
		this.setTextureWrap(this.getWrapMode());
		
		unbind();
	}

}