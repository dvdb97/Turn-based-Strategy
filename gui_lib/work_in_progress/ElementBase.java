package work_in_progress;

public interface ElementBase {
	
	void update(GUIElementMatrix parentMatrix);
	
	void render();
	
	boolean processInput();
	
}
