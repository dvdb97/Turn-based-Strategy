package pathfinding;

public interface INode {
	
	/**
	 * 
	 * @return Return all the successors to this node.
	 */
	public INode[] getSuccessors();
	
	/**
	 * 
	 * @param target A node that is a neighbor of this node.
	 * @return Return the distance between this node and the target node.
	 */
	public float getDistance(INode target);
	
	/**
	 * 
	 * @param target The node we want to reach from this node.
	 * @return Return a value that is an estimate of the path costs from the node to the target
	 */
	public float getHeuristic(INode target);	

}
