package mapModes;

import java.util.HashMap;

import models.gameboard.GameBoardModel;

public class MapModesManager {
	
	private GameBoardModel boardModels;
	private HashMap<Integer, MapMode> modes;
	
	private int currentMode;
	
	public MapModesManager(HashMap<Integer, MapMode> modes, GameBoardModel boardModels) {
		
		this.modes = modes;
		this.boardModels = boardModels;
		
		changeModeTo(0);
		currentMode = 0;
		
	}
	
	
	/**
	 * @param modeKey index of the mode in "Color[][] modes"
	 */
	public void changeModeTo(int modeKey) {
		
		if (!modes.containsKey(modeKey))
			throw new IllegalArgumentException("mode not found");
		
		boardModels.setHexColor(modes.get(modeKey));
		currentMode = modeKey;
				
	}
	
	public void refreshMapModeColor() {
		boardModels.setHexColor(modes.get(currentMode));
	}
	
	/**
	 * @return key of the current map mode
	 */
	public int getCurrentMode() {
		return currentMode;
	}
}
