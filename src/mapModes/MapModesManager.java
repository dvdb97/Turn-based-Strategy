package mapModes;

import java.util.HashMap;

import models.worldModels.BoardModels;

public class MapModesManager {
	
	private BoardModels boardModels;
	private HashMap<Integer, MapMode> modes;
	
	private int currentMode;
	
	public MapModesManager(HashMap<Integer, MapMode> modes, BoardModels boardModels) {
		
		this.modes = modes;
		this.boardModels = boardModels;
		
	}
	
	
	
	/**
	 * 
	 * @param mode index of the mode in "Color[][] modes"
	 * @return true, if mode truely changed
	 */
	public boolean changeModeTo(int mode) {
		
		//TODO: work with exceptions here
		if (!modes.containsKey(mode)) {
			System.err.println("mode not found");
			return false;
		}
		
		if (currentMode == mode) {
			return false;
		}
		
		boardModels.setHexColor(modes.get(mode));
		currentMode = mode;
		
		return true;
		
	}
	
}
