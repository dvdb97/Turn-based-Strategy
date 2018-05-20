package work_in_progress;

import interaction.input.CursorPosInput;
import interaction.input.MouseInputManager;
import math.vectors.Vector3f;

public class Mouse {
	
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
	private static Vector3f cursorPosition;
	
	//buttons
	private static boolean rightButtonDown;
	private static boolean leftButtonDown;
	
	private static boolean rightPressed;
	private static boolean rightReleased;
	
	private static boolean leftPressed;
	private static boolean leftReleased;
	
	//****************************** init ********************************
	
	public static void init() {
		
		cursorPosition = new Vector3f(0, 0, 1);
		
	}
	
	//****************************** update ******************************
	
	public static void update() {
		
		updateCursorPosition();
		
		updateLeft();
		updateRight();
		
	}
	
	private static void updateCursorPosition() {
		cursorPosition.setA(CursorPosInput.getXPosAsOpengl());
		cursorPosition.setB(CursorPosInput.getYPosAsOpengl());
	}

	private static void updateLeft() {
		if (MouseInputManager.isLeftMouseButtonPressed() != leftButtonDown)
			toggleLeft();
		else
			leftPressed = false;
			leftReleased = false;
	}
	
	private static void updateRight() {
		if (MouseInputManager.isRightMouseButtonPressed() != rightButtonDown)
			toggleRight();
		else
			rightPressed = false;
			rightReleased = false;
	}
	
	
	
	//********************************************************************
	
	private static void toggleRight() {
		
		if(rightButtonDown)
			rightReleased = true;
		else
			rightPressed = true;
		
		rightButtonDown = !rightButtonDown;
		
	}
	
	private static void toggleLeft() {
		
		if(leftButtonDown)
			leftReleased = true;
		else
			leftPressed = true;
		
		leftButtonDown = !leftButtonDown;
		
	}

	
	//****************************** get *********************************
	
	public static float getCursorX() {
		return cursorPosition.getA();
	}
	
	public static float getCursorY() {
		return cursorPosition.getB();
	}
	
	public static Vector3f getCursorPosititon() {
		return cursorPosition.copyOf();
	}
	
	/**
	 * @return the rightButtonDown
	 */
	public static boolean isRightButtonDown() {
		return rightButtonDown;
	}
	/**
	 * @return the leftButtonDown
	 */
	public static boolean isLeftButtonDown() {
		return leftButtonDown;
	}
	/**
	 * @return the richtClick
	 */
	public static boolean isRichtPressed() {
		return rightPressed;
	}
	/**
	 * @return the rightRelease
	 */
	public static boolean isRightReleased() {
		return rightReleased;
	}
	/**
	 * @return the leftClick
	 */
	public static boolean isLeftPressed() {
		return leftPressed;
	}
	/**
	 * @return the leftRelease
	 */
	public static boolean isLeftReleased() {
		return leftReleased;
	}
	
	
}
