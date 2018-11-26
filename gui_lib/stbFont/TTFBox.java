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
			
			Bitmap[] bitmaps = new Bitmap[text.length()];
			for (int i=0; i<text.length(); i++) {
				if (text.charAt(i) == ' ') {
					bitmaps[i] = new Bitmap(pixelHeight/4, pixelHeight);
				} else {
					ByteBuffer b = STBTruetype.stbtt_GetCodepointBitmap(fontInfo, scale, scale, text.charAt(i), width, height, null, null);
					bitmaps[i] = new Bitmap(b, width[0], height[0]);
					int[] x0 = new int[1], x1 = new int[1], y0 = new int[1], y1 = new int[1];
					STBTruetype.stbtt_GetCodepointBox(fontInfo, text.charAt(i), x0, y0, x1, y1);
					int spaceAbove = (int)((ascent[0] - y1[0])*scale);
					bitmaps[i].fillupV(pixelHeight, spaceAbove);
					bitmaps[i].fillupH((int)(x0[0]*scale));
				}
				bitmaps[0].addGlyph(bitmaps[i]);
			}
			
			int[] ix0 = new int[1], ix1 = new int[1];
			STBTruetype.stbtt_GetCodepointBitmapBox(fontInfo, ' ', scale, scale, ix0, null, ix1, null);
			
			ByteBuffer coloredBitmap = bitmaps[0].getColoredBufferBitmap();
			
			font = new Texture2D(coloredBitmap, bitmaps[0].getWidth(), pixelHeight);
			
			elementMatrix.setXStretch(reqHeight*(bitmaps[0].getWidth())/pixelHeight*Application.getWindowHeight()/Application.getWindowWidth());
			
		} catch (IOException ioe) {
			
		}
		
	}	
	
	private void setUpSTBFontinfo() {
		ByteBuffer container = BufferUtils.createByteBuffer(160);
		fontInfo = new STBTTFontinfo(container);
	}
	
	private float scaleForReqHeight(float reqHeight) {
		pixelHeight = Utils.getHeightInPx(reqHeight);
		return STBTruetype.stbtt_ScaleForPixelHeight(fontInfo, pixelHeight);
	}	
	
	private void initFont() throws IOException{
		
		DataInputStream data_in = new DataInputStream(
				new BufferedInputStream(
						new FileInputStream(
								new File("res/fonts/ARIALBD.TTF"))));
		//						new File("res/fonts/FreeMono.ttf"))));
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
