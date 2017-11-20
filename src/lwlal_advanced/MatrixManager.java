package lwlal_advanced;

import utils.Const;
import static utils.GeometryUtils.getXPositionOfMarkedTile;
import static utils.GeometryUtils.getYPositionOfMarkedTile;

public class MatrixManager {
	
	//private final static float localMarkedTileXPos = getXPositionOfMarkedTile(0, Const.radiusOfHexagons, logics.Logics.board.WIDTH);
	//  private final static float localMarkedTileYPos = getYPositionOfMarkedTile(0, Const.radiusOfHexagons, logics.Logics.board.WIDTH);
	
	// 	//Compute the shift needed to have the tileMarker on top of the marked tile:
	// 	float shiftX = getXPositionOfMarkedTile(markedTileIndex, Const.radiusOfHexagons, logics.Logics.board.WIDTH) - localMarkedTileXPos;
	// 	float shiftY = getYPositionOfMarkedTile(markedTileIndex, Const.radiusOfHexagons, logics.Logics.board.WIDTH) - localMarkedTileYPos;
	
	private final static float localMarkedTileXPos = getXPositionOfMarkedTile(0, Const.radiusOfHexagons, logics.Logics.board.WIDTH);
	private final static float localMarkedTileYPos = getYPositionOfMarkedTile(0, Const.radiusOfHexagons, logics.Logics.board.WIDTH);
	
	public static TransformationMatrix TileMarkerTransfMatr;
	private static float TMshiftX;
	private static float TMshiftY;
	
	public static void createTMTM() {
		
		TileMarkerTransfMatr = new TransformationMatrix(); 
		
	}
	
	public static TransformationMatrix getTileMarkerTransfMatr(int markedTileIndex) {
		
		float shiftX = getXPositionOfMarkedTile(markedTileIndex, Const.radiusOfHexagons, logics.Logics.board.WIDTH) - localMarkedTileXPos;
		float shiftY = getYPositionOfMarkedTile(markedTileIndex, Const.radiusOfHexagons, logics.Logics.board.WIDTH) - localMarkedTileYPos;
		
		TileMarkerTransfMatr.setTrans(shiftX, shiftY, 0.0f);
		
		return TileMarkerTransfMatr;
	}
}