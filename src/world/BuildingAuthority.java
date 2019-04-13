package world;

import java.util.List;

import assets.meshes.Mesh3D;
import models.gameboard.GameBoardModel;
import models.meeples.CityModel;
import models.seeds.SuperGrid;
import world.city.City;
import world.city.Population;
import world.gameBoard.GameBoard;
import world.gameBoard.Tile;

public class BuildingAuthority {
	
	private static GameBoardModel gameBoardModel;
	private static List<Mesh3D> meepleModels;
	private static SuperGrid superGrid;
	
	public static void init(GameBoardModel gameBoardModel, List<Mesh3D> meepleModels, SuperGrid superGrid) {
		BuildingAuthority.gameBoardModel = gameBoardModel;
		BuildingAuthority.meepleModels = meepleModels;
		BuildingAuthority.superGrid = superGrid;
	}
	
	public static boolean requestCityOnTile(int tileIndex) {
		
		Tile tile = GameBoard.getTile(tileIndex);
		
		if (!GameBoard.tileAvailableForCity(tile))
			return false;
		
		//if (user has no money)
			//return false;
		
		//build city
		City city = new City(new Population());
		GameBoard.addCity(tile, city);
		
		CityModel cityModel = new CityModel(gameBoardModel.transformable);
		cityModel.transformable.setScaling(0.1f, 0.1f, 0.25f);
		cityModel.transformable.setTranslation(superGrid.getHexCenter(tileIndex));
		meepleModels.add(cityModel);
		return true;
	}
	
	
	
}
