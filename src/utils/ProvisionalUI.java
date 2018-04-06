package utils;

import interaction.input.KeyInput;
import world.MapModeManager;

import static org.lwjgl.glfw.GLFW.*;
import static world.MapModeManager.*;

public class ProvisionalUI {
	
	private MapModeManager mmm;
	
	//**************************** cosntructor *************************
	
	public ProvisionalUI(MapModeManager mmm) {
		
		this.mmm = mmm;
		
	}
	
	//**************************** process input ******************************
	
	public void processInput() {
		
		if (KeyInput.keyPressed(GLFW_KEY_N)) {
			mmm.changeModeTo(MM_BLANK);
		}
		
		if (KeyInput.keyPressed(GLFW_KEY_M)) {
			mmm.changeModeTo(MM_FERTILITY);
		}
		
		if (KeyInput.keyPressed(GLFW_KEY_B)) {
			mmm.changeModeTo(MM_FOREST);
		}

		
	}
	
}
