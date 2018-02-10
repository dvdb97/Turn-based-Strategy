package gui.font;

<<<<<<< HEAD

=======
import fontRendering.font.classic.TimesNewRoman;
>>>>>>> parent of 33645b9... Revert "labeled elements"
import fontRendering.font.FontTexture;

public class FontCollection {
	
	private static FontTexture timesNewRoman;
	
	
	public static void init() {
		
		timesNewRoman = new TimesNewRoman();
		
	}
	
	
	public static FontTexture getTimesNewRoman() {
		return timesNewRoman;
	}
	
}
