package world;

import java.util.ArrayList;
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
		
		int angle = TileSurrounding.getAngle(tileIndex1, tileIndex2);
		
		if (angle == -1)
			return false;
		
		if (GameBoard.getTile(tileIndex1).isWater() || GameBoard.getTile(tileIndex2).isWater())
			return false;
		
		if (!GameBoard.addStreet(tileIndex1, tileIndex2))
			return false;
		
		StreetModel streetModel = new StreetModel(gameBoardModel.transformable);
		streetModel.transformable.setScaling(0.2f, 0.2f, 0.2f);
		streetModel.transformable.setRotation(0f, 0f, angle * Transformable._1_DEGREE);
		streetModel.transformable.setTranslation(superGrid.getHexCenter(tileIndex1));
		meepleModels.add(streetModel);
		
		return true;
	}
	
	public static boolean requestConsecutiveStreets(List<Integer> tileIndices) {
		
		int numStreets = tileIndices.size()-1;
		
		ArrayList<Integer> angles = new ArrayList<>(numStreets);
		for (int k=0; k<numStreets; k++) {
			int i = tileIndices.get(k);
			int j = tileIndices.get(k+1);
			int angle = TileSurrounding.getAngle(i, j);
			
			if (angle == -1)
				return false;
			
			if (GameBoard.getTile(i).isWater())
				return false;
			
			if (GameBoard.isStreet(i, j))
				return false;
			
			angles.add(angle);
		}
		
		if (GameBoard.getTile(tileIndices.get(numStreets)).isWater())
			return false;
		
		for (int k=0; k<numStreets; k++){
			StreetModel streetModel = new StreetModel(gameBoardModel.transformable);
			streetModel.transformable.setScaling(0.2f, 0.2f, 0.2f);
			streetModel.transformable.setRotation(0f, 0f, angles.get(k) * Transformable._1_DEGREE);
			streetModel.transformable.setTranslation(superGrid.getHexCenter(tileIndices.get(k)));
			meepleModels.add(streetModel);
		}
		
		return true;
	}
	
}
