package models.textures;

import assets.textures.Texture3D;

public class TerrainTexture extends Texture3D {
		
	private static String[] paths = {
		"res/Textures/Terrain/sand.png",
		"res/Textures/Terrain/grass.png",
		"res/Textures/Terrain/rock.png",
		"res/Textures/Terrain/ice.png"
	};
	
	
	public TerrainTexture() {
		super(paths);
		
		setTextureWrap(CLAMP_TO_EDGE);
	}	
	
}
