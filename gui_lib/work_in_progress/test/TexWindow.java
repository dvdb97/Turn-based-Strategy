package work_in_progress.test;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import fundamental.GUIWindow;
import output.GUITexture;

public class TexWindow extends GUIWindow {
	
	public TexWindow() {
		super(new Color(0f, 0f, 0f, 1f), new GUIElementMatrix(0, 0, 0.6f, 0.2f));
		
		children.add(new GUITexture("res/PH_Grassland.png", new GUIElementMatrix(0.05f, -0.05f, 0.5f, 0.9f)));
	}
	
	
	
}
