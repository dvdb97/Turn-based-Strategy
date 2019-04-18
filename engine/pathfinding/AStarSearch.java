package pathfinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


/**
 * 
 * Utility class for the A*-Algorithm that stores a potential path
 * 
 * @param <N> The type of the nodes of the graph.
 */
class Node<N> implements Comparable<Node<N>> {
	public N value;
	public Node<N> parent;
	
	public float total;
	public float heuristic;
	public float distance;
	
	public Node(N value, Float distance) {
		this.value = value;
		this.distance = distance;
	}
	
	public Node(N value, float total, float heuristic, float distance, Node<N> parent) {
		this.value = value;
		this.heuristic = heuristic;
		this.distance = distance;
		this.total = total;
		this.parent = parent;
	}

	@Override
	public int compareTo(Node<N> n) {
		if (total < n.total) {
			return -1;
		}
		
		if (total > n.total) {
			return 1;
		}
		
		return 0;
	}	
}



public class AStarSearch {	
		
	/**
	 * 
	 * Finds the shortest path between two nodes using the A*-Algorithm.
	 * 
	 * @param graph The graph that is the evironment for our algorithm.
	 * @param start The start node for our path.
	 * @param end The end node for our path.
	 * @return Returns the last Step of our path. It can be used to extract the path costs or the nodes on it.
	 */
	private static <N> Node<N> runAStarSearch(IGraph<N> graph, N start, N end) {
		//The priority queue that sorts the nodes by total distance.
		PriorityQueue<Node<N>> priorityQueue = new PriorityQueue<Node<N>>();
		
		//A map to keep track of all nodes that have been visited so far and their references.
		HashMap<N, Node<N>> visited = new HashMap<N, Node<N>>();
		
		//The first node.
		Node<N> startNode = new Node<N>(start, 0, 0, 0, null);
		
		//The step we are currently working with.
		Node<N> current = startNode;
		
		while (!current.value.equals(end)) {			
			//Mark the node as visited.
			visited.put(current.value, current);
			
			//Look up all sucessors to the current node.
			List<N> successors = graph.getSuccessors(current.value);
			
			for (N successor : successors) {
				//Conpute the total path costs for our next step.
				float distance = graph.getCosts(current.value, successor) + current.distance;
				//Compute the estimated remaining path costs to the end node.
				float heuristic = graph.getHeuristic(successor, end);
				//Compute the estimated total distance from the start node to the end using this path.
				float total = distance + heuristic;
				
				//If the successor has already been visited
				if (visited.containsKey(successor)) {
					Node<N> visitedNode = visited.get(successor);
					
					//If the new path to the visited node is shorter, override its properties with the new ones.
					if (total < visitedNode.total) {
						visitedNode.distance = distance;
						visitedNode.heuristic = heuristic;
						visitedNode.total = total;
						visitedNode.parent = current;
					}
				} else {
					priorityQueue.add(new Node<N>(successor, total, heuristic, distance, current));
				}
			}
			
			current = priorityQueue.poll();
		}
		
		return current;
	}
	
	
	/**
	 * 
	 * Finds the shortest path between two nodes using the A*-Algorithm.
	 * This method also takes the unit's properties into consideration.
	 * 
	 * @param graph The graph that is the evironment for our algorithm.
	 * @param start The start node for our path.
	 * @param end The end node for our path.
	 * @param unit The unit for which we are computing the path.
	 * @return Returns the last Step of our path. It can be used to extract the path costs or the nodes on it.
	 */
	public static <N, U> Node<N> runAStarSearch(ITraversable<N, U> graph, N start, N end, U unit) {
		//The priority queue that sorts the nodes by total distance.
		PriorityQueue<Node<N>> priorityQueue = new PriorityQueue<Node<N>>();
		
		//A map to keep track of all nodes that have been visited so far and their references.
		HashMap<N, Node<N>> visited = new HashMap<N, Node<N>>();
		
		//The first node.
		Node<N> startNode = new Node<N>(start, 0, 0, 0, null);
		
		//The step we are currently working with.
		Node<N> current = startNode;
		
		while (!current.value.equals(end)) {			
			//Mark the node as visited.
			visited.put(current.value, current);
			
			//Look up all sucessors to the current node.
			List<N> successors = graph.getSuccessors(current.value, unit);
			
			for (N successor : successors) {
				//Conpute the total path costs for our next step.
				float distance = graph.getCosts(current.value, successor, unit) + current.distance;
				//Compute the estimated remaining path costs to the end node.
				float heuristic = graph.getHeuristic(successor, end, unit);
				//Compute the estimated total distance from the start node to the end using this path.
				float total = distance + heuristic;
				
				//If the successor has already been visited
				if (visited.containsKey(successor)) {
					Node<N> visitedNode = visited.get(successor);
					
					//If the new path to the visited node is shorter, override its properties with the new ones.
					if (total < visitedNode.total) {
						visitedNode.distance = distance;
						visitedNode.heuristic = heuristic;
						visitedNode.total = total;
						visitedNode.parent = current;
					}
					
				} else {
					priorityQueue.add(new Node<N>(successor, total, heuristic, distance, current));
				}
			}
			
			current = priorityQueue.poll();
		}
		
		return current;
	}
	
	
	/**
	 * 
	 * Recursively convert the linked steps to a list of nodes.
	 * 
	 * @param s The step that is the result of the A*-Search.
	 * @return Returns a list containg all nodes on the path.
	 */
	private static <N> List<N> toPath(Node<N> s) {
		if (s.parent == null) {
			ArrayList<N> path = new ArrayList<N>();
			path.add(s.value);
			
			return path;
		}
		
		List<N> path = toPath(s.parent);
		path.add(s.value);
		
		return path;
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
	
	
	public static <N, U> List<N> getPath(ITraversable<N, U> graph, N start, N end, U unit) {
		return toPath(runAStarSearch(graph, start, end, unit));
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
	
	
	public static <N, U> float getPathCosts(ITraversable<N, U> graph, N start, N end, U unit) {
		return runAStarSearch(graph, start, end, unit).distance;
	}
	
	
	/**
	 * 
	 * Find all nodes that are reachable from the start using the given budget.
	 * 
	 * @param graph The graph to work with.
	 * @param start The node that is the start of our path search.
	 * @param budget The maximum distance that we can walk from the start node.
	 * @return Returns a set of nodes that is reachable from the start node.
	 */
	public static <N> Set<N> getReachableNodes(IGraph<N> graph, N start, float budget) {		
		//All nodes have already been visited by a path and the according path costs.
		HashMap<N, Float> visited = new HashMap<N, Float>();
		PriorityQueue<Node<N>> queue = new PriorityQueue<Node<N>>();
		queue.add(new Node<N>(start, 0f));
		
		Node<N> current = null;
		
		while (!queue.isEmpty()) {
			current = queue.poll();
			
			//Only look at this node if it hasn't been visited yet or we have found a shorter path to this node.
			if (!visited.containsKey(current.value) || visited.get(current.value) > current.total) {
				//Mark this node as visited or override the path costs for this node with an updated value.
				visited.put(current.value, current.total);
				
				//Add all successors of the current node to the priorityQueue.
				for (N node : graph.getSuccessors(current.value)) {
					float distance = current.total + graph.getCosts(current.value, node);
					
					if (distance < budget) {
						queue.add(new Node<N>(node, distance));
					}
				}
			}
		}
		
		return visited.keySet();
	}
	
	
	/**
	 * 
	 * Find all nodes that are reachable from the start using the given budget.
	 * This method also takes the unit's properties into consideration.
	 * 
	 * @param graph The graph to work with.
	 * @param start The node that is the start of our path search.
	 * @param budget The maximum distance that we can walk from the start node.
	 * @param unit The unit for which we are computing the path.
	 * @return Returns a set of nodes that is reachable from the start node.
	 */
	public static <N, U> Set<N> getReachableNodes(ITraversable<N, U> graph, N start, float budget, U unit) {
		//All nodes have already been visited by a path and the according path costs.
		HashMap<N, Float> visited = new HashMap<N, Float>();
		PriorityQueue<Node<N>> queue = new PriorityQueue<Node<N>>();
		
		Node<N> current = null;
		
		while (!queue.isEmpty()) {
			current = queue.poll();
			
			//Only look at this node if it hasn't been visited yet or we have found a shorter path to this node.
			if (!visited.containsKey(current.value) || visited.get(current.value) > current.total) {
				//Mark this node as visited or override the path costs for this node with an updated value.
				visited.put(current.value, current.total);
				
				//Add all successors of the current node to the priorityQueue.
				for (N node : graph.getSuccessors(current.value, unit)) {
					float distance = current.total + graph.getCosts(current.value, node, unit);
					queue.add(new Node<N>(node, distance));
				}
			}
		}
		
		return visited.keySet();
	}
	
}
