package utils;

import world.GameBoard;
import world.Tile;

public class TileSurrounding {
	//TODO: bei radius==2 muss auch radius==1 beachtet werden
	public static int[] getSurrounding(int tileIndex, int radius) {
		
		if (tileIndex == -1 || radius == 0)
			return new int[] {};
		
		int length  = GameBoard.getLength();
		int width   = GameBoard.getWidth();
		int[] surrounding = new int[LENGTH[radius]];
		for (int i=0; i<surrounding.length; i++) {
			
			int x = tileIndex%length + INDICES_DX[(tileIndex/length)%2][radius][i];
			int y = tileIndex/length + INDICES_DY[radius][i];
			
			if (x>=0 && x<length && y>=0 && y<width) {
				surrounding[i] = x + y*length;
			} else {
				surrounding[i] = -1;	//TODO: handle (-1) , e.g. getTile(-1) == null
			}
			
		}
		return surrounding;
	}
	
	public static int[] getSurrounding(int tileIndex) {
		return getSurrounding(tileIndex, 1);
	}
	
	public static Tile[] getSurroundingTiles(int tileIndex, int radius) {
		int[] indices = TileSurrounding.getSurrounding(tileIndex, radius);
		Tile[] spoi = new Tile[indices.length];
		for (int i=0; i<indices.length; i++) {
			spoi[i] = GameBoard.getTile(indices[i]);
		}
		return spoi;
	}
	
	public static Tile[] getSurroundingTiles(int tileIndex) {
		return getSurroundingTiles(tileIndex, 1);
	}
	
	public static boolean areNeighbours(int tileIndex1, int tileIndex2) {
		
		int[] surroundingOfTile1 = getSurrounding(tileIndex1);
		for (int i=0; i<surroundingOfTile1.length; i++) {
			if (surroundingOfTile1[i] == tileIndex2)
				return true;
		}
		return false;
		
	}
	
	/**
	 * C1: center of tile 1.
	 * C2: center of tile 2.
	 * e_x: Unit vector parallel to x-axis.
	 * 
	 * @param tileIndex1 tileIndex of tile 1
	 * @param tileIndex2 tileIndex of tile 2
	 * @return angle between e_x and the vector from C1 to C2. -1, if tiles are not neighbours or tileIndex1 is -1
	 */
	public static int getAngle(int tileIndex1, int tileIndex2) {

		int[] surroundingOfTile1 = getSurrounding(tileIndex1);
		for (int i=0; i<surroundingOfTile1.length; i++) {
			if (surroundingOfTile1[i] == tileIndex2)
				return i*60;
		}
		return -1;
		
	}
	
	//*******************************************************************
	//TODO
	
	private static final int[] LENGTH = new int[] {1, 6, 12, 18}; // = 6*radius
	
	private static final int[][][] INDICES_DX = new int[][][] {
			{{0},{1, 0,-1,-1,-1, 0},{2, 1, 1, 0,-1,-2,-2,-2,-1, 0, 1, 1},{3, 2, 2, 1, 0,-1,-2,-2,-3,-3,-3,-2,-2,-1, 0, 1, 2, 2}},	//gerade Reihe, (tileIndex/length)%2==0
			{{0},{1, 1, 0,-1, 0, 1},{2, 2, 1, 0,-1,-1,-2,-1,-1, 0, 1, 2},{3, 3, 2, 2, 1, 0,-1,-2,-2,-3,-2,-2,-1, 0, 1, 2, 2, 3}}	//ungerade Reihe, (tileIndex/length)%2==1
	};
	
	private static final int[][] INDICES_DY = new int[][] {
			{0},{0, 1, 1, 0,-1,-1},{0, 1, 2, 2, 2, 1, 0,-1,-2,-2,-2,-1},{0, 1, 2, 3, 3, 3, 3, 2, 1, 0,-1,-2,-3,-3,-3,-3,-2,-1}
	};
	
}
