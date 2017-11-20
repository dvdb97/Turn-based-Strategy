package rendering.shapes;

public class Rectangle extends Shape {
	
	private final float[] posData = {
		0.0f, 0.0f,
		1.0f, 0.0f,
		1.0f, 1.0f,
		0.0f, 1.0f
	};

	private final int[] indexData = {
		0, 1, 2,
		0, 3, 2
	};

	public Rectangle() {
		super();
		
		setPosData(posData);
		setIndexData(indexData);
	}

}
