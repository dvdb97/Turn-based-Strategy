package stbFont;

import core.Application;

public class Utils {
	
	public static int getWidthInPx(float width) {
		return (int)(width*Application.getWindowWidth()/2f);
	}
	
	public static int getHeightInPx(float height) {
		return (int)(height*Application.getWindowHeight()/2f);
	}

	
}
