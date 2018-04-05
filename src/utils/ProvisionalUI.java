package utils;

import interaction.input.KeyInput;
import world.MapModeManager;

import static org.lwjgl.glfw.GLFW.*;

public class ProvisionalUI {
	
	private MapModeManager mmm;
	
	//**************************** cosntructor *************************
	
	public ProvisionalUI(MapModeManager mmm) {
		
		this.mmm = mmm;
		
	}
	
	//**************************** process input ******************************
	
	public void processInput() {
		
		if (KeyInput.keyPressed(GLFW_KEY_N)) {
			mmm.changeModeTo(0);
		}
		
		if (KeyInput.keyPressed(GLFW_KEY_M)) {
			mmm.changeModeTo(1);
		}
		
		if (KeyInput.keyPressed(GLFW_KEY_B)) {
			mmm.changeModeTo(2);
		}

		
	}
	
}
