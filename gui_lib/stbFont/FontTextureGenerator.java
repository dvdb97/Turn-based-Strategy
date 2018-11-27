package stbFont;

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

import assets.meshes.geometry.Color;
import assets.textures.Texture2D;

public class FontTextureGenerator {
	
	private STBTTFontinfo fontInfo;
	
	private int[] width = new int[1], height = new int[1];
	private int[] ascent = new int[1], descent = new int[1], lineGap = new int[1];
	private float scale;
	private int pixelHeight;
	
	public FontTextureGenerator(String ttfFile) throws IOException {
		
		setUpSTBFontinfo();
		try {
			initFont(ttfFile);
		} catch (IOException ioe) {
			throw ioe;
		}
		
	}
	
	//---------------------------------------- tier 1 ----------------------------------------
	
	public Texture2D getFontTexture(float reqHeight, String text, Color color) {
		
		ArrayList<String> lineStrings = Utils.divideInLines(text);
		
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
		
		return new Texture2D(coloredBitmap, bitmap.getWidth(), bitmap.getHeight());
	}
	
	//------------------------------- init --------------------------------------------------
	
	private void setUpSTBFontinfo() {
		ByteBuffer container = BufferUtils.createByteBuffer(160);
		fontInfo = new STBTTFontinfo(container);
	}
	
	private void initFont(String ttfFile) throws IOException{
		
		DataInputStream data_in = new DataInputStream(
				new BufferedInputStream(
						new FileInputStream(
								new File(ttfFile))));
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
	
	//-------------------------------- tier 2 --------------------------------------------------
	
	private float scaleForReqHeight(float reqHeight) {
		pixelHeight = Utils.getHeightInPx(reqHeight);
		return STBTruetype.stbtt_ScaleForPixelHeight(fontInfo, pixelHeight);
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
	
	//-------------------------------- tier 3 --------------------------------------------------
	
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
	
}
