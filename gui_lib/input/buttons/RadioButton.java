package input.buttons;

import rendering.shapes.implemented.GUIRadioButton;

public class RadioButton  extends ToggleButton {
	
	public RadioButton(int diameter) {
		super(new GUIRadioButton(), diameter, diameter);
		
		this.addEnableListener((e) -> activate());
		this.addDisableListener((e) -> deactive());
	}
	
	
	public RadioButton(float diameterPercent) {
		super(new GUIRadioButton(), diameterPercent, diameterPercent);
		
		this.addEnableListener((e) -> activate());
		this.addDisableListener((e) -> deactive());
	}
	
	
	private void activate() {
		getShape().setState("active");
	}
	
	
	private void deactive() {
		getShape().setState("Inactive");
	}
	
}
