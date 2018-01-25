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
		
	}
	
	
	private static void computeSelectedTileIndex() {
		
		Matrix33f invertedViewMatrix33f = MatrixInversion33f.generateMultiplicativeInverse(new Matrix33f(CameraOperator.getViewMatrix()));
		
		cursorX = Application.toOpenglXCoords(CursorPosInput.getXPos());
		cursorY = Application.toOpenglYCoords(CursorPosInput.getYPos());
		
		Vector3f rayOrigin = Camera.getPosition();
		Vector3f rayDirection = new Vector3f(cursorX, cursorY/Matrices.getProjectionMatrix().getWidthOverHeight(), -1f);
		
		rayDirection = invertedViewMatrix33f.times(rayDirection);
		rayDirection = rayDirection.normalize();
		
		hoveredTileIndex = getTileIndex(rayOrigin, rayDirection, centerVertices);
		
		if (MouseInputManager.isLeftMouseButtonPressed()) {
			selectedTileIndex = hoveredTileIndex;
		}
		
	}	
	
	
	
	/**
	 * This algorithm searches the vertex that is the closest to the ray defined by origin and direction
	 * 
	 * @param origin The origin of the ray
	 *  
	 * @param direction The direction of the ray
	 *
	 * @return Returns the index of the closest vertex to the ray
	 * 
	 */
	private static int getTileIndex(Vector3f origin, Vector3f direction, Vector3f[] vertices) {
		
		float distance;
		int bestCandidateIndex = 0;
		float bestCandidateDistance = Float.MAX_VALUE;
		
		for (int i=0; i<vertices.length; i++) {
			
			distance = Distances.distanceLinePoint(origin, direction, vertices[i]);
			
			if (distance < bestCandidateDistance) {
				bestCandidateDistance = distance;
				bestCandidateIndex = i;
			}
			
		}
		
		return bestCandidateIndex;
		
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
