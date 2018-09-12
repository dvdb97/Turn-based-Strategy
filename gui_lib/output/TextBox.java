package output;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import fontRendering.legacy.font.FontTexture;
import fontRendering.legacy.font.GUIFontCollection;
import fontRendering.legacy.generation.TextGenerator;
import fontRendering.legacy.rendering.TextModel;
import fundamental.Element;
import gui_core.GUIMatrixManager;
import gui_core.GUIShaderCollection;
import math.matrices.Matrix44f;
import rendering.RenderEngine;
import rendering.shapes.Shape;
import styles.GUIConst.Position;

public class TextBox extends Element {
	
	private String labelText;
	
	private FontTexture font;
	
	private TextModel label;
	
	private Position labelPosition;
	
	private Matrix44f labelMatrix;
	
	
	public TextBox(Shape shape, Color color, GUIElementMatrix transformationMatrix, String text) {
		super(shape, color, transformationMatrix);
		
		labelText = "";
		font = GUIFontCollection.getFont("Times New Roman");
		label = null;
		labelPosition = Position.CENTER;
		setLabel(text);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	//from GUILabeledElement
	@Override
	public void render() {
		
		super.render();
		
		if(label == null) {
			return;
		}
		
		GUIShaderCollection.useFontShader(TM.toMatrix44f().times(labelMatrix));
		
		RenderEngine.draw(label, font);
		
		GUIShaderCollection.disableFontShader();
		
	}
	
	public void setLabel(String text) {
		
		if (text.equals(labelText)) {
			return;
		}
		
		if (text.equals("")) {
			return;
		}
		
		this.labelText = text;	
		
		this.label = TextGenerator.generateTextModel(labelText, font, null);
		
		generateLabelMatrix();
		
	}
	
	private void generateLabelMatrix() {
		if (labelPosition == Position.CENTER || labelPosition  == Position.LEFT || labelPosition == Position.RIGHT) {
			this.labelMatrix = GUIMatrixManager.generateTransformationMatrix44(0f, -this.elementMatrix.getYStretch() / 2, 1f / label.getMaxCharsPerRow(), 1f / label.getMaxCharsPerRow());
		}
		
		if (labelPosition == Position.TOP) {
			this.labelMatrix = GUIMatrixManager.generateTransformationMatrix44(0f, -0.05f, 1f / label.getMaxCharsPerRow(), 1f / label.getMaxCharsPerRow());
		}
		
		if (labelPosition == Position.BOTTOM) {
			this.labelMatrix = GUIMatrixManager.generateTransformationMatrix44(0f, -this.elementMatrix.getYStretch(), 1f / label.getMaxCharsPerRow(), 1f / label.getMaxCharsPerRow());
		}
	}
	
	//////////
	
}
