package gui.test;

import elements.GUIElementBase;
import elements.functions.GUIEventHandler;
import elements.input.GUIToggleButton;
import styles.GUIConst;


public class TestToggleButton extends GUIToggleButton {

	public TestToggleButton() {
		super(0.1f, -0.1f, 0.8f, 0.8f, "Click me!");
		// TODO Auto-generated constructor stub
		
		this.setOnclickFunc(new GUIEventHandler() {
			@Override
			public void function(GUIElementBase element) {
				setLabel(isActivated() ? "Nice!" : "Click me!");
				setLabelPosition(GUIConst.Position.CENTER);
			}
		});
		
	}

}
