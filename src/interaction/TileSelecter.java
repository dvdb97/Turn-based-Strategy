package interaction;

import core.Application;
import graphics.matrices.Matrices;
import interaction.input.CursorPosInput;
import interaction.input.MouseInputManager;
import math.matrices.Matrix33f;
import math.matrices.advanced.MatrixInversion33f;
import math.vectors.Vector3f;
import math.vectors.advanced.Distances;
import world.WorldManager;


public class TileSelecter {
	
	//output
	private static int hoveredTileIndex;
	private static int selectedTileIndex;
	
	//input
	private static float cursorX, cursorY;
	private static Vector3f[] tileCenterVertices;
	
	//variables needed for calculation
	private static Matrix33f invertedViewMatrix33f;
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
<<<<<<< HEAD
		invertedViewMatrix33f = MatrixInversion33f.generateMultiplicativeInverse(new Matrix33f(PlayerCamera.getViewMatrix()));
=======
		invertedViewMatrix33f = MatrixInversion33f.computeMultiplicativeInverse(new Matrix33f(CameraOperator.getViewMatrix()));
>>>>>>> gui_changes
		
		cursorX = CursorPosInput.getXPosAsOpenglCoord();
		cursorY = CursorPosInput.getYPosAsOpenglCoord();
		
		rayOrigin = PlayerCamera.getCameraPosition();
		rayDirection = new Vector3f(cursorX, cursorY/Matrices.getWindowProportions(), -1f);
		
		rayDirection = invertedViewMatrix33f.times(rayDirection);
		rayDirection = rayDirection.normalize();
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
