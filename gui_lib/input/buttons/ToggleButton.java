package input.buttons;

import java.util.LinkedList;
import java.util.List;

import function.GUIEventListener;
import fundamental.GUIButton;
import gui_core.Input;
import rendering.shapes.GUIShape;

public class ToggleButton extends GUIButton {
	
	protected boolean enabled;
	
	private List<GUIEventListener> enableListeners;
	private List<GUIEventListener> disableListeners;
	
	/**
	 * 
	 * @param shape The shape of this element.
	 * @param width The width of this element in pixels.
	 * @param height The height of this element in pixels.
	 */
	public ToggleButton(GUIShape shape, int width, int height) {
		super(shape, width, height);
		
		enableListeners = new LinkedList<GUIEventListener>();
		disableListeners = new LinkedList<GUIEventListener>();
		
		addLeftMouseButtonReleaseListener((Input input) -> toggle(input));
	}
	
	
	/**
	 * 
	 * @param shape The shape of this element.
	 * @param widthPercent The width of this element relative to the width of its parent.
	 * @param heightPercent The height of this element relative to the height of its parentr.
	 */
	public ToggleButton(GUIShape shape, float widthPercent, float heightPercent) {
		super(shape, widthPercent, heightPercent);
		
		enableListeners = new LinkedList<GUIEventListener>();
		disableListeners = new LinkedList<GUIEventListener>();
		
		addLeftMouseButtonDownListener((Input input) -> toggle(input));
	}
	
	
	/**
	 * 
	 * Switch the current state of this ToggleButton.
	 * 
	 * @param input The current input.
	 */
	private void toggle(Input input) {
		enabled = !enabled;
		
		if(enabled)
			enableListeners.forEach((e) -> e.execute(input));
		else
			disableListeners.forEach((e) -> e.execute(input));
	}
	
	
	/**
	 * 
	 * Enable this ToggleButton.
	 * 
	 * @param input The current input.
	 */
	public void enable(Input input) {
		if (!enabled) {
			enableListeners.forEach((e) -> e.execute(input));
			enabled = true;
		}
	}
	
	
	/**
	 * 
	 * Disable this ToggleButton.
	 * 
	 * @param input The current input.
	 */
	public void disable(Input input) {
		if (enabled) {
			disableListeners.forEach((e) -> e.execute(input));
			enabled = false;
		}
	}
	
	
	/**
	 * 
	 * Add a listener that will be called when this button is enabled.
	 * 
	 * @param enableListener The listener to add to this element.
	 */
	public void addEnableListener(GUIEventListener enableListener) {
		this.enableListeners.add(enableListener);
	}
	
	
	/**
	 * 
	 * Add a listener that will be called when this button is disabled.
	 * 
	 * @param disableListener The listener to add to this element.
	 */
	public void addDisableListener(GUIEventListener disableListener) {
		this.disableListeners.add(disableListener);
	}
	
	
	/**
	 * 
	 * @return Returns true if this element is enabled.
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
}
