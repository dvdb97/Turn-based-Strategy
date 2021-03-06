package models.meeples;

import assets.material.Material;
import assets.meshes.Mesh3D;
import assets.meshes.Transformable;
import assets.meshes.fileLoaders.FileLoader;
import math.vectors.Vector3f;
import utils.ColorPalette;

public class CityModel extends Mesh3D {
	
	public CityModel(Transformable gameBoard) {
		super(new Material(ColorPalette.BORDEAUX, Vector3f.ZERO, new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(0.2f, 0.2f, 0.2f), 256f));
		
		/*
		float[] posData = new float[] {-1,-1, 0,
		
		                               -1, 1, 0,
		                                1,-1, 0,
		                                1, 1, 0,
		                               -1,-1, 2,
		                               -1, 1, 2,
		                                1,-1, 2,
		                                1, 1, 2};
		
		FloatBuffer posBuffer = CustomBufferUtils.createFloatBuffer(posData);
		
		setPositionData(posBuffer);
		setIndexBuffer(CustomBufferUtils.createIntBuffer(new int[] {0,1,2,1,2,3,0,1,4,1,4,5,2,3,6,3,6,7,4,5,6,5,6,7,1,3,5,3,5,7,0,2,4,2,4,6}));
		*/
		
		FileLoader.loadObjFile(this, "res/models/City.obj");
		
		transformable.setParent(gameBoard);
	}
	
}
