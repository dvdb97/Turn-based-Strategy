package work_in_progress;

import assets.meshes.geometry.Color;
import gui_core.GUIMatrixManager;
import math.matrices.Matrix44f;
import math.matrices.advanced.MatrixInversion33f;
import math.matrices.advanced.MatrixInversion44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

public abstract class Element implements ElementBase {
	
	protected Shape shape;
	protected Color color;
	
	protected GUIElementMatrix transformationMatrix;
	protected GUIElementMatrix invertedTM;	
	
	//***************** constructor *************************
	
	protected Element(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		
		this.shape = shape;
		this.color = color;
		
		this.transformationMatrix = transformationMatrix;
		this.invertedTM = transformationMatrix.getInverse();
		
	}
	
	
	
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
	public boolean processInput() {
		
		Vector3f vec = Mouse.getCursorPosititon();
		vec = transformationMatrix.times(vec);
		
		if (shape.isHit(vec.getA(), vec.getB())) {
			return true;
		}
		
		return false;
		
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
