package world.gameBoard;

import static java.lang.Math.abs;

import java.util.List;

import pathfinding.ITraversable;
import world.HexGridUtils;
import world.units.Unit;


public class NGameBoard implements ITraversable<Integer, Unit> {

	private NTile[] tiles;
	private int width, length;
	
	
	public NGameBoard(NTile[] tiles, int width, int length) {
		setTiles(tiles, width, length);
	}
	
	
	//------------------------------- Pathfinding -------------------------------
	

	@Override
	public List<Integer> getSuccessors(Integer node) {
		return HexGridUtils.getNeighbors(width, length, node, 0);
	}
	

	@Override
	public float getCosts(Integer start, Integer end) {
		return 0.5f * tiles[start].getPathCosts() * 0.5f * tiles[end].getPathCosts();
	}

	
	@Override
	public float getHeuristic(Integer start, Integer end) {
		int startX = start % length;
		int startY = start / length;
		
		int endX = end % length;
		int endY = end / length;
		
		return (float)abs(endX - startX) + (float)abs(endY - startY);
	}
	

	@Override
	public List<Integer> getSuccessors(Integer node, Unit unit) {
		List<Integer> successors = getSuccessors(node);
		
		for (int i : successors) {
			//Remove the tile from the list of the unit may not access it.
			if (!tiles[i].isPassable(unit)) {
				successors.remove(i);
			}
		}
		
		return successors;
	}
	

	@Override
	public float getCosts(Integer start, Integer end, Unit unit) {
		return 0.5f * tiles[start].getPathCosts(unit) + 0.5f * tiles[end].getPathCosts(unit);
	}
	

	@Override
	public float getHeuristic(Integer start, Integer end, Unit unit) {
		return getHeuristic(start, end);
	}
	
	
	//------------------------------- Getters & Setters -------------------------------
	
	
	public void setTiles(NTile[] tiles, int width, int length) {
		this.tiles = tiles;
		this.width = width;
		this.length = length;
	}


	public NTile[] getTiles() {
		return tiles;
	}


	public int getWidth() {
		return width;
	}


	public int getLength() {
		return length;
	}
	
}
