package package1;

import utils.Const;

public class Input {
	
	public static void refreshInput(long window) {
		
	}
	
	public static int mousePosX;
	public static int mousePosY;
	
	public static boolean mouseRightClick;
	public static boolean mouseLeftClick;
	
	public static boolean leftDownShiftRequested;
	
	//menus
	public static int menuSelect;
	
	public static void resetMenuSelect() {
		menuSelect = Const.NO_MENU_SELECT;
	}
	
	//map movement
	public static boolean mouseAtLeftEdge, mouseAtRightEdge, mouseAtUpperEdge, mouseAtLowerEdge;
	public static boolean wPressed, aPressed, sPressed, dPressed;
	public static boolean rPressed, fPressed, tPressed, gPressed;
	
	int mouseAction;
	
	//hot keys
	public static boolean mPressed;
	public static boolean cPressed;
	public static boolean vPressed;
	public static boolean bPressed;
	
	
	//arrows
	public static boolean leftArrow, rightArrow, upArrow, downArrow;
	
	public static void resetMouseBooleans() {
		
		mouseAtLeftEdge  = false;
		mouseAtRightEdge = false;
		mouseAtUpperEdge = false;
		mouseAtLowerEdge = false;
		
		mouseLeftClick   = false;
		mouseRightClick  = false;
		
	}
	public static void resetWASDBooleans() {
		
		wPressed = false;
		aPressed = false;
		sPressed = false;
		dPressed = false;
		rPressed = false;
		fPressed = false;
		tPressed = false;
		gPressed = false;
	}
	public static void resetHotkeyBooleans() {
		
		cPressed = false;
		vPressed = false;
		mPressed = false;
		bPressed = false;
		
	}
	public static void resetArrowBooleans() {
		
		leftArrow  = false;
		rightArrow = false;
		upArrow    = false;
		downArrow  = false;
		
	}
	
	public static void resetAll() {
		
		resetMenuSelect();
		resetMouseBooleans();
		resetWASDBooleans();
		resetArrowBooleans();
	}
}
