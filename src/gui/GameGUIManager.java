package gui;

import container.GUIWindow;
import dataType.GUIElementMatrix;
import work_in_progress.test.ColorPickWindow;
import work_in_progress.test.FontWindow;

import static utils.ColorPalette.*;

public class GameGUIManager {
	
	private static GUIWindow window;
	
	public static void init() {
		
		window = new ColorPickWindow();
		//window = new FontWindow(RED, new GUIElementMatrix());
		
	}
	
	public static void update() {
				
	}
	
}
