package utils;

import interaction.input.KeyInput;
import mapModes.MapModesManager;

import static org.lwjgl.glfw.GLFW.*;

public class ProvisionalUI {
	
	private MapModesManager mmm;
	
	//**************************** cosntructor *************************
	
	public ProvisionalUI(MapModesManager mmm) {
		
		this.mmm = mmm;
		
	}
	
	//**************************** process input ******************************
	
	public void processInput() {
		
		if (KeyInput.keyPressed(GLFW_KEY_N)) {
			mmm.changeModeTo(0);
			System.out.println("changed to blank mode");
		}
		
		if (KeyInput.keyPressed(GLFW_KEY_M)) {
			mmm.changeModeTo(1);
			System.out.println("changed to fertility mode");
		}
		
		if (KeyInput.keyPressed(GLFW_KEY_B)) {
			mmm.changeModeTo(2);
			System.out.println("changed to forest mode");
		}

		
	}
	
}
