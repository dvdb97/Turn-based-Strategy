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
		
		tiles.add(processSPOIRing(getRadius()+1));
		
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
	
	//TODO: a lot. this method works only in very few cases
	
	private ArrayList<Tile> processSPOIRing(int radius) {
		
		ArrayList<Tile> ring = new ArrayList<>();
		
		int index = getCenterTile().getIndex();
		int length = GameBoard.getLength();
		
		if (radius == 1 && index%2 == 1) {
			
			ring = new ArrayList<>(SPOI_R1_LENGTH);
			
			for (int i=0; i<SPOI_R1_LENGTH; i++) {
				
				ring.add( GameBoard.getTile( (index%length + SPOI_R1_INDICES_DX[i]) + (index/length + SPOI_R1_INDICES_DY[i])*length ) );
				
			}
			
		}
		
		return ring;
		
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
	
	
	//********************* spoi data **************************
	
	private static final int SPOI_R1_LENGTH = 6;
	private static final int[] SPOI_R1_INDICES_DX = new int[] {1, 1, 0,-1, 0, 1};
	private static final int[] SPOI_R1_INDICES_DY = new int[] {0, 1, 1, 0,-1,-1};
	
	
	
}
