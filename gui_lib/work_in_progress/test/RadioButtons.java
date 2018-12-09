package work_in_progress.test;

import assets.meshes.geometry.Color;
import dataType.AdvancedElementList;
import dataType.GUIElementMatrix;
import fundamental.Container;
import input.ToggleButton;
import stbFont.TTFBox;
import static utils.ColorPalette.*;

public class RadioButtons extends Container {
	
	private int currentButton;
	private AdvancedElementList<TTFBox> labels;
	private AdvancedElementList<ToggleButton> buttons;
	
	private float buttonHeight, buttonWidth, buttonOffsetX, buttonOffsetY;
	private float labelHeight;
	
	public RadioButtons(Color color, float labelHeight, GUIElementMatrix transformationMatrix) {
		super(color, transformationMatrix);
		
		buttonHeight = 0.2f;
		buttonWidth = 0.2f;
		buttonOffsetX = 0.2f;
		buttonOffsetY = -0.2f;
		
		this.labelHeight = labelHeight;
		buttons = new AdvancedElementList<>(1);
		children.add(buttons);
		labels = new AdvancedElementList<>(1);
		children.add(labels);
	}
	
	public void addButton(String label, Color color) {
		TestToggleButton button = new TestToggleButton(color, new GUIElementMatrix(buttonOffsetX, buttonOffsetY-buttonHeight*buttons.size(), buttonWidth, buttonHeight));
		buttons.add(button);
		labels.add(new TTFBox(buttonOffsetX+buttonWidth, buttonOffsetY-buttonHeight*buttons.size(), labelHeight, label, color));
		button.setEnableFunc(  (element) -> changeToButton(buttons.indexOf(element)) );
		button.setDisableFunc( (element) -> disableFunc(buttons.indexOf(element)) );
	}
	
	private void changeToButton(int index) {
		if (currentButton != index) {
			toggleCurrentButton();
			currentButton = index;
		}
	}
	
	private void toggleCurrentButton() {
		buttons.get(currentButton).toggle();
	}
	
	private void disableFunc(int index) {
		if (currentButton == index) {
			buttons.get(index).toggle();
		}
	}
}
