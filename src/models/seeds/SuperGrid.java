package models.seeds;

import math.vectors.Vector3f;
import utils.Const;

public class SuperGrid {
	
	
	//dimensions
	private int lengthInHexagons;
	private int widthInHexagons;
	
	private int xOffset;				//must be even
	private int yOffset;				//must be even
	
	private float triEdgeLength;
	private int elr;                	//must be a power of 2 and > 1          //edge length relation = hexaong's edge length / triangle's edge length
	private float triangleAltitude;
	
	private int lengthInVectors;
	private int widthInVectors;

	//data
	private float[][] elevation;
	
	private Vector3f[] positionVectors;
	
	private int[] hexCenterIndices;
	
	
	
	//*************************** constructor **********************************
	
	public SuperGrid(int lengthInHexagons, int widthInHexagons, int xOffset, int yOffset, float triEdgeLength, int elr, float[][] elevation) {
		
		this.lengthInHexagons = lengthInHexagons;
		this.widthInHexagons = widthInHexagons;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.triEdgeLength = triEdgeLength;
		this.elr = elr;
		this.elevation = elevation;
		
		calculations();
		processVectors();
		processHexCenters();
		
	}
	
	
	
	//*************************** prime methods ********************************
	
	private void calculations() {
		
		lengthInVectors = (lengthInHexagons*2 + 2)*elr + 2*xOffset;
		widthInVectors  = (widthInHexagons*3/2 + 1)*elr + 1 + 2*yOffset;
		
		triangleAltitude = 0.5f * Const.SQRT3 * triEdgeLength;
		
	}

	private void processVectors() {
		
		positionVectors = new Vector3f[lengthInVectors*widthInVectors];
		
		for (int y=0; y<widthInVectors; y++) {
			
			for (int x=0; x<lengthInVectors; x++) {
				
				positionVectors[y*lengthInVectors + x] = new Vector3f(x*triangleAltitude, (y+0.5f*(x%2))*triEdgeLength, elevation[x][y]);
				
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
	
	//************************** get *******************************************
	
	public int getLengthInHexagons() {
		return lengthInHexagons;
	}
	
	public int getWidthInHexagons() {
		return widthInHexagons;
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



	public Vector3f[] getVectors() {
		
		Vector3f[] copyOfPositionVectors = new Vector3f[positionVectors.length];
		for (int v=0; v<positionVectors.length; v++) {
			copyOfPositionVectors[v] = positionVectors[v].copyOf();
		}
		return copyOfPositionVectors;
		
	}
	
	public int[] getHexcenterIndices() {
		
		return hexCenterIndices;
		
	}
}
