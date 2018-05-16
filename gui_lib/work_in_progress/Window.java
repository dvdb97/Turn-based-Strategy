package work_in_progress;

public abstract class Window extends Container {
	
	public Window() {
		
		//TODO:
		//GUIManager.addWindow(this);
		
	}
	
	public void update() {
		update(new GUIElementMatrix());
	}
	
}
