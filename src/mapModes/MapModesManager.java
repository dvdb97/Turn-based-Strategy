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
		
		changeModeTo(0);
		currentMode = 0;
		
	}
	
	
	
	/**
	 * 
	 * @param modeKey index of the mode in "Color[][] modes"
	 * @return true, if mode truely changed
	 */
	public boolean changeModeTo(int modeKey) {
		
		//TODO: work with exceptions here
		if (!modes.containsKey(modeKey)) {
			System.err.println("mode not found");
			return false;
		}
		
		if (currentMode == modeKey) {
			return false;
		}
		
		boardModels.setHexColor(modes.get(modeKey));
		currentMode = modeKey;
		
		return true;
		
	}
	
}
