package world;

import java.util.HashMap;

import assets.meshes.geometry.Color;
import models.worldModels.BoardModels;

public class MapModeManager {
	
	public static final int MM_BLANK = 0;
	public static final int MM_FERTILITY = 1;
	public static final int MM_FOREST = 2;
	
	private BoardModels boardModels;
	private int numTiles;
	
	//TODO: set to correct values
	private Color[] blankMode;
	private Color[] fertilityMode;
	private Color[] forestMode;
	
	private HashMap<Integer, Color[]> modes;
	private int currentMode;		//TODO: set correct initial value!
	
	//************************** constructor ***************************
	
	public MapModeManager(BoardModels boardModels) {
		
		this.boardModels = boardModels;
		
		modes = new HashMap<>(3);
		
		setUp();
	}
	
	//*************************** set up *******************************
	
	private void setUp() {
		
		numTiles = boardModels.getLength()*boardModels.getWidth();
		
		setUpBlankMode();
		setUpFertilityMode();
		setUpForestMode();
		
	}
	
	private void setUpBlankMode() {
		
		blankMode = new Color[numTiles];
		
		for (int i=0; i<numTiles; i++) {
			blankMode[i] = new Color(1, 1, 1, 0);
		}
		
	}
	
	private void setUpFertilityMode() {
		
		fertilityMode = new Color[numTiles];
		
		for (int i=0; i<numTiles; i++) {
			fertilityMode[i] = new Color(0.5f + WorldManager.getFertility(i)/2f, 0, 0, 0.5f);
		}
		
	}
	
	private void setUpForestMode() {
		
		forestMode = new Color[numTiles];
		
		for (int i=0; i<numTiles; i++) {
			fertilityMode[i] = new Color(0.5f + WorldManager.getFertility(i)/2f, 0, 0, 0.5f);
		}
		
	}
	
	
	//*************************** change mode **************************
	
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
