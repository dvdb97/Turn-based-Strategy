package world.estate;

import java.util.ArrayList;

import utils.TileSurrounding;
import world.GameBoard;
import world.Tile;
import world.buildings.Building;

public class City extends Estate {
	
	private SphereOfInfluence spoi;		//TODO: spoi nicht bei city abspeichern, sondern bei bedarf sowas wie SPOI-Klasse.getSPOI(tile, radius)
	private int spoiRadius;
	
	private Population population;
	
	private ArrayList<Building> buildings;
	
	//TODO: to implement
	//all trading related stuff
	//...
	
	//******************** constructor ***************************************
	
	public City(Population population) {
		
		this.population = population;
		this.spoiRadius = 1;		//TODO: don't hard code
		this.buildings = new ArrayList<>();
	}
	
	//************************************************************************
	
	public void addBuilding(Building building) {
		buildings.add(building);
		func.accept(this);
	}
	
	//********************     get     ***************************************
	
	public ArrayList<Building> getBuildings() {
		return buildings;
	}
	
	private Tile[] getSPOI() {
		
		int[] indices = TileSurrounding.getSurrounding(GameBoard.getTile(this).getIndex(), spoiRadius);
		Tile[] spoi = new Tile[indices.length];
		for (int i=0; i<indices.length; i++) {
			spoi[i] = GameBoard.getTile(indices[i]);
		}
		return spoi;
	}
	
	@Override
	public String getInfoString() {
		return "Tile: "+GameBoard.getTile(this).getIndex()+"\nNumber of Buildings: "+buildings.size();
	}
		
}