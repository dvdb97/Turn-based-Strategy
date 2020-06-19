package models.textures;

import assets.textures.ArrayTexture2D;

public class TerrainTexture {
	
	private static String[] paths = {
		"res/Textures/Terrain/sand_01_AO_1k.jpg",
		"res/Textures/Terrain/forrest_ground_01_ao_1k.jpg",
		"res/Textures/Terrain/rock_ground_ao_1k.jpg",
		"res/Textures/Terrain/snow_02_ao_1k.jpg"
	};
	
	public static ArrayTexture2D create() {
		ArrayTexture2D texture = new ArrayTexture2D(4, 1024, 1024, paths);
		
		texture.setImageData(paths);
		
		return texture;
	}

}
