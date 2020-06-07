package models.meeples;

import assets.material.Material;
import assets.meshes.Mesh3D;
import assets.meshes.Transformable;
import assets.meshes.fileLoaders.FileLoader;
import math.vectors.Vector3f;
import utils.ColorPalette;

public class MineModel extends Mesh3D {
	
	public MineModel(Transformable gameBoard) {
		super(new Material(ColorPalette.TURQUOISE, Vector3f.ZERO, new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(0.2f, 0.2f, 0.2f), 256f));
				
		FileLoader.loadObjFile(this, "res/models/City.obj");
		
		transformable.setParent(gameBoard);
	}
	
}