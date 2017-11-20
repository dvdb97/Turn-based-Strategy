package utils;

import package1.GameState;

//A class that contains static functions for creating various geometric shapes
public class GeometryUtils {
	
	//data per vertex that is stored in an one-dimensional array
	public static int DIMENSIONS_PER_VERTEX = 3;
	//TODO: private
	public static float pxlW, pxlH;
	
	public static void setPixelWH(int pixelWidth, int pixelHeight) {
		
		pxlW = pixelWidth;
		pxlH = pixelHeight;
		
	}
	
	//The index array needed to display a hexagon
	public static byte[] indexArray = {
		0, 1, 2,
		0, 2, 3,
		0, 3, 4,
		0, 4, 5,
		0, 5, 6,
		0, 6, 1
	};

	//Creates an array of floating point vertex data that can be used to display a polygon
	//3 coordinates per vertex; the first vertex is the center of the polygon
	//Comment: Maybe change it so that it can create polygon of choice
	public static float[] createHexagon(float radius, float shiftX, float shiftY) {
		
		float[] outputArray = new float[(6 + 1) * DIMENSIONS_PER_VERTEX];
		
		float z = 1.0f;
		
		//Sets the the coordinates of the first vertex to the center point of our polygon
		outputArray[0] = 0.0f + shiftX;
		outputArray[1] = 0.0f + shiftY;
		outputArray[2] = 1.6f;
		
		for (double i = 1; i < outputArray.length / DIMENSIONS_PER_VERTEX; i++) {
			float x = (float)(Math.sin(i * (Math.PI / 3)) * radius + shiftX);
			float y = (float)(Math.cos(i * (Math.PI / 3)) * radius + shiftY);
			
			outputArray[(int) (i * DIMENSIONS_PER_VERTEX)] = x;
			outputArray[(int)((i * DIMENSIONS_PER_VERTEX) + 1)] = y;
			outputArray[(int)((i * DIMENSIONS_PER_VERTEX) + 2)] = z;
		}
		
		return outputArray;
	}
	
	public static float[] createHexagonWithoutCenterVertex(float radius, float shiftX, float shiftY) {
		
		float[] outputArray = new float[6 * DIMENSIONS_PER_VERTEX];
		
		float z = 1.0f;
				
		for (double i = 0; i < outputArray.length / DIMENSIONS_PER_VERTEX; i++) {
			float x = (float)(Math.sin(i * (Math.PI / 3)) * radius + shiftX);
			float y = (float)(Math.cos(i * (Math.PI / 3)) * radius + shiftY);
			
			outputArray[(int)((i * DIMENSIONS_PER_VERTEX) + 0)] = x;
			outputArray[(int)((i * DIMENSIONS_PER_VERTEX) + 1)] = y;
			outputArray[(int)((i * DIMENSIONS_PER_VERTEX) + 2)] = z;
		}
		
		return outputArray;
	}
	
	
	/*Creates the same model as createHexagonWithoutCenterVertex() does but makes sure that every line
	 * has its own vertices
	 * 
	 * The specific element Array:
	 */
	public static byte[] borderMapHexagonData = {
		1, 2,
		3, 4,
		5, 6,
		7, 8,
		9, 10,
		11, 12,
		12, 0
	};
	
	public static final int HEXAGON_BORDER_MAP_POSITION_DATA_LENGTH = 2 * 6 * DIMENSIONS_PER_VERTEX;
	public static final int HEXAGON_BORDER_MAP_NUM_OF_VERTICES = 2 * 6;
	
	public static float[] createHexagonWithBorders(float radius, float shiftX, float shiftY) {
		
		float[] outputArray = new float[HEXAGON_BORDER_MAP_POSITION_DATA_LENGTH];
		
		float z = 1.0f;
		
		for (double i = 0; i < (outputArray.length / 2) / DIMENSIONS_PER_VERTEX; ++i) {
			
			float x = (float)(Math.sin(i * (Math.PI / 3)) * radius + shiftX);
			float y = (float)(Math.cos(i * (Math.PI / 3)) * radius + shiftY);			
			
			outputArray[(int)((i * DIMENSIONS_PER_VERTEX) + 0)] = x;
			outputArray[(int)((i * DIMENSIONS_PER_VERTEX) + 1)] = y;
			outputArray[(int)((i * DIMENSIONS_PER_VERTEX) + 2)] = z;
			
			outputArray[(int)((i * DIMENSIONS_PER_VERTEX) + 3)] = x;
			outputArray[(int)((i * DIMENSIONS_PER_VERTEX) + 4)] = y;
			outputArray[(int)((i * DIMENSIONS_PER_VERTEX) + 5)] = z;
		}
		
		
		return outputArray;
		
	}
		
	//calculate the "Tile[] tiles"-index of a tile with given pixel-position of the tiles center
	//TODO: maybe change parameters from pixel data to opengl-coordinates
	public static int[] getAccordingTile(int XPixel, int YPixel) {
		
		int row = (int)Math.round( (2.0/3.0)*(-((2*YPixel/pxlH - 1)/(GameState.zoom*pxlW/pxlH) + GameState.shiftY)/Const.radiusOfHexagons - 1) );
		int col = (int)Math.round( ((2*XPixel/pxlW - 1)/GameState.zoom - GameState.shiftX)/(Const.SQRT3*Const.radiusOfHexagons) + 0.5*(1-row%2) - 1 );
		
		return new int[] {col, row};
		
	}
	public static float getLocalCoordsOfPixelX(int px) {
		
		return (2*px/pxlW - 1)/GameState.zoom - GameState.shiftX;
		
	}
	
	public static float getLocalCoordsOfPixelY(int py) {
		
		return (1 - 2*py/pxlH)/GameState.zoom*pxlH/pxlW - GameState.shiftX;
		
	}
	
	public static float getXPositionOfMarkedTile(int index, float radius, int mapWidth) {
		int col = index % mapWidth;
		int row = index / mapWidth;
		float SQRTOF3 = (float)Math.sqrt(3);
		
		return SQRTOF3 * radius * (0.5f + col + (row % 2 / 2.0f));
	}
	
	public static float getYPositionOfMarkedTile(int index, float radius, int mapWidth) {
		int row = index / mapWidth;
		
		return radius * (1.5f * row + 1);
	}
	
}
