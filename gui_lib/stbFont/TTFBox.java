package stbFont;

import assets.meshes.geometry.Color;
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
	
	public TTFBox(float xShift, float yShift, float reqHeight, String text, Color color) {
		super(new GUIQuad(), null, new GUIElementMatrix(xShift, yShift, 1, reqHeight));
		
		try {
			FontTextureGenerator ftg = new FontTextureGenerator("res/fonts/ARIALBD.TTF");
	//		FontTextureGenerator ftg = new FontTextureGenerator("res/fonts/FreeMono.ttf");
			font = ftg.getFontTexture(reqHeight, text, color);
			
			int totalHeightPx  = (pixelHeight+lineGap[0])*lineStrings.size()-lineGap[0];
			float totalHeight  = (float)totalHeightPx/Application.getWindowHeight()*2;
			
			elementMatrix.setXStretch(totalHeight*bitmap.getWidth()/bitmap.getHeight()*Application.getWindowHeight()/Application.getWindowWidth());
			elementMatrix.setYStretch(totalHeight);
			
		} catch (IOException e) {}
		
		
		
		
		setUpSTBFontinfo();
		
		ArrayList<String> lineStrings = prepare(text);
		
		try {
			initFont();
			
			scale = scaleForReqHeight(reqHeight);
			STBTruetype.stbtt_GetFontVMetrics(fontInfo, ascent, descent, lineGap);
			lineGap[0] *= scale;
			
			Bitmap bitmap = getLineBitmap(lineStrings.get(0));
			for (int l=1; l<lineStrings.size(); l++) {
				bitmap.addLine(getLineBitmap(lineStrings.get(l)), (int)(lineGap[0]));
			}
			
			int[] ix0 = new int[1], ix1 = new int[1];
			STBTruetype.stbtt_GetCodepointBitmapBox(fontInfo, ' ', scale, scale, ix0, null, ix1, null);
			
			ByteBuffer coloredBitmap = bitmap.getColoredBufferBitmap(color);
			
			font = new Texture2D(coloredBitmap, bitmap.getWidth(), bitmap.getHeight());
			
			int totalHeightPx  = (pixelHeight+lineGap[0])*lineStrings.size()-lineGap[0];
			float totalHeight  = (float)totalHeightPx/Application.getWindowHeight()*2;
			
			elementMatrix.setXStretch(totalHeight*bitmap.getWidth()/bitmap.getHeight()*Application.getWindowHeight()/Application.getWindowWidth());
			elementMatrix.setYStretch(totalHeight);
			
		} catch (IOException ioe) {
			
		}
		
	}
	
	private ArrayList<String> prepare(String text) {
		ArrayList<String> strings = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (int c=0; c<text.length(); c++) {
			if(text.charAt(c) ==  '\n') {
				strings.add(sb.toString());
				sb = new StringBuilder();
			} else {
				sb.append(text.charAt(c));
			}
		}
		if (sb.length() > 0) {
			strings.add(sb.toString());
		}
		return strings;
	}
	
	private Bitmap getLineBitmap(String text) {
		Bitmap bitmap = getCodepointBitmap(text.charAt(0));
		Bitmap bufBitmap;
		for (int i=1; i<text.length(); i++) {
			bufBitmap = getCodepointBitmap(text.charAt(i));
			bitmap.addGlyph(bufBitmap);
		}
		return bitmap;
	}

	private Bitmap getCodepointBitmap(char c) {
		if (c == ' ') {
			return new Bitmap(pixelHeight/4, pixelHeight);
		}
		ByteBuffer b = STBTruetype.stbtt_GetCodepointBitmap(fontInfo, scale, scale, c, width, height, null, null);
		Bitmap bitmap = new Bitmap(b, width[0], height[0]);
		int[] x0 = new int[1], x1 = new int[1], y0 = new int[1], y1 = new int[1];
		STBTruetype.stbtt_GetCodepointBox(fontInfo, c, x0, y0, x1, y1);
		int spaceAbove = (int)((ascent[0] - y1[0])*scale);
		bitmap.fillupV(pixelHeight, spaceAbove);
		bitmap.fillupH((int)(x0[0]*scale));
		return bitmap;
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
