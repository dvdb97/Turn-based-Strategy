package pathfinding;

import java.util.List;

public interface ITraversable<E, U> extends IGraph<E> {
	
	/**
	 * 
	 * Returns all the neighbors of the given node that are
	 * accessible for the given unit.
	 * 
	 * When taking the unit itself into consideration we can leave
	 * out nodes that the unit isn't allowed to access.
	 * 
	 * @param node The node whose successor node are needed.
	 * @param unit The unit for which we are computing a path.
	 * @return Returns a list of successor nodes.
	 */
	public List<E> getSuccessors(E node, U unit);
	
	
	/**
	 * 
	 * Returns the path costs for the unit to move from the start node
	 * to the end node.
	 * 
	 * When taking the unit itself into consideration we for example can give
	 * mounted units a penalty when moving through mountains.
	 * 
	 * @param start The start node.
	 * @param end The end node.
	 * @param unit The unit for which we are computing a path.
	 * @return Returns the costs for the unit to move from start to end.
	 */
	public float getCosts(E start, E end, U unit);
	
	
	/**
	 * 
	 * Returns a estimate for the remaining path costs for the unit to move from start to end.
	 * 
	 * @param start The start node.
	 * @param end The end node.
	 * @param unit The unit for which we are computing a path.
	 * @return Returns the heuristic costs for the unit to move from start to end.
	 */
	public float getHeuristic(E start, E end, U unit);
}
