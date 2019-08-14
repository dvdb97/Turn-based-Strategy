package stbFont;

import assets.meshes.geometry.Color;
import assets.textures.Texture2D;
import core.Application1;
import dataType.GUIElementMatrix;
import fundamental.Element;
import rendering.shapes.implemented.GUIQuad;

import java.io.IOException;

public class TTFBox extends Element {
	
	private FontTextureGenerator ftg;
	private float reqHeight;
	private Texture2D font;
	
	public TTFBox(float xShift, float yShift, float reqHeight, String text, Color color) {
		super(new GUIQuad(), color, new GUIElementMatrix(xShift, yShift, 1, reqHeight));
		
		this.reqHeight = reqHeight;
		
		try {
	//		ftg = new FontTextureGenerator("res/fonts/ARIALBD.TTF");
			ftg = new FontTextureGenerator("res/fonts/FreeMono.ttf");
			font = ftg.getFontTexture(reqHeight, text, color);
			
			elementMatrix.setXStretch((float)2*font.getWidth() /Application1.getWindowWidth());
			elementMatrix.setYStretch((float)2*font.getHeight()/Application1.getWindowHeight());
			
		} catch (IOException e) {}
				
	}
	
	//TODO: constantly creating a new ftg is neither fast nor elegant, but not doing it causes a segmentation error... don't ask me why
	public void changeTextTo(String text) {
		try {
		 ftg = new FontTextureGenerator("res/fonts/FreeMono.ttf");
		} catch (IOException e) {}
		
		font = ftg.getFontTexture(reqHeight, text, color);
		
		elementMatrix.setXStretch((float)2*font.getWidth() /Application1.getWindowWidth());
		elementMatrix.setYStretch((float)2*font.getHeight()/Application1.getWindowHeight());
		
	}
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		
		TM = new GUIElementMatrix(elementMatrix.getXShift()*parentMatrix.getXStretch()+parentMatrix.getXShift(), elementMatrix.getYShift()*parentMatrix.getYStretch()+parentMatrix.getYShift(), elementMatrix.getXStretch(), elementMatrix.getYStretch());
		invertedTM = TM.getInverse();		
	}
	
	@Override
	public void render() {
		shape.render(font);
	}
	
}
