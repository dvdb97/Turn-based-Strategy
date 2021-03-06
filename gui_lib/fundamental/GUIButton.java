package fundamental;

import function.GUIEventListener;
import layout.IGUILayoutNode.FlexDirection;
import rendering.shapes.GUIShape;

public class GUIButton extends GUIContainer<GUITextField> {
	
	/**
	 * 
	 * @param shape The shape of this element.
	 * @param width The width of this element in pixels.
	 * @param height The height of this element in pixels.
	 */
	public GUIButton(GUIShape shape, int width, int height) {
		super(shape, width, height, FlexDirection.ROW, 1);
	}
	
	
	/**
	 * 
	 * @param shape The shape of this element.
	 * @param widthPercent The width of this element relative to the width of its parent.
	 * @param heightPercent The height of this element relative to the height of its parentr.
	 */
	public GUIButton(GUIShape shape, float widthPercent, float heightPercent) {
		super(shape, widthPercent, heightPercent, FlexDirection.ROW, 1);
	}
	
	
	public void setLabel(String text, String font, int fontSize) {
		GUITextField label = new GUITextField(text, font, 100f, 100f, fontSize);
		label.setActive(false);
		
		addChild(label);
	}
	
	
	/**
	 * 
	 * Add a function that will be called when the left mouse button
	 * is pressed down while targeting this element with the cursor.
	 * 
	 * @param listener The listener that will be called.
	 */
	public void addOnClickListener(GUIEventListener listener) {
		addLeftMouseButtonDownListener(listener);
	}
	
	
	/**
	 * 
	 * Add a function that will be called when the left mouse button
	 * is still being pressed while targeting this element.
	 * 
	 * @param listener The listener that will be called. 
	 */
	public void addOnHoldListener(GUIEventListener listener) {
		addLeftMouseButtonHoldListener(listener);
	}
	
	
	/**
	 * 
	 * Add a function that will be called when the left mouse button
	 * is released after clicking this element.
	 * 
	 * @param listener The listener that will be called.
	 */
	public void addOnReleaseListener(GUIEventListener listener) {
		addLeftMouseButtonReleaseListener(listener);
	}
	
	
	public void addOnMouseEnterListener(GUIEventListener listener) {
		addMouseEnterListener(listener);
	}
	
	
	public void addOnMouseStayListener(GUIEventListener listener) {
		addMouseStayListener(listener);
	}
	
	
	public void addOnMouseLeaveListener(GUIEventListener listener) {
		addMouseLeaveListener(listener);
	}
	
}