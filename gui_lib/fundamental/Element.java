package fundamental;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import gui_core.GUIMatrixManager;
import input.Mouse;
import math.matrices.Matrix44f;
import math.matrices.advanced.MatrixInversion33f;
import math.matrices.advanced.MatrixInversion44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import rendering.shapes.Shape;

public abstract class Element implements ElementBase {
	
	protected Shape shape;
	protected Color color;
	
	//TODO: shity names
	protected GUIElementMatrix elementMatrix;
	/**
	 * inverted transformation matrix
	 */
	protected GUIElementMatrix invertedTM;
	protected GUIElementMatrix TM = new GUIElementMatrix();
	
	
	
	//***************** constructor *************************
	
	protected Element(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		
		this.shape = shape;
		this.color = color;
		
		this.elementMatrix = transformationMatrix;
		this.invertedTM = transformationMatrix.getInverse();
		
	}
	
	
	
	//*******************************************************
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		updateRenderingMatrix(parentMatrix);
	}
	
	@Override
	public void render() {
		
		shape.render(null, color, TM);
		
	}
	
	/**
	 * returns true if hit
	 */
	@Override
	public boolean processInput() {
		
		Vector3f vec = invertedTM.times(Mouse.getCursorPosititon());
		
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
		
		this.TM = parentMatrix.times(elementMatrix);
		
		this.invertedTM = TM.getInverse();
		
	}


}
