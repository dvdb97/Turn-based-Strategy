package assets.textures;

import java.nio.ByteBuffer;
import assets.textures.utils.Image;
import assets.textures.utils.ImageLoader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL14.GL_GENERATE_MIPMAP;

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
	
	
	public Texture2D(String path, int filter, int mipmapFilter) {
		super(GL_TEXTURE_2D);
		setImageData(path, filter, mipmapFilter);
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
		this.setImageData(path);
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
	
	/**
	 * 
	 * @param bitmap byte buffer: [r1,g1,b1,a1,r2,g2,b2,a2,r3,...]
	 * @param width
	 * @param height
	 */
	public Texture2D(ByteBuffer bitmap, int width, int height) {
		super(GL_TEXTURE_2D, width, height);
		
		setImageData(new Image(width, height, bitmap), Texture.LINEAR, Texture.NEAREST);
	}
	
	
	/**
	 * 
	 * This function call was put into a seperate function to easily change
	 * the behavior of the other functions by overriding this function.
	 * 
	 * @param buffer The buffer of data to be used as this texture's data
	 */
	protected void setData(ByteBuffer buffer) {
		glTexImage2D(getType(), 0, GL_RGBA, this.getWidth(), this.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
	}
	
	
	/**
	 * 
	 * Set the pixel data for the texture.
	 * 
	 * @param image The image to be used as a texture
	 */
	public void setImageData(Image image) {
		this.setWidth(image.getWidth());
		this.setHeight(image.getHeight());
		
		this.setImageData(image.getImageDataAsByteBuffer());
	}
	
	
	public void setImageData(ByteBuffer buffer) {
		bind();
		
		glTexParameteri(getType(), GL_GENERATE_MIPMAP, GL_TRUE);
		
		setData(buffer);
		
		this.setFilter(this.getFilterMode());
		this.setTextureWrap(this.getWrapMode());
		generateMipMapLevels();
		
		unbind();
	}
	
	
	/**
	 * 
	 * Loads a texture from an image file with the given
	 * path.
	 * 
	 * @param path The path of the image file
	 */
	public void setImageData(String path) {
		setImageData(ImageLoader.loadImageRGBA(path));
	}
	
	
	/**
	 * 
	 * Set the pixel data for the texture.
	 * 
	 * @param image The image to be used as a texture
	 * @param filter The filter mode for the regular texture
	 * @param mipmapFilter The filter mode for the mipmap textures
	 */
	public void setImageData(Image image, int filter, int mipmapFilter) {
		this.setWidth(image.getWidth());
		this.setHeight(image.getHeight());
		
		this.setImageData(image.getImageDataAsByteBuffer(), filter, mipmapFilter);
	}
	
	
	public void setImageData(ByteBuffer buffer, int filter, int mipmapFilter) {
		bind();
		
		glTexParameteri(getType(), GL_GENERATE_MIPMAP, GL_TRUE);
		setData(buffer);
		
		this.setFilter(filter, mipmapFilter);
		this.setTextureWrap(this.getWrapMode());
		generateMipMapLevels();
		
		unbind();
	}
	
	
	/**
	 * 
	 * Set the pixel data for the texture.
	 * 
	 * @param path The path of the image file
	 * @param filter The filter mode for the regular texture
	 * @param mipmapFilter The filter mode for the mipmap textures
	 */
	public void setImageData(String path, int filter, int mipmapFilter) {
		setImageData(ImageLoader.loadImageRGBA(path), filter, mipmapFilter);
	}
	
	
	/**
	 * 
	 * Set the pixel data for the texture.
	 * 
	 * @param image The image to be used as a texture
	 * @param wrapMode The wrap mode for the texture
	 */
	public void setImageData(Image image, int wrapMode) {
		this.setWidth(image.getWidth());
		this.setHeight(image.getHeight());
		
		setImageData(image.getImageDataAsByteBuffer(), wrapMode);
	}
	
	
	public void setImageData(ByteBuffer buffer, int wrapMode) {
		bind();
		
		glTexParameteri(getType(), GL_GENERATE_MIPMAP, GL_TRUE);
		setData(buffer);
		
		this.setFilter(getFilterMode());
		this.setTextureWrap(wrapMode);
		generateMipMapLevels();
		
		unbind();
	}
	
	
	/**
	 * 
	 * Set the pixel data for the texture.
	 * 
	 * @param path The path of the image file
	 * @param wrapMode The wrap mode for the texture
	 */
	public void setImageData(String path, int wrapMode) {
		setImageData(ImageLoader.loadImageRGBA(path), wrapMode);
	}
	
	
	/**
	 * 
	 * Set the pixel data for the texture.
	 * 
	 * @param image The image to be used as a texture
	 * @param filter The filter mode for the regular texture
	 * @param mipmapFilter The filter mode for the mipmap textures
	 * @param wrapMode The wrap mode for the texture
	 */
	public void setImageData(Image image, int filter, int mipmapFilter, int wrapMode) {
		this.setWidth(image.getWidth());
		this.setHeight(image.getHeight());
		
		setImageData(image.getImageDataAsByteBuffer(), filter, mipmapFilter, wrapMode);			
	}
	
	
	public void setImageData(ByteBuffer buffer, int filter, int mipmapFilter, int wrapMode) {
		bind();
		
		glTexParameteri(getType(), GL_GENERATE_MIPMAP, GL_TRUE);
		setData(buffer);
		
		this.setFilter(filter, mipmapFilter);
		this.setTextureWrap(wrapMode);
		generateMipMapLevels();
		
		unbind();
	}
	
	
	/**
	 * 
	 * Set the pixel data for the texture.
	 * 
	 * @param path The path of the image file
	 * @param filter The filter mode for the regular texture
	 * @param mipmapFilter The filter mode for the mipmap textures
	 * @param wrapMode The wrap mode for the texture
	 */
	public void setImageData(String path, int filter, int mipmapFilter, int wrapMode) {
		setImageData(ImageLoader.loadImageRGBA(path), filter, mipmapFilter, wrapMode);
	}
	
	
	/**
	 * 
	 * Generates a texture that can be used as the depth component of
	 * a framebuffer.
	 * 
	 * @param width The width of the depth texture.
	 * @param height The height of the depth texture.
	 * @return Returns an empty Texture2D as a depth texture.
	 */
	public static Texture2D generateDepthTexture(int width, int height) {
		Texture2D texture = new Texture2D(width, height);
		
		texture.bind();
		
		glTexImage2D(texture.getType(), 0, GL_DEPTH_COMPONENT32, texture.getWidth(), texture.getHeight(), 0, GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer) null);
		
		texture.setFilter(Texture.NEAREST);
		texture.setTextureWrap(Texture.CLAMP_TO_EDGE);
		
		texture.unbind();
		
		return texture;
	}
	
	
	public static Texture2D generateColorTexture(int width, int height) {
		Texture2D texture = new Texture2D(width, height);
		texture.setImageData((ByteBuffer) null);
		
		return texture;
	}
	
}

