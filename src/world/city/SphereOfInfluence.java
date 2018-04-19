package world.city;

import java.util.ArrayList;

import world.gameBoard.GameBoard;
import world.gameBoard.Tile;

public class SphereOfInfluence {
	
	private static int MAX_RADIUS = 5;
	
	private ArrayList<ArrayList<Tile>> tiles;
	
	
	
	//*********************** constructor **********************
	
	public SphereOfInfluence(Tile centerTile) {
		
		setCenterTile(centerTile);
		
	}
	
	
	
	//*********************** prime methods *********************
	
	public boolean expand() {
		
		if (getRadius() == MAX_RADIUS) {
			System.err.println("SPOI: maximum radius already reached");
			return false;
		}
		
		tiles.add(processSpoiRing(getRadius()+1));
		
		return true;
		
	}
	
	public boolean shrink() {
		
		if (getRadius() == 0) {
			System.err.println("SPOI: minimum radius already reached");
			return false;
		}
		
		tiles.remove(getRadius());
		
		return true;
		
	}
	
	
	
	//*********************************************************
	
	private ArrayList<Tile> processSpoiRing(int radius) {
		
		int length  = GameBoard.getLength();
		int index   = getCenterTile().getIndex();
		
		ArrayList<Tile> spoi = new ArrayList<>(SPOI_LENGTH[radius]);
		
		int x;
		int y;
		
		for (int i=0; i<SPOI_LENGTH[radius]; i++) {
			
			//TODO: check if tile exists
			x = index%length + SPOI_INDICES_DX[index%2][radius][i];
			y = index/length + SPOI_INDICES_DY[index%2][radius][i];
			
			spoi.add(GameBoard.getTile(x + y*length));
			
		}
		
		return spoi;
		
	}
	
	
	//********************* get & set *************************
	
	public int getRadius() {
		return tiles.size()-1;
	}
	
	public Tile getCenterTile() {
		return tiles.get(0).get(0);
	}
	
	private void setCenterTile(Tile centerTile) {
		
		tiles = new ArrayList<>();
		tiles.add(new ArrayList<>(1));
		tiles.get(0).add(centerTile);
		
	}
	
	
	//************************ spoi data ********************************
	//TODO
	
	private static final int [] SPOI_LENGTH = new int[] {6, 12};
	
	private static final int[][][] SPOI_INDICES_DX = new int[][][] {
			{{1, 1, 0,-1, 0, 1},{2, 2, 1, 0, 1,-1,-2,-1,-1, 0, 1, 2}},
			{{},{}}
	};
	
	private static final int[][][] SPOI_INDICES_DY = new int[][][] {
			{{0, 1, 1, 0,-1,-1},{0, 1, 2, 2, 2, 1, 0,-1,-2,-2,-2,-1}},
			{{},{}}
	};	
	
}
