package work_in_progress;

import assets.meshes.geometry.Color;
import gui_core.GUIMatrixManager;
import math.matrices.Matrix44f;
import math.matrices.advanced.MatrixInversion33f;
import math.matrices.advanced.MatrixInversion44f;

public abstract class Element implements ElementBase {
	
	protected GUIElementMatrix transformationMatrix;
	protected GUIElementMatrix invertedTM;	
	
	protected Shape shape;
	protected Color color;
	
	
	
	//*******************************************************
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		updateRenderingMatrix(parentMatrix);
	}
	
	@Override
	public void render() {
		
		shape.render(null, color, transformationMatrix);
		
	}
	
	@Override
	public void processInput(float cursorX, float cursorY, boolean leftMouseButtonDown, boolean rightMouseButtonDown) {
		
	}

	
	
	//*********************************************************
	
	/**
	 * updates rendering matrix (and its inversion)
	 */
	private void updateRenderingMatrix(GUIElementMatrix parentMatrix) {
			
	//TODO:	this.transformationMatrix = new GUIElementMatrix(xShift, yShift, xStretch, yStretch);
		
		this.transformationMatrix = parentMatrix.times(transformationMatrix);
		this.invertedTM = transformationMatrix.getInverse();
	}

}
