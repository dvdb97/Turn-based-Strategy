package work_in_progress;

public abstract class Element implements ElementBase {
	
	protected GUIElementMatrix transformationMatrix;
	protected GUIElementMatrix invertedTM;	
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub

	}

	@Override
	public void processInput(float cursorX, float cursorY, boolean leftMouseButtonDown, boolean rightMouseButtonDown) {
		// TODO Auto-generated method stub

	}

}
