package core;

import core.saves.StartParams;
import graphics.matrices.Matrices;
import interaction.PlayerCamera;
import interaction.tileSelection.TileBuffer;
import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.matrices.advanced.Determinant;
import math.matrices.advanced.LU_Decomposition;
import math.matrices.advanced.MatrixInversion33f;
import math.matrices.advanced.MatrixInversion44f;

public class Main {
	
	public static void main(String[] args) {
			
		Application.init(new StartParams("Files/StartParameters"));
		
	}

}
