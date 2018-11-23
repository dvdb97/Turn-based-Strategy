package stbFont;

import assets.textures.Texture2D;
import dataType.GUIElementMatrix;
import fundamental.Element;
import rendering.shapes.GUIQuad;

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
		
		ByteBuffer bitmap = STBTruetype.stbtt_GetCodepointBitmap(fontInfo, 1, 1, 'A', width, height, null, null);
		
		font = new Texture2D(bitmap, width[0], height[0]);
		
	}
	
	
	@Override
	public void render() {
		
		shape.render(font, null, TM);
		
	}
	
}
