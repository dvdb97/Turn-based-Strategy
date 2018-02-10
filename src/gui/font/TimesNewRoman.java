package gui.font;

<<<<<<< HEAD:gui_lib/fontRendering/font/classic/TimesNewRoman.java
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import fontRendering.font.FontTexture;
=======
import fontRendering.texture.FontTexture;
>>>>>>> parent of d0e5031... labeled elements:src/gui/font/TimesNewRoman.java

public class TimesNewRoman extends FontTexture {

	public TimesNewRoman() {
		super("res/fonts/Font.png", 1000, 1000, 10, 10);
		// TODO Auto-generated constructor stub
		
		char[] correspondingChars = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', '�', '�', '�', '�',
			'�', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '�', '�', '�',
			':', '!', '?', ';', '+', '*', '/', '-', ',', '=',
			'_', '"', '%', '&', '(', ')', '.'
		};
		
		this.setChars(correspondingChars);
		
	}

}
