package mapModes;

import java.util.HashMap;

import assets.meshes.geometry.Color;
import world.WorldManager;
import world.gameBoard.GameBoard;

public class MapModesCreater {
	
	public static Color BLANK_COLOR = new Color(1, 1, 1, 0);
	
	public static final int BLANK_MODE = 0;
	public static final int FERTILITY_MODE = 1;
	public static final int FOREST_MODE = 2;
	
	
	public static HashMap<Integer, MapMode> getMapModes() {
		
		HashMap<Integer, MapMode> modes = new HashMap<>(3);
		modes.put(BLANK_MODE,     (int i) -> BLANK_COLOR);
		modes.put(FERTILITY_MODE, (int i) -> new Color(0.5f + GameBoard.getTile(i).getFertility().getValue()/2f, 0, 0, 0.5f));
		modes.put(FOREST_MODE,    (int i) -> new Color(0.5f + GameBoard.getTile(i).getForest().getValue()/2f, 0, 0, 0.5f));
		
		return modes;
	}
	
}
