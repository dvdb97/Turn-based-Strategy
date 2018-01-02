package rendering.shaders;

import graphics.shaders.ShaderManager;
import utils.FileUtils;

public class ShaderGenerator {
	
	private CodeBuffer code;
	
	public ShaderGenerator(int version) {
		
		code = new CodeBuffer();
		
		code.add("#version" + version + "core");
		
	}
	
	
	public void addInputVariable(String type, int layoutPos, String name) {
		
		code.add("layout(location = " + layoutPos + ") in " + type + " " + name + ";");
		
	}
	
	
	public void addUniformVariable(String type, String name) {
		
		code.add("uniform " + type + " " + name + ";");
		
	}
	
	
	public void addOutputVariable(String type, String name) {
		
		code.add("uniform " + type + " " + name + ";");
		
	}
	
	
	public void addMainFunction(String mainFunction) {
		
		code.add(mainFunction);
		
	}
	
	
	public String getShaderSourceCode() {
		
		return code.toString();
		
	}


	public static void main(String[] args) {
		FileUtils.loadShaderSourceCode("Shaders/GUI/Font/FontShader.frag");
	}

}
