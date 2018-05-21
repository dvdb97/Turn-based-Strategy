package work_in_progress;

import java.util.ArrayList;

import assets.meshes.geometry.Color;

public abstract class RadioButton extends Container {
	
	private AdvancedElementList<ToggleButton> buttons;
	private int currentButton;
	
	protected RadioButton(Shape shape, Color color, GUIElementMatrix transformationMatrix, int numButtons) {
		super(shape, color, transformationMatrix);
		
		buttons = new AdvancedElementList<>(numButtons);
		children.add(buttons);
		
	}
	
	protected void addButton(ToggleButton button) {
		
		button.setEnableFunc( (e) -> {
			buttons.get(currentButton).toggle();
			currentButton = buttons.indexOf(e);
		} );
		
		button.setDisableFunc(new EmptyFunction());
		
		buttons.add(button);
		
	}
	
	
}