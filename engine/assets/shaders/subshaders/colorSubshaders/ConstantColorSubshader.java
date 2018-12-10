package assets.shaders.subshaders.colorSubshaders;

import assets.meshes.geometry.Color;

public class ConstantColorSubshader extends ColorSubshader {

	private static final String code = "vec4 color() {\r\n" + 
									   "	return rgba;\r\n" + 
									   "}";
	
	public ConstantColorSubshader(Color color) {
		super(addColor(color));
		// TODO Auto-generated constructor stub
	}
	
	
	private static String addColor(Color color) {
		return code.replace("rgba", "vec4(" + color.getA() + ", " + color.getB() + ", " + color.getC() + ", " + color.getD() + ")");
	}
	
}
