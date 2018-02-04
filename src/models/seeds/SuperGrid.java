package models.seeds;

import math.vectors.Vector3f;
import utils.Const;

public class SuperGrid {
	
	
	//dimensions
	private int lengthInHexagons;
	private int widthInHexagons;
	
	private int xOffset;
	private int yOffset;
	
	private float triEdgeLength;
	private int elr;                //edge length relation = hexaong's edge length / triangle's edge length
	private float triangleAltitude;
	
	private int gridLength;
	private int gridWidth;

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
		
		gridLength = (lengthInHexagons*2 + 2)*elr + 2*xOffset;
		gridWidth  = (widthInHexagons*3/2 + 1)*elr + 1 + 2*yOffset;
		
		triangleAltitude = 0.5f * Const.SQRT3 * triEdgeLength;
		
	}

	private void processVectors() {
		
		positionVectors = new Vector3f[gridLength*gridWidth];
		
		for (int y=0; y<gridWidth; y++) {
			
			for (int x=0; x<gridLength; x++) {
				
				positionVectors[y*gridLength + x] = new Vector3f(x*triangleAltitude, (y+0.5f*(x%2))*triEdgeLength, elevation[x][y]);
				
			}
			
		}
		
	}
	
	private void processHexCenters() {
		
		for (int x=0; x<lengthInHexagons; x++) {
			for (int y=0; y<widthInHexagons; y++) {
				
				hexCenterIndices[y*gridLength + x] = (yOffset + elr + y*elr*3/2)*gridLength + (xOffset + elr + x*2*elr + (y%2)*elr  );
				
			}
		}
		
	}
	
	//************************** hexagons **************************************
	
	private Vector3f[] getHexBorder(int indexOfTheHexagon) {
		
		Vector3f[] border = new Vector3f[6];
		
		int[] hexBorderIndices = getHexBorderIndices(indexOfTheHexagon);
		
		for (int i=0; i<border.length; i++) {
			
			border[i] = positionVectors[hexBorderIndices[i]];
			
		}
		
		return border;
		
	}
	
	private int[] getHexBorderIndices(int indexOfTheHexagon) {
		
		int[] hexBorderIndices = new int[6];
		
		//TODO
		
		return hexBorderIndices;
		
	}
	
	//************************** get *******************************************
	
	public Vector3f[] getVectors() {
		
		return positionVectors;
		
	}
	
	public int[] getHexcenterIndices() {
		
		return hexCenterIndices;
		
	}
}
