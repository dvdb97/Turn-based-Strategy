package gui.test;

import elements.fundamental.GUIElementBase;
import elements.input.buttons.GUIToggleButton;
import styles.GUIConst;


public class TestToggleButton extends GUIToggleButton {

	public TestToggleButton() {
		super(0.1f, -0.1f, 0.8f, 0.8f, "");
		// TODO Auto-generated constructor stub
		
		this.setOnclickFunc((GUIElementBase element) -> {
				setLabel(isEnabled() ? "Nice!" : "Click me!");
				setLabelPosition(GUIConst.Position.CENTER);
			});
		
	}

}
