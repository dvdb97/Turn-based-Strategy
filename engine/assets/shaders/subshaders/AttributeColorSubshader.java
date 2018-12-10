package assets.shaders.subshaders;

public class AttributeColorSubshader extends Subshader {

	private static final String code = "vec4 color() {\r\n" +
											"return fs_in.fragColor;" +
										"}";
	
	public AttributeColorSubshader() {
		super(code);
	}

}
