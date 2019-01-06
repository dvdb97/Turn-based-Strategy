package fundamental;

import dataType.GUIElementMatrix;

public interface ElementBase {
	
	void update(GUIElementMatrix parentMatrix);
	
	void render();
	
	boolean processInput();
	
}
