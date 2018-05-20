package work_in_progress;

import interaction.input.CursorPosInput;
import interaction.input.MouseInputManager;

public class MouseConcept {
	
	/*
	 * ***************************************
	 * mouse input is not trivial.
	 * maybe a GUIButton should be pressed if you press the mouse button, not when the mouse button is down.
	 * otherwise: if you press and hold the mouse button down and move the cursor over many buttons, every button would be clicked
	 * 
	 * problem:
	 * when you update the boolean leftClicked continously (very often), the gui would ignore a mouse click happening on this GUIElement while the gui's attention
	 * is on another GUIElement
	 * 
	 * when you update the boolean first and than do all the processInput() stuff, a ...
	 * 
	 * wait!
	 * ****************************************
	 * 
	 * ****************************************
	 * 
	 * diese klasse speichert, ob mousebutton pressed ist oder nicht, und ob booleans click and release
	 * A) GUIManager ruft zuerst die methode update() dieser klasse auf: Wenn der neue zustand eines mouse buttons anders ist als der zuletzt gespeicherte wird der entsprechende
	 * boolean (click oder release) auf true gesetzt. Bleibt der zustand gleich, auf false.
	 * B) GUIManager iteriert durch alle GUIElement. Diese checken dann, ob die maus auf ihm drauf ist und ob diese geklickt wurde (oder released, etc)
	 * Dann render(), update(), dann wieder A dann B, usw...
	 * 
	 * so wird jeder klick erfasst.
	 * es sei denn der user klickt und released wieder während der dauer eines game-loop-durchlaufs.
	 * Das ist nicht möglich da diese dauer sehr kurz ist (deshalb haben wir  auch cooldown und so eingefügt
	 */
	
	
	//position
	private float cursorX = CursorPosInput.getXPosAsOpengl();
	private float cursorY = CursorPosInput.getYPosAsOpengl();
	
	//buttons
	private boolean rightButtonDown;
	private boolean leftButtonDown;
	
	private boolean rightClicked;
	private boolean rightReleased;
	
	private boolean leftClicked;
	private boolean leftReleased;
	
	
	//****************************** update ******************************
	
	public void update() {
		
		cursorX = CursorPosInput.getXPosAsOpengl();
		cursorY = CursorPosInput.getYPosAsOpengl();
		
		boolean newRightButtonDown = MouseInputManager.isRightMouseButtonPressed();
		boolean newLeftButtonDown  = MouseInputManager.isLeftMouseButtonPressed();
		
		if (!rightButtonDown && newRightButtonDown)
			rightClicked = true;
		
		
		//usw...
		
	}
	
	//****************************** get *********************************
	
	public float getCursorX() {
		return cursorX;
	}
	
	public float getCursorY() {
		return cursorY;
	}

	
	/**
	 * @return the rightButtonDown
	 */
	public boolean isRightButtonDown() {
		return rightButtonDown;
	}
	/**
	 * @return the leftButtonDown
	 */
	public boolean isLeftButtonDown() {
		return leftButtonDown;
	}
	/**
	 * @return the richtClick
	 */
	public boolean isRichtClicked() {
		return rightClicked;
	}
	/**
	 * @return the rightRelease
	 */
	public boolean isRightReleased() {
		return rightReleased;
	}
	/**
	 * @return the leftClick
	 */
	public boolean isLeftClicked() {
		return leftClicked;
	}
	/**
	 * @return the leftRelease
	 */
	public boolean isLeftReleased() {
		return leftReleased;
	}
	
	
}
