package utils;

import interaction.input.KeyInput;
import mapModes.MapModesManager;

import java.util.HashMap;
import java.util.Set;
import static org.lwjgl.glfw.GLFW.*;



public class ProvisionalUI {
	
	private MapModesManager mmm;
	
	private HashMap<Integer, KeyAction> map;
	private Set<Integer> keySet;
	
	//**************************** cosntructor *************************
	
	public ProvisionalUI(MapModesManager mmm) {
		
		this.mmm = mmm;
		setUp();
		
	}
	
	private void setUp() {
		
		map.put(GLFW_KEY_N, ()-> {mmm.changeModeTo(0);System.out.println("changed to blank mode");});
		map.put(GLFW_KEY_M, ()-> {mmm.changeModeTo(1);System.out.println("changed to fertility mode");});
		map.put(GLFW_KEY_B, ()-> {mmm.changeModeTo(2);System.out.println("changed to forest mode");});
		
	}
	
	//**************************** process input ******************************
	
	public void processInput() {
		
		for (Integer key : keySet) {
			if (KeyInput.keyPressed(key)) {
				map.get(key).execute();
			}
		}
		
	}
	
}
