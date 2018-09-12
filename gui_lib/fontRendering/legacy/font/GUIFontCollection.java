package fontRendering.legacy.font;

import java.util.HashMap;

import fontRendering.legacy.font.FontTexture;
import fontRendering.legacy.font.classic.TimesNewRoman;

public class GUIFontCollection {
	
	private static boolean initialized = false;
	
	private static HashMap<String, FontTexture> fontHashMap;
	
	public static void init() {
		
		if (initialized) {
			return;
		}
		
		fontHashMap = new HashMap<String, FontTexture>();
		
		fontHashMap.put("NewTimesRoman", new TimesNewRoman());
		
		initialized = true;
		
	}
	
	public static FontTexture getFont(String name) {
		
		if (!fontHashMap.containsKey(name)) {
			return fontHashMap.get("NewTimesRoman");
		}
		
		return fontHashMap.get(name);
	}
	
	public static void addFont(String name, FontTexture font) {
		fontHashMap.put(name, font);
	}

}
