package gui;

import container.GUIWindow;
import dataType.GUIElementMatrix;
import work_in_progress.test.FontWindow;

import static utils.ColorPalette.*;

public class GameGUIManager {
	
	private static GUIWindow window;
	
	public static void init() {
		
		window = new FontWindow(WHITE, new GUIElementMatrix());
		
	}
	
	public static void update() {
				
	}
	
}
