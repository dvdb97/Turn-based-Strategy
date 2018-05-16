package work_in_progress;

public abstract class Container extends Element {
	
	protected ElementList children;
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		
		super.update(parentMatrix);
		
		
	}
	
	@Override
	public void render() {
		
	}
	
	@Override
	public void processInput(float cursorX, float cursorY, boolean leftMouseButtonDown, boolean rightMouseButtonDown) {
		
	}
}
