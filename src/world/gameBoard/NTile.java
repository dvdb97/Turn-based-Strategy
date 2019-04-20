package world.gameBoard;

import world.Faction;
import world.city.City;
import world.units.Unit;

public class NTile {
	
	private int index;
	private Faction faction = null;
	
	private City city = null;
	
	public NTile(int index) {
		this.index = index;
	}
	
	
	public void setCity(City city) {
		this.city = city;
	}
	
	
	public City getCity() {
		return city;
	}
	
	
	public boolean belongsToFaction() {
		return faction != null;
	}
	
	
	public void setFaction(Faction faction) {
		this.faction = faction;
	}
	
	
	public Faction getFaction() {
		return faction;
	}
	
	
	public float getPathCosts() {
		//TODO: Somehow compute the path costs.
		return 1f;
	}
	
	
	public float getPathCosts(Unit unit) {
		//TODO: Factor in the unit's properties.
		return 1f;
	}
	
	
	public boolean isPassable(Unit unit) {
		//Check if the tile belongs to a different faction.		
		if (belongsToFaction()) {
			if (getFaction().equals(unit.getFaction())) {
				return true;
			} else {
				return false;
			}
		}
		/*
		 * TODO: Check different terrain types (e.g. a land unit shouldn't be able
		 * to enter a water tile).
		 */
		return true;
	}
}
