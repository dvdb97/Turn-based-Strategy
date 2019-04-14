package font;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import input.ToggleButton;
import interaction.input.KeyEventManager;
import interaction.input.KeyInput;
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
		KeyEventManager kim = new KeyEventManager();
		KeyInputHandler.setKeyEventManager(kim);
	//	kim.addKeyDownEventListener((key) -> processKeyInput(KeyStringConverter.getStringOf(key)));
		kim.addKeyDownEventListener((key) -> System.out.println("**************"+KeyStringConverter.getStringOf(key)));
		setFunctions();
	}
	
	private void setFunctions() {
		setEnableFunc((e) -> color=Color.getComplement(color));
		setDisableFunc((e) -> color=Color.getComplement(color));
	}
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		super.update(parentMatrix);
	}
	
	private void processKeyInput(String string) {
		
		currentText += string;
		textBox.changeTextTo(currentText);
		
	}
	
}
