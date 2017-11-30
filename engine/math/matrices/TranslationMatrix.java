package math.matrices;

public class TranslationMatrix extends Matrix44f{
	
	float shiftX;
	float shiftY;
	float shiftZ;
	
	//--------------------- constructor -------------------
	public TranslationMatrix(float shiftX, float shiftY, float shiftZ) {
		super(1, 0, 0, shiftX, 0, 1, 0, shiftY, 0, 0, 1, shiftZ, 0, 0, 0, 1);
		
		this.shiftX = shiftX;
		this.shiftY = shiftY;
		this.shiftZ = shiftZ;
		
	}
	
}
