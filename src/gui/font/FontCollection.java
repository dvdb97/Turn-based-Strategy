package gui.font;

import fontRendering.font.FontTexture;
import fontRendering.font.classic.TimesNewRoman;

public class FontCollection {
	
	private static FontTexture timesNewRoman;
	
	
	public static void init() {
		
		timesNewRoman = new TimesNewRoman();
		
	}
	
	
	public static FontTexture getTimesNewRoman() {
		return timesNewRoman;
	}
	
}
