package fundamental;

import dataType.GUIElementMatrix;

public interface Moveable {
	
	GUIElementMatrix getTransformationMatrix();
	void onConcludedMovement();
}
