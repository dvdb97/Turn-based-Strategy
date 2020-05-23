package world;

import java.util.List;

import assets.meshes.Mesh3D;
import assets.meshes.Transformable;
import math.MathUtils;
import models.gameboard.GameBoardModel;
import models.meeples.CityModel;
import models.meeples.StreetModel;
import models.seeds.SuperGrid;
import utils.TileSurrounding;
import world.city.City;
import world.city.Population;

public class BuildingAuthority {
	
	private static GameBoardModel gameBoardModel;
	private static List<Mesh3D> meepleModels;
	private static SuperGrid superGrid;
	
	public static void init(GameBoardModel gameBoardModel, List<Mesh3D> meepleModels, SuperGrid superGrid) {
		BuildingAuthority.gameBoardModel = gameBoardModel;
		BuildingAuthority.meepleModels   = meepleModels;
		BuildingAuthority.superGrid      = superGrid;
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
		cityModel.transformable.setScaling(0.25f, 0.25f, 0.25f);
		cityModel.transformable.setRotation(90f * Transformable._1_DEGREE, 0f, 0f);
		cityModel.transformable.setTranslation(superGrid.getHexCenter(tileIndex));
		meepleModels.add(cityModel);
		return true;
	}
	
	
	public static boolean requestStreet(int tileIndex1, int tileIndex2) {
		
		if (TileSurrounding.areNeighbours(tileIndex1, tileIndex2))
			return false;;
		
		if (GameBoard.getTile(tileIndex1).isWater() || GameBoard.getTile(tileIndex2).isWater())
			return false;
		
		if (!GameBoard.addStreet(tileIndex1, tileIndex2))
			return false;
		
		StreetModel streetModel = new StreetModel(gameBoardModel.transformable);
		streetModel.transformable.setScaling(0.25f, 0.25f, 0.25f);
		streetModel.transformable.setRotation(90f * Transformable._1_DEGREE, 0f, 0f); //TODO
		streetModel.transformable.setTranslation(superGrid.getHexCenter(tileIndex1));
		meepleModels.add(streetModel);
		
		return true;
	}
}
