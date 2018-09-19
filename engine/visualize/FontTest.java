package visualize;

import assets.models.Element_Model;
import assets.textures.ArrayTexture2D;
import fontRendering.font.FontTexture;
import fontRendering.font.classic.TimesNewRoman;
import fontRendering.generation.TextGenerator;
import fontRendering.generation.functions.TempFunction;

public class FontTest {
	
	
	private static Element_Model model;
	
	private static FontTexture texture;
	
	
	public static void init(String text) {
		
		texture = new TimesNewRoman();
		
		//model = TextGenerator.generateTextModel(text, -0.8f, 0.0f, -1.0f, 0.1f, 1.6f, 0.1f, texture, new TempFunction());
		
	}
	
	
	public static FontTexture getTexture() {
		return texture;
	}
	
	
	public static Element_Model getModel() {
		return model;
	}

}
