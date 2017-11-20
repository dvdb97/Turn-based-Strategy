package assets.textures.utils;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class Image {
	
	private int width;
	
	private int height;
	
	private byte[] imageData;

	
	public Image(int width, int height, byte[] imageData) {
		super();
		this.width = width;
		this.height = height;
		this.imageData = imageData;
	}
	
	
	public ByteBuffer getImageDataAsByteBuffer() {
		ByteBuffer buffer = BufferUtils.createByteBuffer(height * width * 4);
		
		buffer.put(imageData);
		
		buffer.flip();
		
		return buffer;		
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public byte[] getImageData() {
		return imageData;
	}

}
