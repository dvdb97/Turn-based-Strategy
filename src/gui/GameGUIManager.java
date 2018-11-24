package gui;

import container.GUIWindow;
import dataType.GUIElementMatrix;
import work_in_progress.test.FontWindow;

import static utils.ColorPalette.*;

public class GameGUIManager {
	
	private static GUIWindow window;
	
	public static void init() {
		
		window = new FontWindow(BLUE_1, new GUIElementMatrix(-0.5f, 0.5f, 0.5f, 0.5f));
		
	}
	
	public static void update() {
				
	}
	
}
