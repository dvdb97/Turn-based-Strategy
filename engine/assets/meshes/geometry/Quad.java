package assets.meshes.geometry;

public class Quad {
	
	private Vertex upper_left;
	
	private Vertex upper_right;
	
	private Vertex lower_left;
	
	private Vertex lower_right;

	public Quad(Vertex upper_left, Vertex upper_right, Vertex lower_left, Vertex lower_right) {
		super();
		this.upper_left = upper_left;
		this.upper_right = upper_right;
		this.lower_left = lower_left;
		this.lower_right = lower_right;
	}

	public Vertex getUpper_left() {
		return upper_left;
	}

	public Vertex getUpper_right() {
		return upper_right;
	}

	public Vertex getLower_left() {
		return lower_left;
	}

	public Vertex getLower_right() {
		return lower_right;
	}
	
	
	

}
