package fundamental;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import gui_core.GUIManager;
import input.Mouse;

public abstract class GUIWindow extends Container implements Clickable {
	//TODO: element matrix vs transformation matrix
	public GUIWindow(Color color, GUIElementMatrix elementMatrix) {
		
		super(color, elementMatrix);
		
		GUIManager.addWindow(this);
		
	}
	
	public void update() {
		update(new GUIElementMatrix());
	}
	
	@Override
	public boolean processInput() {
		
		boolean b = super.processInput();
		if (b && Mouse.isLeftClicked()) {
			onClick();
		}
		
		return b;
	}
	
	public void onClick() {
		GUIManager.setPrimaryWindow(this);
	}
	
	public void onRelease() {
		//do nothing
	}
}
