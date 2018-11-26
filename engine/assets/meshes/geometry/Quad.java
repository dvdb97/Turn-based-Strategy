package assets.meshes.geometry;

public class Quad {
	
	private VertexLegacy upper_left;
	
	private VertexLegacy upper_right;
	
	private VertexLegacy lower_left;
	
	private VertexLegacy lower_right;

	public Quad(VertexLegacy upper_left, VertexLegacy upper_right, VertexLegacy lower_left, VertexLegacy lower_right) {
		super();
		this.upper_left = upper_left;
		this.upper_right = upper_right;
		this.lower_left = lower_left;
		this.lower_right = lower_right;
	}

	public VertexLegacy getUpper_left() {
		return upper_left;
	}

	public VertexLegacy getUpper_right() {
		return upper_right;
	}

	public VertexLegacy getLower_left() {
		return lower_left;
	}

	public VertexLegacy getLower_right() {
		return lower_right;
	}
	
	
	

}
