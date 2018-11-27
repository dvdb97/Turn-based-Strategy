package stbFont;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import assets.meshes.geometry.Color;
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
	 * creates a bitmap full of zeros
	 */
	public Bitmap(int width, int height) {
		super(width*height);
		for (int i=0; i<width*height; i++) {
			add((byte)0);
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
	
	public void addLine(Bitmap bitmap) {
		if(bitmap == this) {
			return;
		}
		
		if (this.width < bitmap.width) {
			this.addGlyph(new Bitmap(bitmap.width - width, height));
		}
		if (this.width > bitmap.width) {
			bitmap.addGlyph(new Bitmap(width - bitmap.width, bitmap.height));
		}
		
		addAll(bitmap);
		height += bitmap.height;
	}
	
	
	/**
	 * fills up the bitmap with zeros, such that the new height is equal to "pixelHeight"
	 * @param pixelHeight height of the new bitmap in pixels
	 */
	public void fillupV(int pixelHeight, int spaceAbove) {
		addAll(0, Utils.byteZeros(width*spaceAbove));
		if (pixelHeight-height-spaceAbove >= 0) {
			addAll((height+spaceAbove)*width, Utils.byteZeros((pixelHeight-height-spaceAbove)*width));
		}
		height = pixelHeight;
	}
	
	public void fillupH(int spaceLeftsided) {
		Bitmap b = new Bitmap(spaceLeftsided, height);
		b.size();
		b.addGlyph(this);
		clear();
		addAll(b);
		this.width += spaceLeftsided;
	}
	
	/**
	 * creates a ByteBuffer, with RGBA for every pixel
	 * @return a ByteBuffer-bitmap
	 */
	public ByteBuffer getColoredBufferBitmap(Color color) {
		
		ByteBuffer bufferBitmap = BufferUtils.createByteBuffer(size()*4);
		for (int i=0; i<bufferBitmap.capacity();) {
			bufferBitmap.put((byte)color.getRedInt());i++;
			bufferBitmap.put((byte)color.getGreenInt());i++;
			bufferBitmap.put((byte)color.getBlueInt());i++;
			bufferBitmap.put(this.get(i/4));i++;
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
