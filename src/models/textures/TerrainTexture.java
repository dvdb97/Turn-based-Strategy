package models.textures;

import assets.textures.Texture3D;

public class TerrainTexture extends Texture3D {
		
	private static String[] paths = {
		"res/Textures/Terrain/sand_01_AO_1k.jpg",
		"res/Textures/Terrain/forrest_ground_01_ao_1k.jpg",
		"res/Textures/Terrain/rock_ground_ao_1k.jpg",
		"res/Textures/Terrain/snow_02_ao_1k.jpg"
	};
	
	
	public TerrainTexture() {
		super(paths);
	}	
	
}
