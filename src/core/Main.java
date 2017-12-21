package core;

import assets.meshes.geometry.Vertex;
import core.saves.StartParams;
import graphics.matrices.Matrices;
import interaction.tileSelection.TileBuffer;
import math.matrices.Matrix44f;
import math.matrices.advanced.LU_Decomposition;
import math.matrices.advanced.MatrixInversion;
import math.vectors.Vector3f;

public class Main {
	
	public static void main(String[] args) {
			
		Application.init(new StartParams("Files/StartParameters"));		
		
	}

}
