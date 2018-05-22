package work_in_progress;

import interaction.input.CursorPosInput;
import interaction.input.MouseInputManager;
import math.vectors.Vector3f;

public class Mouse {
	
	/*
	 * TERMINOLOGY
	 * 
	 * not pressed:  xxxxxxxxxxxxx---------------------xxxxxxxxxxxxxxxxx
	 *                          click               release
	 * pressed:      -------------Xxxxxxxxxxxxxxxxxxxxx-----------------
	 * 
	 */
	
	
	//position
	private static Vector3f cursorPosition;
	
	//buttons
	private static boolean rightButtonPressed;
	private static boolean leftButtonPressed;
	
	private static boolean leftClicked;
	private static boolean leftReleased;

	private static boolean rightClicked;
	private static boolean rightReleased;
	
	
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
		if (MouseInputManager.isLeftMouseButtonPressed() != leftButtonPressed) {
			toggleLeft();
		} else {
			leftClicked = false;
			leftReleased = false;
		}
	}
	
	private static void updateRight() {
		if (MouseInputManager.isRightMouseButtonPressed() != rightButtonPressed) {
			toggleRight();
		} else {
			rightClicked = false;
			rightReleased = false;
		}
	}
	
	
	
	//********************************************************************
	
	private static void toggleLeft() {
		
		if(leftButtonPressed)
			leftReleased = true;
		else
			leftClicked = true;
		
		leftButtonPressed = !leftButtonPressed;
		
	}
	
	
	private static void toggleRight() {
		
		if(rightButtonPressed)
			rightReleased = true;
		else
			rightClicked = true;
		
		rightButtonPressed = !rightButtonPressed;
		
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
	
	
	public static boolean isRightButtonPressed() {
		return rightButtonPressed;
	}
	
	
	public static boolean isLeftButtonPressed() {
		return leftButtonPressed;
	}
	
	
	public static boolean isRightClicked() {
		return rightClicked;
	}
	
	
	public static boolean isRightReleased() {
		return rightReleased;
	}
	
	
	public static boolean isLeftClicked() {
		return leftClicked;
	}
	
	
	public static boolean isLeftReleased() {
		return leftReleased;
	}
	
	
}
