package stbFont;

import assets.textures.Texture2D;
import core.Application;
import dataType.GUIElementMatrix;
import fundamental.Element;
import rendering.shapes.GUIQuad;
import utils.CustomBufferUtils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;

public class TTFBox extends Element {
	
	private Texture2D font;
	private STBTTFontinfo fontInfo;
	private int[] width = new int[1], height = new int[1];
	private int[] ascent = new int[1], descent = new int[1], lineGap = new int[1];
	private float scale;
	private int pixelHeight;
	
	public TTFBox(float xShift, float yShift, float reqHeight, String text) {
		super(new GUIQuad(), null, new GUIElementMatrix(xShift, yShift, 1, reqHeight));
		
		setUpSTBFontinfo();
		
		try {
			initFont();
			
			scale = scaleForReqHeight(reqHeight);
			STBTruetype.stbtt_GetFontVMetrics(fontInfo, ascent, descent, lineGap);
			
			
			ByteBuffer[] bitmaps = new ByteBuffer[text.length()];
			for (int i=0; i<text.length(); i++) {
				bitmaps[i] = STBTruetype.stbtt_GetCodepointBitmap(fontInfo, scale, scale, text.charAt(i), width, height, null, null);
				bitmaps[i] = completeBitmap(bitmaps[i]);
			}
			
			
			
			ByteBuffer coloredBitmap = getColoredBitmap(bitmaps[0]);
			font = new Texture2D(coloredBitmap, width[0], pixelHeight);
			
			elementMatrix.setXStretch(reqHeight*width[0]/pixelHeight*Application.getWindowHeight()/Application.getWindowWidth());
			
		} catch (IOException ioe) {
			
		}
		
	}
	
	private ByteBuffer completeBitmap(ByteBuffer bitmap) {
		
		ArrayList<Byte> list = Utils.bufferToList(bitmap);
		int difference = pixelHeight - height[0];
	//	for (int w=0; w<width[0]; w++) {
	//		list.addAll(w*(pixelHeight), Utils.byteZeros(difference));
	//	}
		
		list.addAll(0, Utils.byteZeros(width[0]*difference));
		
		return CustomBufferUtils.createByteBuffer(list);
	}
	
	
	private void setUpSTBFontinfo() {
		ByteBuffer container = BufferUtils.createByteBuffer(160);
		fontInfo = new STBTTFontinfo(container);
	}
	
	private float scaleForReqHeight(float reqHeight) {
		pixelHeight = Utils.getHeightInPx(reqHeight);
		return STBTruetype.stbtt_ScaleForPixelHeight(fontInfo, pixelHeight);
	}

	private ByteBuffer getColoredBitmap(ByteBuffer bitmap) {
		ByteBuffer newBitmap = BufferUtils.createByteBuffer(bitmap.limit()*4);
		for (int i=0; i<newBitmap.capacity();) {
			newBitmap.put(bitmap.get());i++;
			newBitmap.put((byte)0);i++;
			newBitmap.put((byte)0);i++;
			newBitmap.put((byte)255);i++;
		}
		newBitmap.flip();
		return newBitmap;
	}
	
	
	private void initFont() throws IOException{
		
		DataInputStream data_in = new DataInputStream(
				new BufferedInputStream(
						new FileInputStream(
								new File("res/fonts/ARIALBD.TTF"))));
		ByteBuffer fontData = BufferUtils.createByteBuffer(1 << 20);
		
		while(true) {
			try {
				byte b = data_in.readByte();
				fontData.put(b);
				
			} catch (EOFException eof) {break;}
		}
		data_in.close();
		fontData.flip();
		STBTruetype.stbtt_InitFont(fontInfo, fontData, 0);
	}
	
	
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		
		TM = new GUIElementMatrix(elementMatrix.getXShift()*parentMatrix.getXStretch()+parentMatrix.getXShift(), elementMatrix.getYShift()*parentMatrix.getYStretch()+parentMatrix.getYShift(), elementMatrix.getXStretch(), elementMatrix.getYStretch());
		invertedTM = TM.getInverse();		
	}
	
	@Override
	public void render() {
		
		shape.render(font, null, TM);
		
	}
	
}
