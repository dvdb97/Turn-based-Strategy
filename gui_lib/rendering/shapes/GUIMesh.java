package rendering.shapes;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GUIMesh {
	
	private FloatBuffer posData;
	
	private FloatBuffer colorData;
	
	private FloatBuffer texPosData;
	
	private IntBuffer indexData;

	public GUIMesh(FloatBuffer posData, FloatBuffer colorData, FloatBuffer texPosData, IntBuffer indexData) {
		super();
		
		this.posData = posData;
		this.colorData = colorData;
		this.texPosData = texPosData;
		this.indexData = indexData;
		
	}
	
	

}
