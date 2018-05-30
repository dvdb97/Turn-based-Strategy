package rendering.matrices.transformation;

import math.matrices.Matrix44f;
import math.vectors.Vector3f;

public class TranslationMatrix {
	
	
	public static Matrix44f getTranslationMatrix(float x, float y, float z) {
		
		Matrix44f matrix = new Matrix44f();
		matrix.setIdentity();
		
		matrix.setA4(x);
		matrix.setB4(y);
		matrix.setC4(z);
		
		return matrix;
		
	}
	
	
	public static Matrix44f getTranslationMatrix(Vector3f translVector) {
		
		return getTranslationMatrix(translVector.getA(), translVector.getB(), translVector.getC());
		
	}

}
