package rendering.shapes;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class Shape {
	
	public abstract FloatBuffer getPositionData();
	
	public abstract FloatBuffer getColorData();
	
	public abstract FloatBuffer getTexPosData();
	
	public abstract IntBuffer getIndexData();

}
