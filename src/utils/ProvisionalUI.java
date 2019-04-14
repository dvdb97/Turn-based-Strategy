package utils;

import interaction.input.KeyInputHandler;
import mapModes.MapModesManager;

import java.util.HashMap;
import java.util.Set;

import gui.GameGUIManager;

import static org.lwjgl.glfw.GLFW.*;



public class ProvisionalUI {
	
	//TODO: private
	public MapModesManager mmm;
	
	private HashMap<Integer, KeyAction> map;
	private Set<Integer> keySet;
	
	//**************************** cosntructor *************************
	
	public ProvisionalUI(MapModesManager mmm) {
		
		this.mmm = mmm;
		setUp();
		
	}
	
	private void setUp() {
		
		map = new HashMap<>(3);
		map.put(GLFW_KEY_B, () -> mmm.changeModeTo(0));
		map.put(GLFW_KEY_N, () -> mmm.changeModeTo(1));
		map.put(GLFW_KEY_M, () -> mmm.changeModeTo(2));
		keySet = map.keySet();
		
	}
	
	//**************************** process input ******************************
	
	public void processInput() {
		
		for (Integer key : keySet) {
			if (KeyInputHandler.keyPressed(key)) {
				map.get(key).execute();
			}
		}
		
	}
	
}
