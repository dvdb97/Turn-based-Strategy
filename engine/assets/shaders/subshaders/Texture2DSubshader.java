package assets.shaders.subshaders;

public class Texture2DSubshader extends Subshader {

	private static final String code =  "uniform sampler2D tex;\r\n" + 
										"\r\n" + 
										"vec4 color() {\r\n" + 
										"	return texture2D(tex, fs_in.fragTexPos.xy);\r\n" + 
										"}";
	
	public Texture2DSubshader() {
		super(code);
	}

}
