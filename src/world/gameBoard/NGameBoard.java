package world.gameBoard;

import java.util.List;

import pathfinding.ITraversable;
import world.units.Unit;

/**
 * 
 * @author Dario
 * 
 * Test for a new GameBoard.
 *
 */
public class NGameBoard implements ITraversable<Integer, Unit> {

	private Tile[] tiles;
	
	public NGameBoard(int width, int height) {
		tiles = new Tile[height * width];
	}
	

	@Override
	public List<Integer> getSuccessors(Integer node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getCosts(Integer start, Integer end) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeuristic(Integer start, Integer end) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getSuccessors(Integer node, Unit unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getCosts(Integer start, Integer end, Unit unit) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeuristic(Integer start, Integer end, Unit unit) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
