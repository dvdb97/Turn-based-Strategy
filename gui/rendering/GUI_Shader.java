package rendering;

import assets.meshes.geometry.Color;
import rendering.shaders.ShaderProgram;

public class GUI_Shader extends ShaderProgram {

	public GUI_Shader() {
		super("Shaders/GUI/GUI.vert", "Shaders/GUI/GUI.frag");
	}
	
	
	public void setColor(Color color) {
		this.setUniform3fv("color", color.toArray());
	}

}
