package container;

import assets.meshes.geometry.Color;
import dataType.AdvancedElementList;
import dataType.GUIElementMatrix;
import font.TextBox;
import function.BooleanFunction;
import function.Function;
import fundamental.Container;
import input.RadioToggleButton;
import input.ToggleButton;

import static utils.ColorPalette.*;

public class RadioButtons extends Container {
	
	private int currentButton;
	private AdvancedElementList<TextBox> labels;
	private AdvancedElementList<ToggleButton> buttons;
	
	private float buttonHeight, buttonWidth, buttonOffsetX, buttonOffsetY;
	private float labelHeight;
	
	public RadioButtons(Color color, float labelHeight, GUIElementMatrix transformationMatrix) {
		super(color, transformationMatrix);
		
		buttonHeight = 0.2f;
		buttonWidth = 0.4f;
		buttonOffsetX = 0.2f;
		buttonOffsetY = -0.2f;
		
		this.labelHeight = labelHeight;
		buttons = new AdvancedElementList<>(1);
		children.add(buttons);
		labels = new AdvancedElementList<>(1);
		children.add(labels);
	}
	
	public void addButton(String label, Color color, Function<ToggleButton> func) {
		RadioToggleButton button = new RadioToggleButton(color, new GUIElementMatrix(buttonOffsetX, buttonOffsetY-buttonHeight*buttons.size(), buttonWidth, buttonHeight));
		if (buttons.size() == 0) {
			button.setEnableFunc((element) -> {});
			button.toggle();
		}
		labels.add(new TextBox(buttonOffsetX+buttonWidth, buttonOffsetY-buttonHeight*buttons.size(), labelHeight, label, color));
		buttons.add(button);
		button.setEnableFunc(  (element) -> {changeToButton(buttons.indexOf(element));func.execute(button);} );
		button.setDisableFunc( (element) -> {} );
		button.setSuspendFunc(suspendFunc);
	}
	
	private BooleanFunction<ToggleButton> suspendFunc = new BooleanFunction<ToggleButton>() {
		@Override
		public boolean test(ToggleButton e) {
			return currentButton == buttons.indexOf(e);
		}
	};
	
	private void changeToButton(int index) {
		toggleCurrentButton();
		currentButton = index;
	}
	
	private void toggleCurrentButton() {
		buttons.get(currentButton).toggle();
	}
	
}
