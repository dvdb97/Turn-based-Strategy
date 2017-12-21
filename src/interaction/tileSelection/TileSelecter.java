package interaction.tileSelection;

import core.Application;
import graphics.matrices.Matrices;
import interaction.PlayerCamera;
import interaction.input.CursorPosInput;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

public class TileSelecter {	
	
	private static int hoveredTileIndex;
	
		
	public static void init(Vector3f[] centerVertices) {
		
		TileBuffer.init(centerVertices, new Vector3f(-200.34f, 120.567f, -10.32f));
		
	}
	
	
	private static void computeSelectedTileIndex() {
		
		float cursorX = Application.toOpenglXCoords(CursorPosInput.getXPos());
		float cursorY = Application.toOpenglYCoords(CursorPosInput.getYPos());
		
		Vector4f rayStart = new Vector4f(cursorX, cursorY, 1.0f, 1.0f);
		Vector4f rayDirection = new Vector4f(0f, 0f, -1f, 1f);		
		
		Matrix44f invertedVMatrix = PlayerCamera.getInvertedMatrix().times(Matrices.getProjectionMatrix());
		rayStart = invertedVMatrix.times(rayStart);	
		rayDirection = invertedVMatrix.times(rayDirection);
		
		hoveredTileIndex = TileBuffer.getTileIndex(rayStart, rayDirection, 0.001f, 10, 20);
		
	}
	
	
	public static void processInput() {
		computeSelectedTileIndex();
	}
	
	
	public static int getHoveredTileIndex() {
		return hoveredTileIndex;
	}

}
