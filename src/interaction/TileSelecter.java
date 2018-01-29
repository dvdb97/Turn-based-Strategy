package interaction;

import core.Application;
import graphics.Camera;
import graphics.matrices.Matrices;
import interaction.input.CursorPosInput;
import interaction.input.MouseInputManager;
import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.matrices.RotationMatrix;
import math.matrices.advanced.MatrixInversion33f;
import math.matrices.advanced.MatrixInversion44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import math.vectors.advanced.Distances;

import static org.lwjgl.opengl.GL11.*;


public class TileSelecter {
	
	private static int hoveredTileIndex;
	
	private static int selectedTileIndex;
	
	private static float cursorX, cursorY;
	
	private static Vector3f[] centerVertices;
	
	
	
	public static void init(Vector3f[] centerVertices) {
		
		hoveredTileIndex = 0;
		selectedTileIndex = 0;
		
		TileSelecter.centerVertices = centerVertices;
		System.out.println(centerVertices.length);
	}
	
	
	private static void computeSelectedTileIndex() {
		
		Matrix33f invertedViewMatrix33f = MatrixInversion33f.generateMultiplicativeInverse(new Matrix33f(CameraOperator.getViewMatrix()));
		
		cursorX = Application.toOpenglXCoords(CursorPosInput.getXPos());
		cursorY = Application.toOpenglYCoords(CursorPosInput.getYPos());
		
		Vector3f rayOrigin = Camera.getPosition();
		Vector3f rayDirection = new Vector3f(cursorX, cursorY/Matrices.getProjectionMatrix().getWidthOverHeight(), -1f);
		
		rayDirection = invertedViewMatrix33f.times(rayDirection);
		rayDirection = rayDirection.normalize();
		
		hoveredTileIndex = Distances.getClosestPointToLine(rayOrigin, rayDirection, centerVertices);
		
		if (MouseInputManager.isLeftMouseButtonPressed()) {
			selectedTileIndex = hoveredTileIndex;
		}
		
	}
	
	
	public static void processInput() {
		computeSelectedTileIndex();
	}
	
	
	public static int getHoveredTileIndex() {
	//	System.out.println(hoveredTileIndex);
		return hoveredTileIndex;
	}
	
	
	public static int getSelectedTileIndex() {
		return selectedTileIndex;
	}

}
