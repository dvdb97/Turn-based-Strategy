package stbFont;

import assets.meshes.geometry.Color;
import assets.textures.Texture2D;
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
	
	
	public TTFBox(GUIElementMatrix transformationMatrix) {
		super(new GUIQuad(), null, transformationMatrix);
		
		ByteBuffer container = BufferUtils.createByteBuffer(160);
		STBTTFontinfo fontInfo = new STBTTFontinfo(container);
		int[] width = new int[1], height = new int[1];
		
		try {
			ByteBuffer data = getFontData();
			STBTruetype.stbtt_InitFont(fontInfo, data, 0);
			
			ByteBuffer bitmap = STBTruetype.stbtt_GetCodepointBitmap(fontInfo, 1, 1, 65, width, height, null, null);
			font = new Texture2D(bitmap, width[0], height[0]);
		} catch (IOException ioe) {
			
		}
		
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
				System.out.println(b);
				data_out.put(b);
				
			} catch (EOFException eof) {break;}
		}
		data_in.close();
		return data_out;
	}
	
	
	
	
	
	@Override
	public void render() {
		
		shape.render(font, null, TM);
		
	}
	
}
