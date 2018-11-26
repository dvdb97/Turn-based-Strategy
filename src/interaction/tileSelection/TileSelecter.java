package interaction.tileSelection;

import interaction.PlayerCamera;
import interaction.Window;
import interaction.input.CursorPosInput;
import interaction.input.MouseInputManager;
import math.matrices.Matrix33f;
import math.matrices.advanced.MatrixInversion33f;
import math.vectors.Vector3f;
import utils.TileBuffer;


public class TileSelecter {	
	
	private static TileBuffer tileBuffer;
	
	private static int hoveredTileIndex;
	
	private static int selectedTileIndex;
	
		
	public static void init(Vector3f[] centerVertices) {
		
		hoveredTileIndex = 0;
		selectedTileIndex = 0;
		
		tileBuffer = new TileBuffer(centerVertices, new Vector3f(-0.1f, -0.1f, 0.1f));
		
	}
	
	
	private static void computeSelectedTileIndex() {
		
		Matrix33f invertedViewMatrix33f = MatrixInversion33f.generateMultiplicativeInverse(new Matrix33f(PlayerCamera.getViewMatrix()));
		
		float cursorX = CursorPosInput.getXPosAsOpengl();
		float cursorY = CursorPosInput.getYPosAsOpengl();
		
		Vector3f rayOrigin = PlayerCamera.getCameraPosition();
		
		Vector3f rayDirection = new Vector3f(cursorX, cursorY/Window.main.getAspectRatio(), -1f);
		
		rayDirection = invertedViewMatrix33f.times(rayDirection);
		
		rayDirection = rayDirection.normalize();
		
		hoveredTileIndex = tileBuffer.getTileIndex(rayOrigin, rayDirection);	
		
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
