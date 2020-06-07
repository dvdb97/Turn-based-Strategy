package world;

import java.util.ArrayList;
import java.util.List;

import assets.meshes.Mesh3D;
import assets.meshes.Transformable;
import gameplay.Tribe;
import gameplay.TaskBarManager;
import math.MathUtils;
import models.gameboard.GameBoardModel;
import models.meeples.CityModel;
import models.meeples.MineModel;
import models.meeples.StreetModel;
import models.seeds.SuperGrid;
import utils.TileSurrounding;
import world.buildings.Building;
import world.estate.City;
import world.estate.Mine;
import world.estate.Population;

public class BuildingAuthority {
	
	private static GameBoardModel gameBoardModel;
	private static List<Mesh3D> meepleModels;
	private static SuperGrid superGrid;
	
	//TODO: don't hard code
	private static final int CITY_COST = 25;
	private static final int MINE_COST = 15;
	private static final int BUILDING_COST = 15;
	private static final int STREET_COST = 5;
	private static final int MAX_NUM_BUILDINGS_PER_CITY = 5;
	
	
	public static void init(GameBoardModel gameBoardModel, List<Mesh3D> meepleModels, SuperGrid superGrid) {
		BuildingAuthority.gameBoardModel = gameBoardModel;
		BuildingAuthority.meepleModels   = meepleModels;
		BuildingAuthority.superGrid      = superGrid;
	}
	
	
	public static City requestCityOnTile(int tileIndex) {
		Tile tile = GameBoard.getTile(tileIndex);
		
		if (!GameBoard.tileAvailableForEstate(tile))
			return null;
				
		if (Tribe.getCash() < CITY_COST)
			return null;
		
		//build city
		City city = new City(new Population());
		GameBoard.addEstate(tile, city);
		
		Tribe.increaseCash(-CITY_COST);
		
		CityModel cityModel = new CityModel(gameBoardModel.transformable);
		cityModel.transformable.setScaling(0.25f, 0.25f, 0.25f);
		cityModel.transformable.setRotation(90f * Transformable._1_DEGREE, 0f, 0f);
		cityModel.transformable.setTranslation(superGrid.getHexCenter(tileIndex));
		meepleModels.add(cityModel);
		return city;
	}
	
	public static boolean requestMineOnTile(int tileIndex, City motherCity) {
		Tile tile = GameBoard.getTile(tileIndex);
		
		if (!GameBoard.tileAvailableForEstate(tile))
			return false;
				
		if (Tribe.getCash() < MINE_COST)
			return false;
		
		if (WorldManager.getRelativeDistance(GameBoard.getTile(motherCity).getIndex(), tileIndex) > 3)
			return false;
		
		//build mine
		Mine mine = new Mine();
		GameBoard.addEstate(tile, mine);
		
		Tribe.increaseCash(-MINE_COST);
		Tribe.increaseGain(mine.getGain());
		
		MineModel mineModel = new MineModel(gameBoardModel.transformable);
		mineModel.transformable.setScaling(0.25f, 0.25f, 0.25f);
		mineModel.transformable.setRotation(90f * Transformable._1_DEGREE, 0f, 0f);
		mineModel.transformable.setTranslation(superGrid.getHexCenter(tileIndex));
		meepleModels.add(mineModel);
		return true;
		
	}
	
	
	public static boolean requestBuildingInCity(City city) {
		
		if (city.getBuildings().size() >= MAX_NUM_BUILDINGS_PER_CITY)
			return false;
		
		if (Tribe.getCash() < BUILDING_COST)
			return false;
		
		Building building = new Building(1);
		city.addBuilding(building);
		Tribe.increaseCash(-BUILDING_COST);
		Tribe.increaseGain(building.getGain());
		
		return true;
	}
	
	
	public static boolean requestStreet(int tileIndex1, int tileIndex2) {
		
		int angle = TileSurrounding.getAngle(tileIndex1, tileIndex2);
		
		if (angle == -1)
			return false;
		
		if (Tribe.getCash() < STREET_COST)
			return false;
		
		if (GameBoard.getTile(tileIndex1).isWater() || GameBoard.getTile(tileIndex2).isWater())
			return false;
		
		if (GameBoard.isStreet(tileIndex1, tileIndex2))
			return true;
		
		GameBoard.addStreet(tileIndex1, tileIndex2);
		Tribe.increaseCash(-STREET_COST);
		
		StreetModel streetModel = new StreetModel(gameBoardModel.transformable);
		streetModel.transformable.setScaling(0.2f, 0.2f, 0.2f);
		streetModel.transformable.setRotation(0f, 0f, angle * Transformable._1_DEGREE);
		streetModel.transformable.setTranslation(superGrid.getHexCenter(tileIndex1));
		meepleModels.add(streetModel);
		return true;
	}
	
	
	public static boolean requestConsecutiveStreets(List<Integer> tileIndices) {
		
		int numStreets = tileIndices.size()-1;
		
		if (numStreets < 1)
			return false;
		
		int numToBuild = numStreets;
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
				numToBuild -= 1;
			
			angles.add(angle);
		}
		
		if (GameBoard.getTile(tileIndices.get(numStreets)).isWater())
			return false;
		
		if (Tribe.getCash() < numToBuild*STREET_COST)
			return false;
		
		if (numToBuild == 0)
			return true;
		
		for (int k=0; k<numStreets; k++){
			int i = tileIndices.get(k);
			int j = tileIndices.get(k+1);
			
			if (GameBoard.isStreet(i, j))
				continue;
			
			GameBoard.addStreet(i, j);
			Tribe.increaseCash(-STREET_COST);
			
			StreetModel streetModel = new StreetModel(gameBoardModel.transformable);
			streetModel.transformable.setScaling(0.2f, 0.2f, 0.2f);
			streetModel.transformable.setRotation(0f, 0f, angles.get(k) * Transformable._1_DEGREE);
			streetModel.transformable.setTranslation(superGrid.getHexCenter(i));
			meepleModels.add(streetModel);
		}
		
		return true;
	}
	
}
