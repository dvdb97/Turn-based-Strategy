package input.buttons;

import fundamental.GUIContainer;
import gui_core.Input;
import rendering.shapes.implemented.GUIQuad;
import utils.ColorPalette;

public class OptionSet<E extends ToggleButton> extends GUIContainer<E> {
	
	private E enabledButton;
	private E defaultButton;

	public OptionSet(int width, int height, FlexDirection flexDirection) {
		super(new GUIQuad(ColorPalette.ZERO), width, height, flexDirection);
	}
	
	
	public OptionSet(float widthPercent, float heightPercent, FlexDirection flexDirection) {
		super(new GUIQuad(ColorPalette.ZERO), widthPercent, heightPercent, flexDirection);
	}


	@Override
	public void addChild(E element) {
		//Calls a function that resets the toggle that is currently enabled.
		element.addEnableListener((Input input) -> enableButton(element, input));
		
		super.addChild(element);
	}
	
	
	public void addButton(E element) {
		addChild(element);
	}
	
	
	public void addDefaultButton(E element) {
		Input input = new Input();
		
		if (defaultButton != null) {
			defaultButton.disable(input);
		}
		
		defaultButton = element;
		addButton(element);
		defaultButton.enable(new Input());
	}
	
	
	private void enableButton(E button, Input input) {
		if (enabledButton != null) {
			enabledButton.setActive(true);
			enabledButton.disable(input);
		}
			
		enabledButton = button;
		enabledButton.setActive(false);
	}
	
}
