package assets.meshes.algorithms.terrain;

import assets.textures.utils.Image;
import assets.textures.utils.ImageLoader;

public class Heightmap implements ElevationFunction {
	
	private int width, height;
	
	private float[][] hmap;
	
	public Heightmap(String filePath) {
		loadHeightmap(filePath);		
	}
	
	
	public Heightmap(int width, int height) {
		hmap = new float[height][width];
		
		this.width = width;
		this.height = height;
	}
	
	
	private void loadHeightmap(String path) {
		Image image = ImageLoader.loadImageRGBA(path);
		byte[] pixelData = image.getImageDataAsByteBuffer().array();
		
		this.width = image.getWidth();
		this.height = image.getHeight();
		
		hmap = new float[height][width];
		
		for (int i = 0; i < height * width; ++i) {
			//Take every first byte of each pixel and transform it to a value in the range of -1f and 1f.
			hmap[i / width][i % width] = (float)Byte.toUnsignedInt(pixelData[i * 4]) / 128f - 1f;
		}
	}
	
	
	@Override
	public float getDepth(int x, int y) {
		if (y < 0 || y >= hmap.length)
			return 0.0f;
		
		if (x < 0 || x >= hmap[y].length)
			return 0.0f;
		
		return hmap[height - 1 - y][x];
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

}
