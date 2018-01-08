package interaction.tileSelection;

import core.Application;
import graphics.Camera;
import graphics.matrices.Matrices;
import interaction.PlayerCamera;
import interaction.input.CursorPosInput;
import interaction.input.MouseInputManager;
import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.matrices.advanced.MatrixInversion33f;
import math.matrices.advanced.MatrixInversion44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;


public class TileSelecter {	
	
	private static TileBuffer tileBuffer;
	
	private static Matrix44f invertedProjectionMatrix;
	private static Matrix33f invertedProjectionMatrix33f;
	
	private static int hoveredTileIndex = 0;
	
	private static int selectedTileIndex = 0;
	
		
	public static void init(Vector3f[] centerVertices) {
		
		//Generate the inverse of the 4x4 projection matrix
		invertedProjectionMatrix = MatrixInversion44f.generateMultiplicativeInverse(Matrices.getProjectionMatrix());
		
		//Generate the inverse of the 3x3 projection matrix
		invertedProjectionMatrix33f = MatrixInversion33f.generateMultiplicativeInverse(new Matrix33f(Matrices.getProjectionMatrix()));
		
		tileBuffer = new TileBuffer(centerVertices, new Vector3f(-0.1f, -0.1f, 0.1f));
		
	}
	
	
	private static void computeSelectedTileIndex() {
		
		Matrix44f invertedViewMatrix = MatrixInversion44f.generateMultiplicativeInverse(PlayerCamera.getViewMatrix());
		Matrix33f invertedViewMatrix33f = MatrixInversion33f.generateMultiplicativeInverse(new Matrix33f(PlayerCamera.getViewMatrix()));
		
		float cursorX = Application.toOpenglXCoords(CursorPosInput.getXPos());
		float cursorY = Application.toOpenglYCoords(CursorPosInput.getYPos());
		
		Vector3f rayOrigin = Camera.getPosition();
		
		Vector3f ray = new Vector3f(cursorX, cursorY, -1f);
		ray = invertedViewMatrix33f.times(ray);
		ray = ray.normalize();
		
		hoveredTileIndex = tileBuffer.getTileIndex(rayOrigin, ray);	
		
		if (MouseInputManager.isLeftMouseButtonPressed()) {
			selectedTileIndex = hoveredTileIndex;
		}
		
	}	
	
	
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
