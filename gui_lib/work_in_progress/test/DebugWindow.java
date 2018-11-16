package work_in_progress.test;

import container.GUIWindow;
import dataType.GUIElementMatrix;
import interaction.input.CursorPosInput;
import rendering.shapes.GUIQuad;

import static utils.ColorPalette.*;

public class DebugWindow extends GUIWindow{

	public DebugWindow() {
		super(new GUIQuad(), RED, new GUIElementMatrix(CursorPosInput.getXPosAsOpenglCoord(), CursorPosInput.getYPosAsOpenglCoord(), 0.1f, 0.1f));
	}
	
	@Override
	public void update() {
		this.TM = new GUIElementMatrix(CursorPosInput.getXPosAsOpenglCoord(), CursorPosInput.getYPosAsOpenglCoord(), 0.1f, 0.1f);
	}
	
}
