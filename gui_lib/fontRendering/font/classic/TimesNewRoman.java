package fontRendering.font.classic;

import fontRendering.font.FontTexture;

public class TimesNewRoman extends FontTexture {

	public TimesNewRoman() {
<<<<<<< HEAD
<<<<<<< HEAD:src/gui/font/TimesNewRoman.java
		super("res/fonts/Font.png", 10, 10);
=======
		super("res/fonts/Font.png", 1000, 1000, 10, 10);
		// TODO Auto-generated constructor stub
>>>>>>> master:gui_lib/fontRendering/font/classic/TimesNewRoman.java
		
		char[] correspondingChars = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', 'Ä', 'Ö', 'Ü', 'ß',
			'Ñ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', 'ä', 'ö', 'ü',
			':', '!', '?', ';', '+', '*', '/', '-', ',', '=',
			'_', '"', '%', '&', '(', ')', '.'
		};
		
		this.setChars(correspondingChars);
		
=======
		super("res/fonts/TimesNewRoman.png", 1024, 1024);		
>>>>>>> gui_changes
	}

}
