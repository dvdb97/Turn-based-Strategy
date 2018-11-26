package stbFont;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import utils.CustomBufferUtils;

public class Bitmap extends ArrayList<Byte>{
	
	private int width;
	private int height;
	
	public Bitmap(ByteBuffer byteBuffer, int width, int height) {
		super(byteBuffer.limit());
		for (int i=0; i<byteBuffer.limit(); i++) {
			add(byteBuffer.get(i));
		}
		
		this.width = width;
		this.height = height;
		
	}
	
	public void addGlyph(Bitmap bitmap) {
		
		if (this.height != bitmap.height) {
			throw new IllegalArgumentException("the heights of the bitmaps do not match: " + this.height + ", " + bitmap.height);
		}
		
		for (int row=0; row<height; row++) {
			addAll(row*(this.width+bitmap.width)+this.width, bitmap.subList(row*bitmap.width, (row+1)*bitmap.width));
		}
		width += bitmap.width;
	}
	
	public void completeBitmap(int pixelHeight) {
		addAll(0, Utils.byteZeros(width*(pixelHeight - height)));
		height = pixelHeight;
	}

	public ByteBuffer getColoredBufferBitmap() {
		
		ByteBuffer bufferBitmap = BufferUtils.createByteBuffer(size()*4);
		for (int i=0; i<bufferBitmap.capacity();) {
			bufferBitmap.put(this.get(i/4));i++;
			bufferBitmap.put((byte)0);i++;
			bufferBitmap.put((byte)0);i++;
			bufferBitmap.put((byte)255);i++;
		}
		bufferBitmap.flip();
		return bufferBitmap;
	}

	
	
	public ByteBuffer getByteBuffer() {
		return CustomBufferUtils.createByteBuffer(this);
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	
}
