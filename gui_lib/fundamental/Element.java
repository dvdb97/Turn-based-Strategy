package fundamental;

import static org.lwjgl.util.yoga.Yoga.*;

import java.util.LinkedList;
import java.util.List;

import function.GUIDragListener;
import function.GUIEventListener;
import gui_core.Input;
import rendering.shapes.GUIShape;

public abstract class Element {	
	
	public enum Direction {
		LEFT, TOP, RIGHT, BOTTOM, START, END, HORIZONTAL, VERTICAL, ALL
	}
	
	public enum Alignment {
		AUTO, CENTER, BASELINE, FLEX_END, FLEX_START, SPACE_AROUND, SPACE_BETWEEN, STRETCH
	}
	
	//The Yoga ID for this element.
	protected final long layoutID;
	
	private GUIShape shape;
	
	//Cursor input event listeners.
	private boolean isTargeted;
	private List<GUIEventListener> mouseEnterListeners;
	private List<GUIEventListener> mouseStayListeners;
	private List<GUIEventListener> mouseLeaveListeners;
	
	//Left mouse button event listeners.
	private boolean isLeftClicked;
	private List<GUIEventListener> leftMouseButtonDownListeners;
	private List<GUIEventListener> leftMouseButtonHoldListeners;
	private List<GUIEventListener> leftMouseButtonReleaseListeners;
	
	private boolean isRightClicked;
	private List<GUIEventListener> rightMouseButtonDownListeners;
	private List<GUIEventListener> rightMouseButtonHoldListeners;
	private List<GUIEventListener> rightMouseButtonReleaseListeners;
	
	private List<GUIEventListener> dragListener;
	
