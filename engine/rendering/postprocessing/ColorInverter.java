package rendering.postprocessing;

import assets.shaders.ShaderLoader;

public class ColorInverter extends PostprocessingLayer {

	public ColorInverter() {
		super(ShaderLoader.loadShader("Shaders/Postprocessing/postprocessing.vert", "Shaders/Postprocessing/inverse.frag"));
	}

}
