package rendering.postprocessing;

import assets.IDeletable;
import assets.meshes.specialized.Quad;
import assets.shaders.ShaderLoader;
import assets.shaders.ShaderProgram;
import assets.textures.Texture2D;

public class PostprocessingLayer implements IDeletable {
	private ShaderProgram shader;
	private Quad quad;
	
	public PostprocessingLayer(ShaderProgram shader) {
		this.shader = shader;
		quad = new Quad();
		quad.useTextureColor();
	}
	
	
	public void runPostprocessing(Texture2D frame) {
		shader.bind();
		frame.bind();
		
		quad.render();
		
		frame.unbind();
		shader.unbind();
	}


	@Override
	public void delete() {
		shader.delete();
		quad.delete();
	}

}
