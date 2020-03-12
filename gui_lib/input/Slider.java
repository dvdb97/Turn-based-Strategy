package input;

import java.util.LinkedList;

import function.Function;
import function.GUIEventListener;
import fundamental.GUIContainer;
import gui_core.Input;
import layout.IGUILayoutNode.FlexDirection;
import math.MathUtils;
import moving_parts.Handle;
import rendering.shapes.GUIShape;

public class Slider extends GUIContainer<Handle> {
	
	private Handle handle;
	private float value;
	
	private LinkedList<GUIEventListener> listeners;
	
	
	public Slider(GUIShape socketShape, GUIShape handleShape, int width, int height, FlexDirection direction) {
		super(socketShape, width, height, direction);
		
		init(handleShape, direction);
	}
	
	
	public Slider(GUIShape socketShape, GUIShape handleShape, float width, float height, FlexDirection direction) {
		super(socketShape, width, height, direction);
		
		init(handleShape, direction);
	}
	
	
	private void init(GUIShape handleShape, FlexDirection direction) {
		if (direction == FlexDirection.COLUMN) {
			handle = new Handle(handleShape, 100f, 10f);
		} else {
			handle = new Handle(handleShape, 20f, 100f);
		}
		
		
		handle.addDragListener(e -> moveSlider(e));
		addChild(handle);
		
		addLeftMouseButtonDownListener(e ->	moveSlider(e));
		
		listeners = new LinkedList<GUIEventListener>();
	}
	
	
	private void moveSlider(Input input) {
		if (getFlexDirection() == FlexDirection.COLUMN) {
			int globalYPos = getGlobalYPosition();
			
			int clampedY = MathUtils.clamp(input.cursorY - globalYPos, 0, getHeight());
			float percent = (float)clampedY / (float)getHeight() * 100f;
			
			handle.setLocalYPosition(percent);
			this.value = percent;
		} else {
			int globalXPos = getGlobalXPosition();
			
			int clampedX = MathUtils.clamp(input.cursorX - globalXPos, 0, getWidth());
			float percent = (float)clampedX / (float)getWidth() * 100f;
			
			handle.setLocalXPosition(percent);
			this.value = percent;
		}
		
		listeners.forEach(e -> e.execute(input));
	}
	
	
	public void addEventListener(GUIEventListener listener) {
		listeners.add(listener);
	}
	
	
	public void setValue(float value) {
		this.value = value;
		handle.setLocalXPosition(value);
	}
	
	
	public float getValue() {
		return value;
	}

}
