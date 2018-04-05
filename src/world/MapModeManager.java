package world;

import assets.meshes.geometry.Color;
import models.worldModels.BoardModels;

public class MapModeManager {
	
	private BoardModels boardModels;
	
	//TODO: set to correct values
	private Color[] blankMode;
	private Color[] fertilityMode;
	private Color[] forestMode;
	
	private Color[][] modes;
	private int currentMode;		//TODO: set correct initial value!
	
	//************************** constructor ***************************
	
	public MapModeManager(BoardModels boardModels) {
		
		this.boardModels = boardModels;
		
		modes = new Color[][] {blankMode, fertilityMode, forestMode};
		
	}
	
	//*************************** change mode **************************
	
	/**
	 * 
	 * @param mode index of the mode in "Color[][] modes"
	 * @return true, if mode truely changed
	 */
	public boolean changeModeTo(int mode) {
		
		if (currentMode == mode) {
			return false;
		}
		
		currentMode = mode;
		boardModels.setHexColor(modes[mode]);
		
		return true;
		
	}
	
}
