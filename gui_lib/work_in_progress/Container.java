package work_in_progress;

public abstract class Container extends Element {
	
	protected ElementList children;
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		
		super.update(parentMatrix);
		children.update(parentMatrix);
		
	}
	
	@Override
	public void render() {
		
		super.render();
		children.render();
		

	}
	
	@Override
	public void processInput(float cursorX, float cursorY, boolean leftMouseButtonDown, boolean rightMouseButtonDown) {
		
		super.processInput(cursorX, cursorY, leftMouseButtonDown, rightMouseButtonDown);
		children.processInput(cursorX, cursorY, leftMouseButtonDown, rightMouseButtonDown);
		

	}
}
