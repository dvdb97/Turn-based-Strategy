package world;

public class GameBoard {
	
	public int HEIGHT, WIDTH;
	public Tile[] tiles;
	public byte[] seed;			//landscapeIDs of every tile
	
	//------------------------------ constructor --------------------------
	public GameBoard(int height, int width, byte[] seed) {
		
		HEIGHT = height;
		WIDTH = width;
		this.seed = seed;
		
		//create Tiles
		tiles = new Tile[HEIGHT*WIDTH];
		for (int t=0; t<tiles.length; t++) {
			tiles[t] = new Tile(t, utils.Const.landscapeTypes[seed[t]], seed[t]);
		}
		
	}
	
}

