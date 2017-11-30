package rendering.matrices.transformation;

import math.matrices.Matrix44f;
import math.vectors.Vector3f;

import static rendering.matrices.transformation.TranslationMatrix.*;
import static rendering.matrices.transformation.RotationMatrix.*;
import static rendering.matrices.transformation.ScalingMatrix.*;


public class TransformationMatrix {
	
	
	public static Matrix44f getTransformationMatrix(float xTransl, float yTransl, float zTransl, float xRot, float yRot, float zRot, float scale) {
		
		return getTranslationMatrix(xTransl, yTransl, zTransl).times(getRotationMatrix(xRot, yRot, zRot).times(getScalingMatrix(scale)));
		
	}
	
	
	public static Matrix44f getTransformationMatrix(Vector3f transl, Vector3f rot, float scale) {
		
		return getTransformationMatrix(transl.getA(), transl.getB(), transl.getC(), rot.getA(), rot.getB(), rot.getC(), scale);
		
	}
	

}
