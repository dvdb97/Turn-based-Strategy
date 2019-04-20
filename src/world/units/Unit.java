package world.units;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import datastructures.Tuple;
import world.Faction;
import world.gameBoard.NTile;

public abstract class Unit {
	
	//The position of the unit on the board.
	private int position;
	
	//The faction this unit belongs to.
	private Faction faction;
	
	//All tiles the unit can reach and the respective costs
	private Set<Integer> reachableTiles;
	private HashMap<Integer, Float> costs;
	
	private float totalBudget;
	private float remainingBudget;
	
	public Unit(int position, float budget) {
		this.totalBudget = budget;
		this.remainingBudget = budget;
		this.position = position;
	}
	
	public Set<Integer> getReachableTiles() {
		return reachableTiles;
	}
	
	public void resetBudget() {
		this.remainingBudget = totalBudget;
	}
	
	public void updateReachableTiles(List<Tuple<Integer, Float>> reachableTiles) {
		this.reachableTiles = new HashSet<Integer>();
		this.costs = new HashMap<Integer, Float>();
		
		for (Tuple<Integer, Float> tile : reachableTiles) {
			this.reachableTiles.add(tile.x);
			this.costs.put(tile.x, tile.y);
		}
	}
	
	public boolean isReachable(int tile) {
		return reachableTiles.contains(tile);
	}
	
	public boolean moveTo(int tile) {
		if (isReachable(tile)) {
			position = tile;
			remainingBudget -= costs.get(tile);
			
			return true;
		}
		
		return false;
	}
	
	public int getPosition() {
		return position;
	}
	
	
	public Faction getFaction() {
		return faction;
	}


	public float getTotalBudget() {
		return totalBudget;
	}


	public float getRemainingBudget() {
		return remainingBudget;
	}

}
