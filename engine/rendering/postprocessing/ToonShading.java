package rendering.postprocessing;

import assets.shaders.ShaderLoader;

public class ToonShading extends PostprocessingLayer {

	private static final String path = "Shaders/Postprocessing/";
	
	public ToonShading() {
		super(ShaderLoader.loadShader(path + "postprocessing.vert", path + "toon.frag"));
	}

}
