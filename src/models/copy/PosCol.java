package models.copy;

import assets.meshes.geometry.Color;
import models.seeds.ColorFunction;

public class PosCol implements ColorFunction{
	
	private float length = 1024;
	private float width = 512;
	
	public PosCol(int length, int width) {
		this.length = length;
		this.width = width;
	}
	
	@Override
	public Color color(int x, int y, float height) {
		return new Color(x/length, y/width, 0.5f, 1.0f);
	}
	
}
