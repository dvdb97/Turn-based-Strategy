package rendering;

import assets.meshes.prefabs.Axis;
import assets.shaders.ShaderLoader;
import assets.shaders.ShaderProgram;

public class AxisRenderer {
	
	private static final String path = "Shaders/StandardShaders/colorAttrib";
	
	private static Axis axis;
	
	private static ShaderProgram shader;
	
	private static boolean init = false;
	
	
	public static void init() {
		if (init) return;
		
		axis = new Axis();		
		shader = ShaderLoader.loadShader(path + ".vert", path + ".frag");
		init = true;
	}
	
}
