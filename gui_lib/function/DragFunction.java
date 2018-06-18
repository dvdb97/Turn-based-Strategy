package function;

import dataType.GUIElementMatrix;
import math.vectors.Vector2f;

public interface DragFunction {
	
	void calculate(GUIElementMatrix matrix, GUIElementMatrix parentMatrix, Vector2f offset);
	
}
