package assets.textures.utils;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import utils.CustomBufferUtils;

public class Image {
	
	private int width;
	
	private int height;
	
	private ByteBuffer imageData;

	
	public Image(int width, int height, byte[] imageData) {
		this(width, height, CustomBufferUtils.createByteBuffer(imageData));
	}
	
	
	public Image(int width, int height, ByteBuffer imageData) {
		this.width = width;
		this.height = height;
		this.imageData = imageData;
	}
	
	
	public ByteBuffer getImageDataAsByteBuffer() {		
		return imageData;		
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}
}
