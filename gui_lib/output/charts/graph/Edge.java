package output.charts.graph;

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