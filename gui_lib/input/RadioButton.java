package input;

import assets.meshes.geometry.Color;
import dataType.AdvancedElementList;
import dataType.GUIElementMatrix;
import fundamental.Container;

public abstract class RadioButton extends Container {
	
	private AdvancedElementList<PushButton> buttons;
	private int currentButton;
	
	protected RadioButton(Color color, GUIElementMatrix transformationMatrix, int numButtons) {
		super(color, transformationMatrix);
		
		buttons = new AdvancedElementList<>(numButtons);
		children.add(buttons);
		
	}
	
	protected void addButton(PushButton button) {
		
		button.setFunction((e) -> {
			
			if (currentButton != buttons.indexOf(e)) {
				buttons.get(currentButton).reset();
			}
			
			currentButton = buttons.indexOf(e);
			
		});
		
		buttons.add(button);
		
	}
	
	
}