package interaction;

import interaction.input.CursorPosInput;
import interaction.input.MouseInputManager;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import math.vectors.advanced.Distances;
import rendering.SceneManager;
import world.WorldManager;


public class TileSelecter {
	
	//output
	private static int hoveredTileIndex;
	private static int selectedTileIndex;
	
	//input
	private static float cursorX, cursorY;
	private static Vector3f[] tileCenterVertices;
	
	//variables needed for calculation
	private static Vector3f rayOrigin;					//ray is an imaginary line starting at the camera postion and going through the cursor position
	private static Vector3f rayDirection;				//	it's a line in 3D space corresponding to a dot in 2D space
	
	
	//************************************** init ****************************************************
	public static void init() {
		
		hoveredTileIndex = 0;
		selectedTileIndex = 0;
		
		TileSelecter.tileCenterVertices = WorldManager.getTileCenterPos();
		
	}
	
	
	//************************************** calculation *********************************************
	private static void computeSelectedTileIndex() {
		
		refreshVariables();
		
		hoveredTileIndex = Distances.getClosestPointToLine(rayOrigin, rayDirection, tileCenterVertices);
		
		//TODO: move
		if (MouseInputManager.isLeftMouseButtonPressed()) {
			selectedTileIndex = hoveredTileIndex;
		}
		
	}

	private static void refreshVariables() {
		//Normalised device space
		cursorX = CursorPosInput.getXPosAsOpenglCoord();
		cursorY = CursorPosInput.getYPosAsOpenglCoord();		
		
		//Homogeneous Clip Coordinates
		Vector4f ray_clip = new Vector4f(cursorX, cursorY, -1f, 1f);
		
		//To eye space of the given camera
		Vector4f ray_eye = SceneManager.getCamera().getInvertedProjectionMatrix().times(ray_clip);
		ray_eye = new Vector4f(ray_eye.getA(), ray_eye.getB(), -1f, 0f);
		
		//To world Space
		rayDirection = SceneManager.getCamera().getInvertedViewMatrix().times(ray_eye).toVector3f().normalize();
		rayOrigin = SceneManager.getCamera().getPosition();
	}
	
	
	
	
	//************************************ public *****************************************************
	
	/**
	 * computes the index of the now hovered/selected tile
	 */
	public static void processInput() {
		computeSelectedTileIndex();
	}
	
	
	public static int getHoveredTileIndex() {
		return hoveredTileIndex;
	}
	
	
	public static int getSelectedTileIndex() {
		return selectedTileIndex;
	}

}
