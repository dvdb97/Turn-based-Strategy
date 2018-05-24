package function;

import dataType.GUIElementMatrix;
import input.Mouse;
import math.vectors.Vector3f;

public class TestDragFunction implements DragFunction {
	
	@Override
	public void calculate(GUIElementMatrix elementMatrix, GUIElementMatrix parentMatrix) {
		
		Vector3f vec = parentMatrix.getInverse().times(Mouse.getCursorPosititon());
		
		elementMatrix.setYShift(vec.getB()+0.5f*elementMatrix.getYStretch());
		
	}
	
}
