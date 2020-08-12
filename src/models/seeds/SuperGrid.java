package models.seeds;

import java.util.Random;

import math.vectors.Vector3f;
import utils.Const;

public class SuperGrid {
	
	private static final float Z_SHIFT = 0.02f;
	
	//dimensions
	private int lengthInHexagons;
	private int widthInHexagons;
	
	private int xOffset;				//must be even
	private int yOffset;				//must be even
	
	private float width;
	private float length;
	
	private float triEdgeLength;
	private int elr;                	//must be a power of 2 and > 1          //edge length relation = hexaong's edge length / triangle's edge length
	private float triangleAltitude;
	
	private int lengthInVectors;
	private int widthInVectors;

	//data
	private float[][] elevation;
	
	private Vector3f[] positionVectors;
	private Vector3f[] texCoords;
	
	private int[] hexCenterIndices;
	
	private Random random;
	
	
	//*************************** constructor **********************************
	
	public SuperGrid(int lengthInHexagons, int widthInHexagons, int xOffset, int yOffset, float triEdgeLength, int elr, float[][] elevation) {
		this.lengthInHexagons = lengthInHexagons;
		this.widthInHexagons = widthInHexagons;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.triEdgeLength = triEdgeLength;
		this.elr = elr;
		this.elevation = elevation;
		this.random = new Random();
		
		calculations();
		processVectors();
		processHexCenters();
	}
	
	
	//*************************** prime methods ********************************
	
	
	private void calculations() {
		lengthInVectors = (lengthInHexagons*2 + 2)*elr + 2*xOffset;
		widthInVectors  = (widthInHexagons*3/2 + 1)*elr + 1 + 2*yOffset;
		
		triangleAltitude = 0.5f * Const.SQRT3 * triEdgeLength;
		
		width = widthInVectors * triangleAltitude;
		length = (lengthInVectors + 0.5f) * triEdgeLength;
	}
	
	
	private Vector3f toTexCoords(float x, float y, float z) {		
		return new Vector3f(x, y, z * (1f + (float)random.nextGaussian() * 0.25f));		
	}
	

	private void processVectors() {
		positionVectors = new Vector3f[lengthInVectors*widthInVectors];
		texCoords = new Vector3f[lengthInVectors*widthInVectors];
		
		for (int y=0; y<widthInVectors; y++) {
			for (int x=0; x<lengthInVectors; x++) {
				float xCoord = x*triangleAltitude;
				float yCoord = (y+0.5f*(x%2))*triEdgeLength;
				float zCoord = elevation[x][y];
				
				positionVectors[y*lengthInVectors + x] = new Vector3f(xCoord, yCoord, zCoord);
				texCoords[y*lengthInVectors + x] = toTexCoords(xCoord, yCoord, zCoord);
			}
		}
	}
	
	private void processHexCenters() {
		hexCenterIndices = new int[lengthInHexagons*widthInHexagons];
		
		for (int x=0; x<lengthInHexagons; x++) {
			for (int y=0; y<widthInHexagons; y++) {
				hexCenterIndices[y*lengthInHexagons + x] = (yOffset + elr + y*elr*3/2)*lengthInVectors + (xOffset + elr + x*2*elr + (y%2)*elr  );
			}
		}
	}
	
	//************************** hexagons **************************************
	
	public Vector3f getHexCenter(int indexOfTheHexagon) {
		return positionVectors[hexCenterIndices[indexOfTheHexagon]].copyOf();
	}
	
	
	public Vector3f[] getHexBorder(int indexOfTheHexagon) {
		Vector3f[] border = new Vector3f[6];
		
		int[] hexBorderIndices = getHexBorderIndices(indexOfTheHexagon);
		
		for (int i=0; i<border.length; i++) {
			border[i] = positionVectors[hexBorderIndices[i]].copyOf();
		}
		
		return border;	
	}
	
	/**
	 *  starting at the bottom and then counterclockwise
	 * @param indexOfTheHexagon requested hexagons's index in "int[] hexcenterIndices"
	 * @return an array containing the indices of the vertices of the border of the hexagons
	 */
	private int[] getHexBorderIndices(int indexOfTheHexagon) {
		
		int[] hexBorderIndices = new int[6];
		
		int center = hexCenterIndices[indexOfTheHexagon];
		
		hexBorderIndices[0] = center - elr   * lengthInVectors;
		hexBorderIndices[1] = center - elr/2 * lengthInVectors + elr;
		hexBorderIndices[2] = center + elr/2 * lengthInVectors + elr;
		hexBorderIndices[3] = center + elr   * lengthInVectors;
		hexBorderIndices[4] = center + elr/2 * lengthInVectors - elr;
		hexBorderIndices[5] = center - elr/2 * lengthInVectors - elr;
		
		return hexBorderIndices;
		
	}
	
	//************************** util ******************************************
	
	public static void adjustToTerrainAndSea(Vector3f[] vertices) {
		
		Vector3f zShift = new Vector3f(0, 0, Z_SHIFT);
		
		for (int v=0; v<vertices.length; v++) {
			
			if (vertices[v].getC() < 0) {
				vertices[v].setC(0);
			}
			
			vertices[v].plusEQ(zShift);
			
		}
		
	}
	
	//************************** get *******************************************
	
	public int getLengthInHexagons() {
		return lengthInHexagons;
	}
	
	public int getWidthInHexagons() {
		return widthInHexagons;
	}
	
	public int getTotalHexagons() {
		return positionVectors.length;
	}
	
	/**
	 * @return the lengthInVectors
	 */
	public int getLengthInVectors() {
		return lengthInVectors;
	}
	
	
	/**
	 * @return the widthInVectors
	 */
	public int getWidthInVectors() {
		return widthInVectors;
	}
	
	public float getTriEdgeLength() {
		return triEdgeLength;
	}
	
	public float getTriangleAltitude() {
		return triangleAltitude;
	}
	
	private Vector3f[] getCopy(Vector3f[] array) {
		Vector3f[] copy = new Vector3f[array.length];
		
		for (int i = 0; i < array.length; i++) {
			copy[i] = array[i];
		}
		
		return copy;
	}
	
	public Vector3f[] getPositions() {
		return getCopy(positionVectors);
	}
	
	
	public Vector3f getPosition(int i) {
		return positionVectors[i];
	}
	
	
	public Vector3f[] getTexCoords() {
		return getCopy(texCoords);
	}
	
	
	public int[] getHexcenterIndices() {
		return hexCenterIndices;
	}
}
