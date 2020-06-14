package mapModes;

import java.util.HashMap;

import assets.meshes.geometry.Color;
import utils.ColorPalette;
import world.GameBoard;
import world.WorldManager;

public class MapModesCreater {
	
	public static Color BLANK_COLOR = new Color(1, 1, 1, 0);
	
	public static final int BLANK_MODE = 0;
	public static final int FERTILITY_MODE = 1;
	public static final int FOREST_MODE = 2;
	public static final int PATH_MODE = 3;
	
	
	public static HashMap<Integer, MapMode> getMapModes() {
		
		float alpha = 0.5f;
		
		HashMap<Integer, MapMode> modes = new HashMap<>(3);
		modes.put(BLANK_MODE,     (int i) -> BLANK_COLOR);
		modes.put(FERTILITY_MODE, (int i) -> new Color(0.5f + GameBoard.getTile(i).getFertility().getValue()/2f, 0, 0, alpha));
//		modes.put(FOREST_MODE,    (int i) -> new Color(0.5f + GameBoard.getTile(i).getForest().getValue()/2f, 0, 0, alpha));
		modes.put(FOREST_MODE,    (int i) -> ColorPalette.colorMix(0f, 1f, 0f, alpha, 1f-0.5f*GameBoard.getTile(i).getForest().getValue(), 0f, 0f, alpha));
		modes.put(PATH_MODE,      (int i) -> {
			if (WorldManager.isInLatestPath(i)) {return new Color(1f, 0f, 0f, alpha);}
			else if (GameBoard.getTile(i).isWater()) {return new Color(0f, 0f, 1f, alpha);}
			else {return new Color(0f, 1f, 0f, alpha);}
		});
		
		return modes;
	}
	
}
