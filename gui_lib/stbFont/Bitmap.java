package stbFont;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import utils.CustomBufferUtils;

public class Bitmap extends ArrayList<Byte>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7104940190213034292L;
	
	private int width;
	private int height;
	
	/**
	 * creates Bitmap.
	 * All needed parameters are returned by {@link org.lwjgl.stb.STBTruetype#stbtt_GetCodepointBitmap stbtt_GetCodepointBitmap}.
	 * 
	 * @param byteBuffer the bitmap, but as a ByteBuffer
	 * @param width width in pixels
	 * @param height height in pixels
	 */
	public Bitmap(ByteBuffer byteBuffer, int width, int height) {
		super(byteBuffer.limit());
		for (int i=0; i<byteBuffer.limit(); i++) {
			add(byteBuffer.get(i));
		}
		
		this.width = width;
		this.height = height;
		
	}
	
	/**
	 * combines the bitmaps, such that the added glyph is next to this glyph, right handed
	 * @param bitmap bitmap of the glyph you want to add
	 */
	public void addGlyph(Bitmap bitmap) {
		
		if(bitmap == this) {
			return;
		}
		
		if (this.height != bitmap.height) {
			throw new IllegalArgumentException("the heights of the bitmaps do not match: " + this.height + ", " + bitmap.height);
		}
		
		for (int row=0; row<height; row++) {
			addAll(row*(this.width+bitmap.width)+this.width, bitmap.subList(row*bitmap.width, (row+1)*bitmap.width));
		}
		width += bitmap.width;
	}
	
	/**
	 * fills up the bitmap with zeros, such that the new height is equal to "pixelHeight"
	 * @param pixelHeight height of the new bitmap in pixels
	 */
	public void fillupBitmap(int pixelHeight) {
		addAll(0, Utils.byteZeros(width*(pixelHeight - height)));
		height = pixelHeight;
	}
	
	/**
	 * creates a ByteBuffer, with RGBA for every pixel
	 * @return a ByteBuffer-bitmap
	 */
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

	
	/**
	 * 
	 * @return a ByteBuffer representing this bitmap
	 */
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
