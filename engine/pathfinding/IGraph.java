package pathfinding;

import java.util.List;

public interface IGraph<E> {
	
	/**
	 * 
	 * Returns all the neighbors of the given node.
	 * 
	 * @param node The node whose successor node are needed.
	 * @return Returns a list of successor nodes.
	 */
	public List<E> getSuccessors(E node);
	
	
	/**
	 * 
	 * Returns the path costs to move from the start node
	 * to the end node.
	 * 
	 * @param start The start node.
	 * @param end The end node.
	 * @return Returns the costs for the unit to move from start to end.
	 */
	public float getCosts(E start, E end);
	
	
	/**
	 * 
	 * Returns an estimate for the remaining path costs to move from start to end.
	 * 
	 * @param start The start node.
	 * @param end The end node.
	 * @return Returns the heuristic costs for the unit to move from start to end.
	 */
	public float getHeuristic(E start, E end);

}
