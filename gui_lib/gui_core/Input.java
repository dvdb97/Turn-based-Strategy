package gui_core;

import interaction.input.CursorPosInput;
import interaction.input.MouseInputManager;

public class Input {
	
	public int cursorX, cursorY;
	public int dx, dy;
	public boolean leftMouseButton;
	public boolean rightMouseButton;
	
	public Input() {
		this.cursorX = (int)CursorPosInput.getXPos();
		this.cursorY = (int)CursorPosInput.getYPos();
		this.dx = this.dy = 0;
		this.leftMouseButton = MouseInputManager.isLeftMouseButtonPressed();
		this.rightMouseButton = MouseInputManager.isRightMouseButtonPressed();
	}
}
