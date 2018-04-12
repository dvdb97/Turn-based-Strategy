package mapModes;

import assets.meshes.geometry.Color;
import world.WorldManager;

public class MapMode {
	
	private Color[] colors;
	private ColorFunc2 colorFunc;
	
	
	public MapMode(ColorFunc2 colorFunc2) {
		
		colorFunc = colorFunc2;
		refreshColors();
	}
	
	
	public void refreshColors() {
		
		for (int i=0; i<WorldManager.getNumTiles(); i++) {
			colors[i] = colorFunc.getColor(i);
		}
		
	}
	
	
	/**
	 * @param i index of the requested tile
	 * @return the color of the requested tile according to this map mode
	 */
	public Color get(int i) {
		return colors[i];
	}
	
	/*
	 * With get(int i) you get the pointer of the object Color.
	 * So both the hexagon grid and the map mode have this pointer.
	 * If the color changes the pointer does not have to be transferred
	 * to the grid anew. it is just neccessary to call the grid's method
	 * updateColor().
	 * 
	 * It's possible but maybe not practical; it could affect the
	 * readability un cleanness.
	 * Because you need a method that transfers the color's pointer to
	 * the grid anyway
	 */
	
}