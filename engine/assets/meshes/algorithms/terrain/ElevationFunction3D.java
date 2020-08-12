package assets.meshes.algorithms.terrain;

import assets.textures.Texture3D;

public interface ElevationFunction3D {
	
	public float getDepth(float x, float y, float z);
	
	public Texture3D toTexture3D(float x, float xScale, float y, float yScale, float z, float zScale, 
								 int width, int height, int depth);

}
