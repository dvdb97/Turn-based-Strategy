package mapModes;

import java.util.HashMap;

import assets.meshes.geometry.Color;
import world.WorldManager;

public class MapModesCreater {
	
	public static HashMap<Integer, MapMode> getMapModes() {
		
		HashMap<Integer, MapMode> modes = new HashMap<>(2);
		modes.put(0, getBlankColorFunc());
		modes.put(1, getFertilityColorFunc());
		modes.put(2, getForestColorFunc());
		
		return modes;
	}
	
	private static MapMode getBlankColorFunc() {
		
		return new MapMode() {
			
			private Color blankColor = new Color(1, 1, 1, 0);
			
			public Color getColor(int i) {
				return blankColor;
			}
			
		};
		
	}
	
	
	
	private static MapMode getFertilityColorFunc() {
		
		return new MapMode() {
			
			public Color getColor(int i) {
				return new Color(0.5f + WorldManager.getFertility(i)/2f, 0, 0, 0.5f);
			}
			
		};
		
	}
	
	private static MapMode getForestColorFunc() {
		
		return new MapMode() {
			
			public Color getColor(int i) {
				return new Color(0.5f + WorldManager.getFertility(i)/2f, 0, 0, 0.5f);
			}
			
		};
		
	}
	
}
