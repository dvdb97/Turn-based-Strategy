package rendering;

import assets.textures.Skybox;

public class GameSkybox extends Skybox {

	private static String[] paths = {
		"res/Textures/Skyboxes/ice/right.jpg",
		"res/Textures/Skyboxes/ice/left.jpg",
		"res/Textures/Skyboxes/ice/top.jpg",
		"res/Textures/Skyboxes/ice/bottom.jpg",
		"res/Textures/Skyboxes/ice/back.jpg",
		"res/Textures/Skyboxes/ice/front.jpg"
	};
	
	public GameSkybox() {
		super(paths);
	}

}
