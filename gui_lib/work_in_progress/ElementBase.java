package work_in_progress;

public interface ElementBase {
	
	void update(GUIElementMatrix pratentMatrix);
	
	void render();
	
	void processInput(float cursorX, float cursorY, boolean leftMouseButtonDown, boolean rightMouseButtonDown);
	
}
