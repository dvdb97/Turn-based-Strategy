package assets.meshes.algorithms.terrain;

import java.nio.ByteBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;

import assets.textures.Texture3D;

public class PerlinNoise3D implements ElevationFunction3D {
	
	private int size = 256;
	private int mask = size - 1;
	
	protected int[] permutations;
	
	private float[] gradX;
	private float[] gradY;
	private float[] gradZ;
	
	private Random random;
	
	public PerlinNoise3D() {
		permutations = new int[size];
		gradX = new float[size];
		gradY = new float[size];
		gradZ = new float[size];
		
		random = new Random();
		
		for (int i = 0; i < size; i++) {
			int other = random.nextInt() % (i + 1);
			
			if (i > other)
				permutations[i] = permutations[other];
			
			permutations[other] = i;
			
			double theta = 2f * Math.PI * i / size;
			double phi = 2f * Math.PI * i / size;
			
			gradX[i] = (float)Math.sin(theta) * (float)Math.cos(phi);
			gradY[i] = (float)Math.sin(theta) * (float)Math.sin(phi);
			gradZ[i] = (float)Math.cos(theta);
		}
	}
	
	private float falloff(float t) {
		t = Math.abs(t);
		
		return t >= 1 ? 0 : 1 - (3 - 2 * t) * t * t;
	}
	
	private float surflet(float x, float y, float z, float gradX, float gradY, float gradZ)	{
		return falloff(x) * falloff(y) * falloff(z) * (gradX * x + gradY * y + gradZ * z);
	}
	
	@Override
	public float getDepth(float x, float y, float z) {
		float result = 0f;
		
		int cellX = (int)Math.floor(x);
		int cellY = (int)Math.floor(y);
		int cellZ = (int)Math.floor(z);
		
		for (int gridZ = 0; gridZ <= cellZ; gridZ++) {
			for (int gridY = 0; gridY <= cellY; gridY++) {
				for (int gridX = 0; gridX < cellX; gridX++) {
					int hash = permutations[(((permutations[gridX & mask] + gridY) & mask) + gridZ) & mask];
					
					result += surflet(x - gridX, y - gridY, z - gridZ, gradX[hash], gradY[hash], gradZ[hash]);
				}
			}
		}
		
		return result;
	}

	@Override
	public Texture3D toTexture3D(float x, float xScale, float y, float yScale, float z, float zScale, int width,
			int height, int depth) {
		
		ByteBuffer pixels = BufferUtils.createByteBuffer(depth * height * width * Texture3D.RGBA_CHANNELS);
		
		for (int zi = 0; zi < depth; zi++) {
			for (int yi = 0; yi < height; yi++) {
				for (int xi = 0; xi < width; xi++) {
					byte value = (byte)(128 + getDepth(x + xi * xScale, y + yi * height, z + zi * zScale) * 128);
					
					pixels.put(value).put(value).put(value).put(value);
				}
			}
		}
		
		pixels.flip();
		
		Texture3D texture = new Texture3D();
		texture.setImageData(pixels, width, height, depth);
		
		return texture;
	}
	
	
}
