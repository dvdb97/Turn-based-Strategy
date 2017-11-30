package math.matrices;

public class RotationMatrix extends Matrix44f {
	
	float rotX;
	float rotY;
	float rotZ;
	
	//c=cosine, s=sine, r=rotation
	float cRX;
	float sRX;
	float cRY;
	float sRY;
	float cRZ;
	float sRZ;
	
	//---------------------- constructor -------------------------
	public RotationMatrix(float rotX, float rotY, float rotZ) {
		
		super();
		
		this.rotX = rotX;
		this.rotX = rotY;
		this.rotX = rotZ;
		
		this.cRX = (float)Math.cos(rotX);
		this.sRX = (float)Math.sin(rotX);
		this.cRY = (float)Math.cos(rotY);
		this.sRY = (float)Math.sin(rotY);
		this.cRZ = (float)Math.cos(rotZ);
		this.sRZ = (float)Math.sin(rotZ);
		
		setA1(cRY*cRZ);
		setA2(-sRZ);
		setA3(sRY);
		setB1(sRZ);
		setB2(cRX*cRZ);
		setB3(-sRX);
		setC1(-sRY);
		setC2(sRX);
		setC3(cRX*cRY);
	}
}
