package gui.font;

<<<<<<< HEAD
import fontRendering.font.classic.TimesNewRoman;
import fontRendering.font.FontTexture;
=======
import fontRendering.texture.FontTexture;
>>>>>>> parent of d0e5031... labeled elements

public class FontCollection {
	
	private static FontTexture timesNewRoman;
	
	
	public static void init() {
		
		timesNewRoman = new TimesNewRoman();
		
	}
	
	
	public static FontTexture getTimesNewRoman() {
		return timesNewRoman;
	}
	
}
