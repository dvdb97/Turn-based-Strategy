package work_in_progress.test;

import output.TextBox;
import rendering.shapes.GUIQuad;

import static utils.ColorPalette.*;

import container.GUIWindow;
import dataType.GUIElementMatrix;

public class TextWindow extends GUIWindow{
	
	private TextBox text;
	
	public TextWindow() {
		super(new GUIQuad(), GREEN, new GUIElementMatrix(-0.2f, -0.2f, 0.5f, 0.5f));
		text = new TextBox(new GUIQuad(), GRAY, new GUIElementMatrix(0.1f, -0.1f, 0.8f, 0.8f), "Lorem Ip");
		children.add(text);
	}
	
}