package output;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import fontRendering.font.FontTexture;
import fontRendering.font.GUIFontCollection;
import fontRendering.generation.TextGenerator;
import fontRendering.rendering.TextModel;
import fundamental.Element;
import gui_core.GUIMatrixManager;
import gui_core.GUIShaderCollection;
import math.matrices.Matrix44f;
import rendering.RenderEngine;
import rendering.shapes.GUIQuad;
import rendering.shapes.Shape;
import styles.GUIConst.Position;

public class TextBox extends Element {
	
	private String labelText;
	
	private FontTexture font;
	
	private TextModel label;
	
	private Position labelPosition;
	
	private Matrix44f labelMatrix;
	
	
	public TextBox(Color color, GUIElementMatrix transformationMatrix, String text) {
		super(new GUIQuad(), color, transformationMatrix);
		
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
		
		font.bind();
		
		GUIShaderCollection.useFontShader(TM.toMatrix44f().times(labelMatrix), new Color(0f, 0f, 0f, 1f));
		
		RenderEngine.draw(label, font);
		
		GUIShaderCollection.disableFontShader();
		
		font.unbind();		
	}
	
	public void setLabel(String text) {
		
		if (text.equals(labelText)) {
			return;
		}
		
		if (text.equals("")) {
			return;
		}
		
		this.labelText = text;	
		
		this.label = TextGenerator.generateTextModel(labelText, null);
		
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
