package world;

import java.util.HashSet;
import java.util.Set;

import assets.meshes.geometry.Color;
import world.city.City;
import world.gameBoard.Tile;
import world.units.Unit;

public class Faction {

	private String name;
	private Color color;
	
	//All objects of the world that belong to the faction.
	//TODO: Not sure if sets are useful here.
	private Set<City> cities;
	private Set<Tile> tiles;
	private Set<Unit> units;
	
	public Faction(String name, Color color) {
		this.name = name;
		
		cities = new HashSet<City>();
		tiles = new HashSet<Tile>();
		units = new HashSet<Unit>();
	}
	
	private String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {		
		return name.equals(((Faction)obj).getName());
	}

}
