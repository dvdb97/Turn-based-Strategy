package function;

import dataType.GUIElementMatrix;
import input.Mouse;
import math.vectors.Vector3f;

public class TestDragFunction implements DragFunction {
	
	@Override
	public void calculate(GUIElementMatrix elementMatrix, GUIElementMatrix parentMatrix) {
		
		Vector3f vec = parentMatrix.getInverse().times(Mouse.getCursorPosititon());
		
		float a = vec.getB()+0.5f*elementMatrix.getYStretch();
		
		if (a < -0.9f)
			elementMatrix.setYShift(-0.9f);
		else if (a > 0)
			elementMatrix.setYShift(0);
		else
			elementMatrix.setYShift(a);
		
	}
	
}
