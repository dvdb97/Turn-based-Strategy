package assets.meshes.algorithms.terrain;

import assets.textures.Texture2D;
import assets.textures.utils.Image;
import assets.textures.utils.ImageLoader;
import math.MathUtils;

public class Heightmap implements ElevationFunction2D {
	
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
	
	private float getValue(int y, int x) {
		if (y < 0 || y >= getHeight()) {
			return 0f;
		}
		
		if (x < 0 || x >= getWidth()) {
			return 0f;
		}
		
		return hmap[y][x];
	}

	@Override
	public float getDepth(float x, float y) {
		int x0 = MathUtils.floor(x * (getWidth() - 1));
		int y0 = MathUtils.floor(y * (getHeight() - 1));
		
		int x1 = MathUtils.ceil(x * (getWidth() - 1));
		int y1 = MathUtils.ceil(y * (getHeight() - 1));
		
		float tx = x - x0;
		float ty = y - y0;
		
		return MathUtils.blerp(tx, ty, getValue(y0, x0), getValue(y0, x1), getValue(y1, x0), getValue(y1, x1));
	}

	@Override
	public Texture2D toTexture2D() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
