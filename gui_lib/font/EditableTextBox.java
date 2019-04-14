package font;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import input.ToggleButton;
import interaction.input.KeyInputHandler;
import rendering.shapes.implemented.GUIQuad;
import static utils.ColorPalette.*;

public class EditableTextBox extends ToggleButton{
	
	//TODO: private
	public TextBox textBox;
	private String currentText;
	
	public EditableTextBox(float xShift, float yShift, float xStretch, float reqHeight, Color color) {
		super(new GUIQuad(), color, new GUIElementMatrix(xShift, yShift, xStretch, reqHeight));
		currentText = "$";
		textBox = new TextBox(xShift, yShift, reqHeight, currentText, BLACK);
		setFunctions();
	}

	private void setFunctions() {
		setEnableFunc((e) -> color=Color.getComplement(color));
		setDisableFunc((e) -> color=Color.getComplement(color));
	}
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		super.update(parentMatrix);
		if (isEnabled())
			processKeyInput();
	}
	
	private void processKeyInput() {
		
		for (int k=32; k<=90; k++) {
			if (KeyInputHandler.keyPressed(k)) {
				currentText += (char)k;
				textBox.changeTextTo(currentText);
				break;
			}
		}
		
	}
	
}
