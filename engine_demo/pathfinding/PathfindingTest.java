package pathfinding;

import java.util.List;

import output.charts.graph.Graph;
import output.charts.graph.Node;

public class PathfindingTest {
	
	public static void main(String[] args) {
		run();
	}
	
	
	private static void run() {		
		Graph graph = new Graph();
		
		graph.addNode("A", -1f, 1f);
		graph.addNode("B", 0f, 1f);
		graph.addNode("C", 1f, 1f);
		graph.addNode("D", -1f, 0f);
		graph.addNode("E", 0f, 0f);
		graph.addNode("F", 1f, 0f);
		graph.addNode("G", -1f, -1f);
		graph.addNode("H", 0f, -1f);
		graph.addNode("I", 1f, -1f);
		
		graph.addEdge("A", "D");
		graph.addEdge("A", "E");
		graph.addEdge("B", "D");
		graph.addEdge("B", "E");
		graph.addEdge("B", "F");
		graph.addEdge("C", "E");
		graph.addEdge("D", "E");
		graph.addEdge("D", "G");
		graph.addEdge("E", "I");
		graph.addEdge("F", "G");
		graph.addEdge("F", "I");
		graph.addEdge("G", "H");
		
		List<Node> path = AStarSearch.getPath(graph, graph.getNode("H"), graph.getNode("I"));
		
		for (Node n : path) {
			System.out.println(n.label);
		}
	}

}
