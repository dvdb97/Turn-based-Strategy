package assets.meshes.geometry;

public class Rectangle {
	
	public static final int NUMBER_OF_VERTICES = 6;
	
	private Color color;
	
	private Vertex[] vertices;
	
	//----------------------- constructor -------------------------
	public Rectangle(Vertex bottomLeft, Vertex topRight, Color color) {
		
		this.color = color;
		
	}
	
	//-------------------- get & set -----------------------------
	public Color getColor() {
		return color;
	}
	
	//and so on...
	
}
