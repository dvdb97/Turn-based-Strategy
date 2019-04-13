package world;

import java.util.List;

import assets.meshes.Mesh3D;
import models.gameboard.GameBoardModel;
import models.meeples.CityModel;
import world.city.City;
import world.city.Population;
import world.gameBoard.GameBoard;
import world.gameBoard.Tile;

public class BuildingAuthority {
	
	private static GameBoardModel gameBoardModel;
	private static List<Mesh3D> meepleModels;
	
	public static void init(GameBoardModel gameBoardModel, List<Mesh3D> meepleModels) {
		BuildingAuthority.gameBoardModel = gameBoardModel;
		BuildingAuthority.meepleModels = meepleModels;
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
		meepleModels.add(cityModel);
		
		return true;
	}
	
	
	
}
