package stbFont;

import assets.meshes.geometry.Color;
import assets.textures.Texture;
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
			System.out.println("Font initialized? " + STBTruetype.stbtt_InitFont(fontInfo, data, 0));
			
			ByteBuffer bitmap = STBTruetype.stbtt_GetCodepointBitmap(fontInfo, 1, 1, 65, width, height, null, null);
			System.out.println(width[0] + " x " + height[0]);
			System.out.println("pos: "+bitmap.position()+" limit: "+bitmap.limit() );
			
			ByteBuffer newBitmap = BufferUtils.createByteBuffer(bitmap.limit()*4);
			for (int i=0; i<newBitmap.capacity();) {
				newBitmap.put(bitmap.get());i++;
				newBitmap.put((byte)200);i++;
				newBitmap.put((byte)0);i++;
				newBitmap.put((byte)255);i++;
			}
			newBitmap.flip();
	//		font = new Texture2D(newBitmap, width[0], height[0]);
			font = new Texture2D("res/temp.png", 100, 100, Texture.LINEAR, Texture.NEAREST);
			
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
				data_out.put(b);
				
			} catch (EOFException eof) {break;}
		}
		data_in.close();
		data_out.flip();
		return data_out;
	}
	
	
	
	
	
	@Override
	public void render() {
		
		shape.render(font, null, TM);
		
	}
	
}
