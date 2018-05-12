package work_in_progress;

import java.util.ArrayList;

import graphics.matrices.TransformationMatrix;

public class ElementList extends ArrayList<ElementBase> implements ElementBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1285077944138307000L;

	@Override
	public void update(GUIElementMatrix parentMatrix) {
		
		forEach((e) -> e.update(parentMatrix));
		
	}

	@Override
	public void render() {
		
		forEach((e) -> e.render());
		
	}

	@Override
	public void processInput(float cursorX, float cursorY, boolean leftMouseButtonDown, boolean rightMouseButtonDown) {
		
		forEach((e) -> e.processInput(cursorX, cursorY, leftMouseButtonDown, rightMouseButtonDown));
		
	}

}
