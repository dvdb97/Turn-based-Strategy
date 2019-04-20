package world;

import java.util.LinkedList;
import java.util.List;

public class HexGridUtils {
	
	public static List<Integer> getNeighbors(int width, int length, int tile, int radius) {		
		int x;
		int y;
		
		LinkedList<Integer> spoi = new LinkedList<Integer>();
		
		for (int i=0; i<SPOI_RING_LENGTH[radius]; i++) {
			x = tile % length + SPOI_INDICES_DX[tile % 2][radius][i];
			y = tile / length + SPOI_INDICES_DY[radius][i];
			int index = x + y * length;
			
			//Checks if the tile actually exists.
			if (index >= 0 && index < length * width) {
				spoi.add(x + y*length);
			}
		}
		
		return spoi;
	}
	
	private static final int[] SPOI_RING_LENGTH = new int[] {6, 12, 18}; // = 6*radius
	
	private static final int[][][] SPOI_INDICES_DX = new int[][][] {
			{{1, 1, 0,-1, 0, 1},{2, 2, 1, 0,-1,-1,-2,-1,-1, 0, 1, 2},{3, 3, 2, 2, 1, 0,-1,-2,-2,-3,-2,-2,-1, 0, 1, 2, 2, 3}},
			{{1, 0,-1,-1,-1, 0},{2, 1, 1, 0,-1,-2,-2,-2,-1, 0, 1, 1},{3, 2, 2, 1, 0,-1,-2,-2,-3,-3,-3,-2,-2,-1, 0, 1, 2, 2}}
	};
	
	private static final int[][] SPOI_INDICES_DY = new int[][] {
			{0, 1, 1, 0,-1,-1},{0, 1, 2, 2, 2, 1, 0,-1,-2,-2,-2,-1},{0, 1, 2, 3, 3, 3, 3, 2, 1, 0,-1,-2,-3,-3,-3,-3,-2,-1}
	};	

}
