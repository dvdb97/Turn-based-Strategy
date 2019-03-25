package pathfinding;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 
 * Utility class for the A*-Algorithm that stores a potential path
 * 
 * @param <N> The type of the nodes of the graph.
 */
class Step<N> implements Comparable<Step<N>> {
	public N node;
	public Step<N> predecessor;
	public float distance;
	public float heuristic;
	
	public Step(N node, Step<N> predecessor, float distance, float heuristic) {
		this.node = node;
		this.predecessor = predecessor;
		this.distance = distance;
		this.heuristic = heuristic;
	}
	

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[" + node + ", " + distance + ", " + heuristic + "]";
	}
	
	
	public float getTotal() {
		return  distance + heuristic;
	}
	

	@Override
	public int compareTo(Step<N> o) {
		float total = getTotal();
		float oTotal = o.getTotal();
		
		if (total < oTotal) {
			return -1;
		}
		
		if (total > oTotal) {
			return 1;
		}
		
		return 0;
	}
}


public class AStarSearch<N> {	
	
	/**
	 * 
	 * Finds the shortest path between two nodes using the A*-Algorithm.
	 * 
	 * @param graph The graph that is the evironment for our algorithm.
	 * @param start The start node for our path.
	 * @param end The end node for our path.
	 * @return Returns the last Step of our path. It can be used to extract the path costs or the nodes on it.
	 */
	private static <N> Step<N> runAStarSearch(IGraph<N> graph, N start, N end) {
		PriorityQueue<Step<N>> priorityQueue = new PriorityQueue<Step<N>>();
		
		//The first step.
		Step<N> step = new Step<N>(start, null, 0f, 0f);
		priorityQueue.add(step);
		
		//The step we are currently working with.
		Step<N> current = null;
		
		while ((current = priorityQueue.poll()).node != end) {
			//Look up all sucessors to the current node.
			List<N> successors = graph.getSuccessors(current.node);
			
			for (N successor : successors) {
				//Conpute the total path costs for our next step.
				float distance = graph.getCosts(current.node, successor) + current.distance;
				//Compute the estimated remaining path costs to the end node.
				float heuristic = graph.getHeuristic(successor, end);
				
				priorityQueue.add(new Step<N>(successor, current, distance, heuristic));
			}
		}
		
		return current;
	}
	
	
	/**
	 * 
	 * Searches the shortest path from a start N to an end N with end being
	 * the type of the nodes of the given graph.
	 * 
	 * @param graph The graph to search the path in.
	 * @param start The start node of our path.
	 * @param end The end node of our path.
	 * @return Returns a list of nodes representing the path.
	 */
	public static <N> List<N> getPath(IGraph<N> graph, N start, N end) {
		return toPath(runAStarSearch(graph, start, end));
	}
	
	
	/**
	 * 
	 * Searches the shortest path from a start N to an end N with end being
	 * the type of the nodes of the given graph. 
	 * 
	 * @param graph The graph to search the path in.
	 * @param start The start node of our path.
	 * @param end The end node of our path.
	 * @return Returns the path costs for the shortest path between start and end.
	 */
	public static <N> float getPathCosts(IGraph<N> graph, N start, N end) {
		return runAStarSearch(graph, start, end).distance;
	}
	
	
	/**
	 * 
	 * Recursively convert the linked steps to a list of nodes.
	 * 
	 * @param s The step that is the result of the A*-Search.
	 * @return Returns a list containg all nodes on the path.
	 */
	private static <N> List<N> toPath(Step<N> s) {
		if (s.predecessor == null) {
			ArrayList<N> path = new ArrayList<N>();
			path.add(s.node);
			
			return path;
		}
		
		List<N> path = toPath(s.predecessor);
		path.add(s.node);
		
		return path;
	}
	
}
