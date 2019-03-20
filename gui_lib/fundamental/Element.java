package fundamental;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import input.Mouse;
import math.vectors.Vector3f;
import rendering.shapes.GUIShape;

public abstract class Element implements ElementBase {
	
	protected GUIShape shape;
	protected Color color;
	
	//TODO: shity names
	protected GUIElementMatrix elementMatrix;
	/**
	 * inverted transformation matrix
	 */
	protected GUIElementMatrix invertedTM;
	protected GUIElementMatrix TM = new GUIElementMatrix();
	
	
	
	//***************** constructor *************************
	
	protected Element(GUIShape shape, Color color, GUIElementMatrix transformationMatrix) {
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
		shape.render(color, elementMatrix);
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
