package output.charts.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import pathfinding.IGraph;

import static math.MathUtils.*;

class Edge implements Comparable<Edge> {
	public Node a;
	public Node b;
	public Integer hash;
	
	public Edge(Node a, Node b) {
		this.a = a;
		this.b = b;
		hash = (a.label + b.label).hashCode();
	}

	@Override
	public int compareTo(Edge o) {
		return hash.compareTo(o.hash);
	}
}


public class Graph implements IGraph<Node> {

	private TreeSet<Node> nodes;
	private TreeSet<Edge> edges;
	
	private HashMap<String, Node> nodeLookUp;
	private HashMap<Node, TreeSet<Node>> lookUpTable;
	
	public Graph() {
		nodes = new TreeSet<Node>();
		edges = new TreeSet<Edge>();
		
		nodeLookUp = new HashMap<String, Node>();
		lookUpTable = new HashMap<Node, TreeSet<Node>>();
	}
	
	
	@Override
	public List<Node> getSuccessors(Node node) {
		List<Node> successors = new LinkedList<Node>();
		
		for (Node n : lookUpTable.get(node)) {
			successors.add(n);
		}
		
		return successors;
	}
	

	@Override
	public float getCosts(Node start, Node end) {
		return sqrt(square(end.x - start.x) + square(end.y - start.y));
	}
	

	@Override
	public float getHeuristic(Node start, Node end) {
		return sqrt(square(end.x - start.x) + square(end.y - start.y));
	}
	
	
	public void addNode(String label, float x, float y) {
		Node node = new Node(x, y, label);
		
		nodeLookUp.put(label, node);
		nodes.add(node);
		lookUpTable.put(node, new TreeSet<Node>());
	}
	
	
	public void removeNode(String label) {
		Node node = nodeLookUp.get(label);
		
		TreeSet<Node> neighbors = lookUpTable.get(node);
		
		for (Node neighbor : neighbors) {
			lookUpTable.get(neighbor).remove(node);			
		}
		
		nodeLookUp.remove(label);
		nodes.remove(node);
		lookUpTable.remove(node);
	}
	
	
	public void addEdge(String nodeA, String nodeB) {
		Node a = nodeLookUp.get(nodeA);
		Node b = nodeLookUp.get(nodeB);
		
		lookUpTable.get(a).add(b);
		lookUpTable.get(b).add(a);
		edges.add(new Edge(a, b));
	}
	
	
	public void removeEdge(String nodeA, String nodeB) {
		Node a = nodeLookUp.get(nodeA);
		Node b = nodeLookUp.get(nodeB);
		
		lookUpTable.get(a).remove(b);
		lookUpTable.get(b).remove(a);
		
		Edge e0 = new Edge(a, b);
		Edge e1 = new Edge(b, a);
		
		if (edges.contains(e0)) {
			edges.remove(e0);
		} else {
			edges.remove(e1);
		}
	}
	
	
	public Node getNode(String label) {
		return nodeLookUp.get(label);
	}
	
}