	private boolean active = true;
	private boolean visible = true;
	
	
	/**
	 * 
	 * @param width The width of the element in pixels.
	 * @param height The height of the element in pixels.
	 */
	protected Element(GUIShape shape, int width, int height) {
		layoutID = YGNodeNew();
		
		this.shape = shape;
		setWidth(width);
		setHeight(height);
		init();
	}
	
	
	/**
	 * 
	 * @param widthPercent The width of the element in relation to the parent.
	 * @param heightPercent The height of the element in relation to the parent.
	 */
	protected Element(GUIShape shape, float widthPercent, float heightPercent) {
		layoutID = YGNodeNew();
		
		this.shape = shape;
		setWidth(widthPercent);
		setHeight(heightPercent);
		init();
	}
	
	
	/**
	 * Init all event listener lists.
	 */
	private void init() {
		mouseEnterListeners = new LinkedList<GUIEventListener>();
		mouseStayListeners = new LinkedList<GUIEventListener>();
		mouseLeaveListeners = new LinkedList<GUIEventListener>();
		
		leftMouseButtonDownListeners = new LinkedList<GUIEventListener>();
		leftMouseButtonHoldListeners = new LinkedList<GUIEventListener>();
		leftMouseButtonReleaseListeners = new LinkedList<GUIEventListener>();
		
		rightMouseButtonDownListeners = new LinkedList<GUIEventListener>();
		rightMouseButtonHoldListeners = new LinkedList<GUIEventListener>();
		rightMouseButtonReleaseListeners = new LinkedList<GUIEventListener>();
		
		dragListener = new LinkedList<GUIEventListener>();
	}
	
	
	/**
	 * 
	 * @param width The width of the element in pixels.
	 */
	public void setWidth(int width) {
		YGNodeStyleSetWidth(layoutID, width);
	}
	
	
	/**
	 * 
	 * @param percent The width of the element in relation to the parent.
	 */
	public void setWidth(float percent) {
		YGNodeStyleSetWidthPercent(layoutID, percent);
	}
	
	
	/**
	 * 
	 * @return Returns the width of the element as it was calculated by Yoga.
	 */
	public int getWidth() {
		return (int)YGNodeLayoutGetWidth(layoutID);
	}
	
	
	/**
	 * 
	 * @param height The height of the element in pixels.
	 */
	public void setHeight(int height) {
		YGNodeStyleSetHeight(layoutID, height);
	}
	
	
	/**
	 * 
	 * @param percent The height of the element in relation to the parent.
	 */
	public void setHeight(float percent) {
		YGNodeStyleSetHeightPercent(layoutID, percent);
	}
	
	
	/**
	 * 
	 * @return Returns the height of the element as it was calculated by Yoga.
	 */
	public int getHeight() {
		return (int)YGNodeLayoutGetHeight(layoutID);
	}
	
	
	/**
	 * 
	 * Utility function that converts from a Java enum to a Yoga enum.
	 * 
	 * @param dir The java enum value.
	 * @return The corresponding Yoga enum value.
	 */
	private int directionToYGEnum(Direction dir) {
		switch (dir) {
			case ALL:
				return YGEdgeAll;
			case BOTTOM:
				return YGEdgeBottom;
			case TOP:
				return YGEdgeTop;
			case LEFT:
				return YGEdgeLeft;
			case RIGHT:
				return YGEdgeRight;
			case START:
				return YGEdgeStart;
			case END:
				return YGEdgeEnd;
			case HORIZONTAL:
				return YGEdgeHorizontal;
			default:
				return YGEdgeVertical;
		}
	}
	
	
	public void setMargin(Direction dir, int margin) {
		int edge = directionToYGEnum(dir);
		YGNodeStyleSetMargin(layoutID, edge, margin);
	}
	
	
	public void setMargin(Direction dir, float percent) {
		int edge = directionToYGEnum(dir);
		YGNodeStyleSetMarginPercent(layoutID, edge, percent);
	}
	
	
	public void setPadding(Direction dir, int padding) {
		int edge = directionToYGEnum(dir);
		YGNodeStyleSetPadding(layoutID, edge, padding);
	}
	
	
	public void setPadding(Direction dir, float percent) {
		int edge = directionToYGEnum(dir);
		YGNodeStyleSetPaddingPercent(layoutID, edge, percent);
	}
	
	
	private int alignmentToYGEnum(Alignment align) {
		switch (align) {
			case AUTO:
				return YGAlignAuto;
			case CENTER:
				return YGAlignCenter;
			case BASELINE:
				return YGAlignBaseline;
			case FLEX_START:
				return YGAlignFlexStart;
			case FLEX_END:
				return YGAlignFlexEnd;
			case SPACE_AROUND:
				return YGAlignSpaceAround;
			case SPACE_BETWEEN:
				return YGAlignSpaceBetween;
			default:
				return YGAlignStretch;
		}
	}
	
	
	public void setAlignment(Alignment align) {
		YGNodeStyleSetAlignSelf(layoutID, alignmentToYGEnum(align));
	}
	
	
	/**
	 * 
	 * @return Returns the x coordinate of the upper left corner of this element. Note that this
	 * isn't the global position but the offset relative to the parent's position.
	 */
	public int getLocalXPosition() {
		return (int)YGNodeLayoutGetLeft(layoutID);
	}
	
	
	/**
	 * 
	 * @param parentX The parent's x coordinate on the screen.
	 * @return Return this element's x coordinate on the screen.
	 */
	public int getGlobalXPosition(int parentX) {
		return parentX + getLocalXPosition();
	}
	
	
	/**
	 * 
	 * @return Returns the y coordinate of the upper left corner of this element. Note that this
	 * isn't the global position but the offset relative to the parent's position.
	 */
	public int getLocalYPosition() {
		return (int)YGNodeLayoutGetTop(layoutID);
	}
	
	
	/**
	 * 
	 * @param parentY The parent's y coordinate on the screen.
	 * @return Return this element's y coordinate on the screen.
	 */
	public int getGlobalYPosition(int parentY) {
		return parentY + getLocalYPosition();
	}
	
	
	/**
	 * 
	 * @return Returns the Yoga layout ID for this element.
	 */
	public long getLayoutID() {
		return layoutID;
	}
	
	
	/**
	 * 
	 * @return Returns the shape of this element.
	 */
	public GUIShape getShape() {
		return shape;
	}
	
	
	/**
	 * 
	 * @param shape Sets the shape of this element.
	 */
	public void setShape(GUIShape shape) {
		this.shape = shape;
	}
	
	
	/**
	 * 
	 * Renders the Element onto the screen.
	 * 
	 * @param parentX The x coordinate of the parent element.
	 * @param parentY The y coordinate of the parent element.
	 */
	public void render(int parentX, int parentY) {
		if (visible) {
			int x = getGlobalXPosition(parentX);
			int y = getGlobalYPosition(parentY);
			
			shape.render(x, y, getWidth(), getHeight());
		}
	}
	
	
	/**
	 * 
	 * Check if the cursor is currently targeting this element.
	 * 
	 * @param parentX The x coordinate of the parent element.
	 * @param parentY The y coordinate of the parent element.
	 * @param cursorX The x coordinate of the cursor.
	 * @param cursorY The y coordinate of the cursor.
	 * @return Returns true if the cursor is targeting this element.
	 */
	public boolean isTargeted(int parentX, int parentY, int cursorX, int cursorY) {
		int x = parentX + getLocalXPosition();
		int y = parentY + getLocalYPosition();
		
		return shape.isTargeted(x, y, getWidth(), getHeight(), cursorX, cursorY);
	}
	
	
	/**
	 * Updates the state that marks this element as targeted.
	 */
	private void updateTargetingState(Input input) {
		if (!isTargeted) {
			onMouseEnter(input);
			isTargeted = true;
		} else {
			onMouseStay(input);
		}
	}
	
	
	private void updateLeftMouseButtonState(Input input) {
		//Check if the left mouse button is actually pressed down.
		if (input.leftMouseButton) {
			if (!isLeftClicked) {
				onLeftMouseButtonDown(input);
				isLeftClicked = true;
			} else {
				onLeftMouseButtonHold(input);
				onDrag(input);
			}
		} else {
			if (isLeftClicked) {
				onLeftMouseButtonRelease(input);
				isLeftClicked = false;
			}
		}
	}
	
	
	private void updateRightMouseButtonState(Input input) {
		//Check if the right mouse button is actually pressed down.
		if (input.rightMouseButton) {
			if (!isRightClicked) {
				onRightMouseButtonDown(input);
				isRightClicked = true;
			} else {
				onRightMouseButtonHold(input);
			}
		} else {
			if (isRightClicked) {
				onRightKeyRelease(input);
				isRightClicked = false;
			}
		}	
	}
	
	
	/**
	 * Cleans up the input state when the cursor isn't targeting 
	 * this element anymore.
	 */
	private void resetInputStates(Input input) {
		if (isTargeted) {
			onMouseLeave(input);
			isTargeted = false;
		}
		
		if (isLeftClicked) {
			onLeftMouseButtonRelease(input);
			isLeftClicked = false;
		}
		
		if (isRightClicked) {
			onRightKeyRelease(input);
			isRightClicked = false;
		}
	}
	
	
	/**
	 * 
	 * This method handles everything regarding input.
	 * 
	 * @param parentX The x coordinate of the parent element.
	 * @param parentY The y coordinate of the parent element.
	 * @param cursorX The x coordinate of the cursor.
	 * @param cursorY The y coordinate of the cursor.
	 * @return Returns true if the user is currently interacting with this element.
	 */
	public void processInput(int parentX, int parentY, Input input) {
		if (active) {
			if (isTargeted(parentX, parentY, input.cursorX, input.cursorY)) {
				updateTargetingState(input);
				updateLeftMouseButtonState(input);
				updateRightMouseButtonState(input);		
			} else {
				resetInputStates(input);
			}
		}
	}
	
	
	protected void addMouseEnterListener(GUIEventListener listener) {
		mouseEnterListeners.add(listener);
	}
	
	
	private void onMouseEnter(Input input) {
		mouseEnterListeners.forEach((e) -> e.execute(input));
	}
	
	
	protected void addMouseStayListener(GUIEventListener listener) {
		mouseStayListeners.add(listener);
	}
	
	
	private void onMouseStay(Input input) {
		mouseStayListeners.forEach((e) -> e.execute(input));
	}
	
	
	protected void addMouseLeaveListener(GUIEventListener listener) {
		mouseLeaveListeners.add(listener);
	}
	
	
	private void onMouseLeave(Input input) {
		mouseLeaveListeners.forEach((e) -> e.execute(input));
	}
	
	
	protected void addLeftMouseButtonDownListener(GUIEventListener listener) {
		leftMouseButtonDownListeners.add(listener);
	}
	
	
	private void onLeftMouseButtonDown(Input input) {
		leftMouseButtonDownListeners.forEach((e) -> e.execute(input));
	}
	
	
	protected void addLeftMouseButtonHoldListener(GUIEventListener listener) {
		leftMouseButtonHoldListeners.add(listener); 
	}
	
	
	private void onLeftMouseButtonHold(Input input) {
		leftMouseButtonHoldListeners.forEach((e) -> e.execute(input));
	}
	
	
	protected void addLeftMouseButtonReleaseListener(GUIEventListener listener) {
		leftMouseButtonReleaseListeners.add(listener);
	}
	
	
	private void onLeftMouseButtonRelease(Input input) {
		leftMouseButtonReleaseListeners.forEach((e) -> e.execute(input));
	}
	
	
	protected void addRightMouseButtonDownListener(GUIEventListener listener) {
		rightMouseButtonDownListeners.add(listener);
	}
	
	
	private void onRightMouseButtonDown(Input input) {
		rightMouseButtonDownListeners.forEach((e) -> e.execute(input));
	}
	
	
	protected void addRightMouseButtonHoldListener(GUIEventListener listener) {
		rightMouseButtonHoldListeners.add(listener);
	}
	
	
	private void onRightMouseButtonHold(Input input) {
		rightMouseButtonHoldListeners.forEach((e) -> e.execute(input));
	}
	
	
	protected void addRightMouseButtonReleaseListener(GUIEventListener listener) {
		rightMouseButtonReleaseListeners.add(listener);
	}
	
	
	private void onRightKeyRelease(Input input) {
		rightMouseButtonReleaseListeners.forEach((e) -> e.execute(input));
	}
	
	
	public void addDragListener(GUIEventListener listener) {
		dragListener.add(listener);
	}
	
	
	private void onDrag(Input input) {
		dragListener.forEach((e) -> e.execute(input));
	}
	
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	public void setVisible(boolean active) {
		this.active = false;
		this.visible = false;
	}

	
	public boolean equals(Element element) {
		return element.layoutID == this.layoutID;
	}
	
}
