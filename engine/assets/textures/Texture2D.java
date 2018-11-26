package assets.textures;

import static org.lwjgl.opengl.GL11.*;
<<<<<<< HEAD
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;

import java.nio.ByteBuffer;
import assets.textures.utils.Image;
=======
import java.nio.ByteBuffer;
>>>>>>> master
import assets.textures.utils.ImageLoader;


public class Texture2D extends Texture {	
<<<<<<< HEAD
	
	
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
		
=======

	public Texture2D(String path, int width, int height) {
		super(GL_TEXTURE_2D, width, height);
		
>>>>>>> master
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
	}
	
	
<<<<<<< HEAD
	public static Texture2D generateDepthTexture(int width, int height) {
		
		Texture2D texture = new Texture2D(width, height);
		
		texture.bind();
		
		glTexImage2D(texture.getType(), 0, GL_DEPTH_COMPONENT32, texture.getWidth(), texture.getHeight(), 0, GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer) null);
		
		texture.setFilter(GL_NEAREST);
		texture.setTextureWrap(GL_CLAMP_TO_EDGE);
		
		texture.unbind();
		
		return texture;
		
	}
	
	
	public void setImageData(String path) {
		bind();

		Image image = ImageLoader.loadImageRGBA(path);
		
		this.setWidth(image.getWidth());
		this.setHeight(image.getHeight());
		
		ByteBuffer buffer = image.getImageDataAsByteBuffer();
		
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		
		glTexImage2D(getType(), 0, GL_RGBA, this.getWidth(), this.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		this.setFilter(this.getFilterMode());
		this.setTextureWrap(this.getWrapMode());
		
		generateMipMapLevels();
=======
	public void setImageData(String path) {
		bind();

		ByteBuffer buffer = ImageLoader.loadImageRGBA(path).getImageDataAsByteBuffer();
		
		glTexImage2D(getType(), 0, GL_RGBA, this.getWidth(), this.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		generateMipMapLevels();
		
		this.setFilter(this.getFilterMode());
		this.setTextureWrap(this.getWrapMode());
>>>>>>> master
		
		unbind();
	}

}