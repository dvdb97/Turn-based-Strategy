package work_in_progress;

import assets.meshes.geometry.Color;

public abstract class RadioButton extends Container {
	
	private AdvancedElementList<PushButton> buttons;
	private int currentButton;
	
	protected RadioButton(Shape shape, Color color, GUIElementMatrix transformationMatrix, int numButtons) {
		super(shape, color, transformationMatrix);
		
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