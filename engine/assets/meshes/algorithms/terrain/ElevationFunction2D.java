package assets.meshes.algorithms.terrain;

import assets.textures.Texture2D;

public interface ElevationFunction2D {
	
	public float getDepth(float x, float y);
	
	public Texture2D toTexture2D();

}
