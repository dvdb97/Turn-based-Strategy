package elements;

import assets.textures.Texture2D;
import fontRendering.font.GUIFontCollection;
import fontRendering.font.FontTexture;
import fontRendering.generation.TextGenerator;
import fontRendering.rendering.TextModel;
import gui_core.GUIMatrixManager;
import gui_core.GUIShaderCollection;
import math.matrices.Matrix44f;
import math.vectors.Vector4f;
import rendering.RenderEngine;
import rendering.shapes.GUIShape;

import static styles.GUIConst.*;

public abstract class GUILabeledElement extends GUIElement {
	
	private String labelText = "";
	
	private FontTexture font = null;
	
	private TextModel label = null;
	
	private Position labelPosition = Position.CENTER;
	
	private Matrix44f labelMatrix;
	

	public GUILabeledElement(GUIShape shape, Texture2D texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);
		
		this.setFont("NewTimesRoman");
	}
	
	
	public GUILabeledElement(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		
		this.setFont("NewTimesRoman");
	}
	
	
	public GUILabeledElement(GUIShape shape, Texture2D texture, float x, float y, float width, float height, String text) {
		this(shape, texture, x, y, width, height);
		
		this.setLabel(text);
	}
	
	
	public GUILabeledElement(GUIShape shape, Vector4f color, float x, float y, float width, float height, String text) {
		this(shape, color, x, y, width, height);
		
		this.setLabel(text);
	}
	
	
	@Override
	public void render() {
		
		super.render();
		
		if(label == null) {
			return;
		}
		
		GUIShaderCollection.useFontShader(this.getRenderingMatrix().times(this.labelMatrix));
		
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
	
	
	public String getLabel() {
		return labelText;
	}
	
	
	public void setLabelPosition(Position position) {
		this.labelPosition = position;
		
		generateLabelMatrix();
	}
	
	
	//Generates a matrix that positions the label according to the current state of "position"
	private void generateLabelMatrix() {
		if (labelPosition == Position.CENTER || labelPosition  == Position.LEFT || labelPosition == Position.RIGHT) {
			this.labelMatrix = GUIMatrixManager.generateRenderingMatrix(0f, -this.getHeight() / 2, 1f / label.getMaxCharsPerRow(), 1f / label.getMaxCharsPerRow());
		}
		
		if (labelPosition == Position.TOP) {
			this.labelMatrix = GUIMatrixManager.generateRenderingMatrix(0f, -0.05f, 1f / label.getMaxCharsPerRow(), 1f / label.getMaxCharsPerRow());
		}
		
		if (labelPosition == Position.BOTTOM) {
			this.labelMatrix = GUIMatrixManager.generateRenderingMatrix(0f, -this.getHeight(), 1f / label.getMaxCharsPerRow(), 1f / label.getMaxCharsPerRow());
		}
	}
	
	
	public void setFont(String name) {
		this.font = GUIFontCollection.getFont(name);		
	}
	
}
