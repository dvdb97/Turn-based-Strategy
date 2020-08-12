package assets.meshes.algorithms.terrain;

import assets.textures.Texture1D;

public interface ElevationFunction1D {
	
	public float getDepth(float x);
	
	public Texture1D toTexture1D();

}
