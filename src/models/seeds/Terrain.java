package models.seeds;

import models.seeds.noise.TrigonalPerlinNoise;

//TODO: rename, maybe call it seed, elevationMap, elevationSeed,
//			don't forget to integrate vegetation
public class Terrain implements Generator {
	
	/************************************************************
	 * 															*
	 * 						variables							*
	 * 															*
	 ************************************************************/
	//dimensions
	private int length, width;
	
	private float[][] elevation;
	
	private TrigonalPerlinNoise elePerlin;
	private TrigonalPerlinNoise expPerlin;
	
	
	/************************************************************
	 * 															*
	 * 						constructor							*
	 * 															*
	 ************************************************************/
	public Terrain(int length, int width) {
		
		this.length  = length;
		this.width = width;
		
		int maxOctave = 7;
		int extra = (int)Math.pow(2, maxOctave);
		
		elePerlin = new TrigonalPerlinNoise(length+extra, width+extra, 0, 7);
		expPerlin = new TrigonalPerlinNoise(length+extra, width+extra, 5, 7);
		
		elevation = new float[length][width];
		
		for (int x=0; x<length; x++) {
			for (int y=0; y<width; y++) {
				
				elevation[x][y] = elePerlin.getValue(x, y);
				
			}
		}
		
		seaLevelToZero();
		
		calculateEvelation();
		
	}
	
	/************************************************************
	 * 															*
	 * 						prime methods						*
	 * 															*
	 ************************************************************/
	private void calculateEvelation() {
		
		for (int x=0; x<length; x++) {
			for (int y=0; y<width; y++) {
				
				elevation[x][y] = -(float)Math.log( 1/(elevation[x][y]+0.5) - 1 );
				
			}
		}
		
	}
	
	private void seaLevelToZero() {
		
		float averageElevation = 0;
		
		for (int x=0; x<length; x++) {
			for (int y=0; y<width; y++) {
				
				averageElevation += elevation[x][y];
				
			}
		}
		
		//because the "0.9f*" it's not the average height, but it looks better
		averageElevation = 0.9f*averageElevation/length/width;
		
		for (int x=0; x<length; x++) {
			for (int y=0; y<width; y++) {
				
				elevation[x][y] -= averageElevation;
				
			}
		}
		
	}
	
	/************************************************************
	 * 															*
	 * 						getter								*
	 * 															*
	 ************************************************************/
	public float getElevation(int x, int y) {
		
		return elevation[x][y];
		
	}
	
	//TODO: maybe temp
	public float[][] getElevationArray() {
		
		float[][] r = new float[length][width];
		
		for (int x=0; x<length; x++) {
			for (int y=0; y<width; y++) {
				r[x][y] = elevation[x][y];
			}
		}
		
		return r;
		
	}
	
	public int getLength() {
		return length;
	}
	public int getWidth() {
		return width;
	}
	
	public float getSeaLevel() {
		return 0.0f;
	}
	
}
