package output.charts;

import assets.meshes.geometry.Color;

public class Group {
	public String name;
	public Color color;
	public int amount;
	
	public Group(String name, Color color, int amount) {
		this.name = name;
		this.color = color;
		this.amount = amount;
	}
}
