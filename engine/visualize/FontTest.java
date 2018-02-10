package visualize;

import assets.models.Element_Model;
<<<<<<< HEAD
import assets.textures.ArrayTexture2D;
import fontRendering.font.classic.TimesNewRoman;
import fontRendering.font.FontTexture;
=======
import assets.textures.Texture2D;
>>>>>>> parent of d0e5031... labeled elements
import fontRendering.generation.TextGenerator;
import fontRendering.generation.functions.TempFunction;
import fontRendering.texture.FontTexture;
import gui.font.TimesNewRoman;

public class FontTest {
	
	
	private static Element_Model model;
	
	private static FontTexture texture;
	
	
	public static void init(String text) {
		
		texture = new TimesNewRoman();
		
<<<<<<< HEAD
		model = TextGenerator.generateTextModel(text, -0.8f, 0.0f, -1.0f, 0.1f, 1.6f, 0.1f, texture, new TempFunction());
=======
		model = TextGenerator.generateTextModel(text, -0.8f, 0.0f, 0.0f, 1.6f, 0.1f, texture, new TempFunction());
>>>>>>> parent of d0e5031... labeled elements
		
	}
	
	
	public static FontTexture getTexture() {
		return texture;
	}
	
	
	public static Element_Model getModel() {
		return model;
	}

}
