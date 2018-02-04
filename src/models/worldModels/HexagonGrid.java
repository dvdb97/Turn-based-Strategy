package models.worldModels;

import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;

public class HexagonGrid {
	
	//dimensions
	private int length, width;
	
	//others
	private Color[] colors;
	
	//***************************** constructor *****************************
	
	public HexagonGrid(Vertex[] vertices, Color[] colors) {
		
		this.colors = colors;
		
		
		
	}
	
	//****************************** methods ********************************
	
	
	
	//****************************** update *********************************
	
	private void updateColor() {
		
		float[] colorsF = new float[colors.length*4];
		
		for (int i=0; i<colors.length; i++) {
			
			colorsF[i*4 + 0] = colors[i].getA();
			colorsF[i*4 + 1] = colors[i].getB();
			colorsF[i*4 + 2] = colors[i].getC();
			colorsF[i*4 + 3] = colors[i].getD();
			
		}
		
	}
	
	
	//****************************** get & set ******************************
	
	
	public void setColor(int index, Color color) {
		
		colors[index] = color;
		updateColor();
		
	}
	
	public void setColor(Color[] colors) {
		
		for (int i=0; i<colors.length; i++) {
			this.colors[i] = colors[i];
		}
		updateColor();
	}
	
	public void setColor(int[] indices, Color color) {
		
		for (int i=0; i<indices.length; i++) {
			
			colors[indices[i]] = color;
			
		}
		
	}
	
}