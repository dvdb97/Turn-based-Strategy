package output.charts.graph;

public class Node implements Comparable<Node> {
	public String label;
	public float x, y;
	
	public Node(float x, float y, String label) {
		this.label = label;
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Node o) {
		return label.compareTo(o.label);
	}

	@Override
	public String toString() {
		return label;
	}
	
	
}