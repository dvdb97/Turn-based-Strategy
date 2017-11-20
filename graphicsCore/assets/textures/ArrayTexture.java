package assets.textures;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL42.*;

import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;

import assets.textures.utils.Image;
import assets.textures.utils.ImageLoader;


public class ArrayTexture extends Texture {
	
	private int width;	
	private int height;
	
	//The length of the array of textures stored
	private int numberOfLevels;
	
	//The number of mipmapLevels
	private int mipmapLevels;
	

	public ArrayTexture() {
		super(GL_TEXTURE_2D_ARRAY);
	}
	
	
	public ArrayTexture(String[] paths, int filterMode, int wrapMode) {
		super(GL_TEXTURE_2D_ARRAY);
		
		this.setImageData(paths, mipmapLevels, filterMode, wrapMode);
	}
	
	
	public void setImageData(String[] paths, int mipmapLevels, int filterMode, int wrapMode) {
		bind();
		
		//Every amount of mipmap levels below 1 isn't valid
		if (mipmapLevels < 1) {
			mipmapLevels = 1;
		}
		
		
		this.numberOfLevels = paths.length;	
		
		//The image that is currently loaded, checked and added to the array below
		Image currentImage = null;
		
		//The images to be stored in this texture array
		Image[] images = new Image[paths.length];
		
		
		for (int i = 0; i < paths.length; ++i) {
			
			currentImage = ImageLoader.loadImageRGBA(paths[i]);
			
			if (i == 0) {
				
				/*
				 * The width and height of each texture in this texture array
				 * has to have the same size as the first one.
				 */
				this.width = currentImage.getWidth();
				this.height = currentImage.getHeight();
				
			} else {
				
				//If the new image doesnt have the same size as the first one it will be replaced by a placeholder
				if (currentImage.getWidth() != width || currentImage.getHeight() != height) {
					System.err.println("The image " + i + " doesn't match the requested size" +
									  " thus it was replaced with a placeholder!");
					
					currentImage = ImageLoader.generatePlaceholderTexture(width, height);
				}
			}
		
			images[i] = currentImage;
						
		}
		
		//Create immutable texture storage space on the gpu 
		glTexStorage3D(getType(), mipmapLevels, GL_RGBA8, width, height, numberOfLevels);
		
		
		//Settings
		this.setFilter(filterMode);
		this.setTextureWrap(wrapMode);
		
		
		//Store all the images data in a single buffer 
		ByteBuffer texBuffer = BufferUtils.createByteBuffer(width * height + numberOfLevels);
		
		for (int i = 0; i < images.length; ++i) {
			texBuffer.put(images[i].getImageData());
		}
		
		texBuffer.flip();
		
		
		//Store the texture data on the gpu
		glTexSubImage3D(getType(), 0, 0, 0, 0, width, height, numberOfLevels, GL_RGBA, GL_UNSIGNED_BYTE, texBuffer);
		
		unbind();
	}
	
}
