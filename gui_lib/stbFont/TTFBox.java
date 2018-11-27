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
	
	public TTFBox(float xShift, float yShift, float reqHeight, String text, Color color) {
		super(new GUIQuad(), null, new GUIElementMatrix(xShift, yShift, 1, reqHeight));
		
		try {
			FontTextureGenerator ftg = new FontTextureGenerator("res/fonts/ARIALBD.TTF");
	//		FontTextureGenerator ftg = new FontTextureGenerator("res/fonts/FreeMono.ttf");
			font = ftg.getFontTexture(reqHeight, text, color);
			
			elementMatrix.setXStretch((float)2*font.getWidth() /Application.getWindowWidth());
			elementMatrix.setYStretch((float)2*font.getHeight()/Application.getWindowHeight());
			
		} catch (IOException e) {}
				
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
