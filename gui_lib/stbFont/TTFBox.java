package stbFont;

import assets.textures.Texture2D;
import core.Application;
import dataType.GUIElementMatrix;
import fundamental.Element;
import rendering.shapes.GUIQuad;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;

public class TTFBox extends Element {
	
	private Texture2D font;
	
	public TTFBox(float xShift, float yShift, float reqHeight) {
		super(new GUIQuad(), null, new GUIElementMatrix(xShift, yShift, 1, reqHeight));
		
		ByteBuffer container = BufferUtils.createByteBuffer(160);
		STBTTFontinfo fontInfo = new STBTTFontinfo(container);
		int[] width = new int[1], height = new int[1];
		
		try {
			ByteBuffer data = getFontData();
			STBTruetype.stbtt_InitFont(fontInfo, data, 0);
			
			float pixelHeight = Utils.getHeightInPx(reqHeight);
			float scale = STBTruetype.stbtt_ScaleForPixelHeight(fontInfo, pixelHeight);
			
			ByteBuffer bitmap = STBTruetype.stbtt_GetCodepointBitmap(fontInfo, scale, scale, 'f', width, height, null, null);
			ByteBuffer newBitmap = getNewBitmap(bitmap);
			font = new Texture2D(newBitmap, width[0], height[0]);
			
			elementMatrix.setXStretch(reqHeight*width[0]/height[0]*Application.getWindowHeight()/Application.getWindowWidth());
			
		} catch (IOException ioe) {
			
		}
		
	}
	
	private ByteBuffer getNewBitmap(ByteBuffer bitmap) {
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
	
	
	private ByteBuffer getFontData() throws IOException{
		
		DataInputStream data_in = new DataInputStream(
				new BufferedInputStream(
						new FileInputStream(
								new File("res/fonts/ARIALBD.TTF"))));
		ByteBuffer data_out = BufferUtils.createByteBuffer(1 << 20);
		
		while(true) {
			try {
				byte b = data_in.readByte();
				data_out.put(b);
				
			} catch (EOFException eof) {break;}
		}
		data_in.close();
		data_out.flip();
		return data_out;
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
