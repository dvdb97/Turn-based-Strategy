package assets.shaders.subshaders;

import assets.meshes.geometry.Color;

public class ConstantColorSubshader extends Subshader {

	private static final String code = "vec4 color() {\r\n" + 
									   "	return rgba;\r\n" + 
									   "}";
	
	public ConstantColorSubshader(Color color) {
		super(addColor(color));
		// TODO Auto-generated constructor stub
	}
	
	
	private static String addColor(Color color) {
		return code.replace("rgba", "vec4(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ", " + color.getAlpha() + ")");
	}
	
}
