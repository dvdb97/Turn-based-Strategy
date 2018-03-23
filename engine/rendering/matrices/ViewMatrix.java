package rendering.matrices;

import assets.cameras.Camera;
import graphics.matrices.TransformationMatrix;
import math.matrices.Matrix44f;

//A ViewMatrix is a TransformationMatrix which represents the
//opposite movement of the camera it is referring to
public class ViewMatrix extends TransformationMatrix {
	
	private Camera camera;
	
	public ViewMatrix(Camera camera) {
		//gives an 4x4 identity matrix
		super();
		
		this.camera = camera;
		
		refresh();
		
	}
	
	public void refresh() {
		
		//setAll() is a method of the superclass TransformationMatrix
		setAll(camera.getPosition().negated(), -camera.getPitch(),
				-camera.getYaw(), -camera.getRoll(), camera.getZoom());
		updateData();
	}
	
	@Override
	protected void updateData() {
		
		setA1( scale*cRY*cRZ);
		setA2(-scale*sRZ);
		setA3( scale*sRY);
		setA4( scale*(+transX*cRY*cRZ-transY*sRZ+transZ*sRY));
		
		setB1( scale*sRZ);
		setB2( scale*cRX*cRZ);
		setB3(-scale*sRX);
		setB4( scale*(+transX*sRZ+transY*cRX*cRZ-transZ*sRX));
		
		setC1(-scale*sRY);
		setC2( scale*sRX);
		setC3( scale*cRX*cRY);
		setC4( scale*(-transX*sRY+transY*sRX+transZ*cRX*cRY));		
		
	    //setD4( scale);
		setD4( 1.0f);
		
	}
	
	
	public Matrix44f getMultiplicativeInverse() {
		
		return new TransformationMatrix(camera.getPosition().getA(), camera.getPosition().getB(), camera.getPosition().getC(), camera.getPitch(), camera.getYaw(), camera.getRoll(), 1.0f / camera.getZoom());
		
	}
}