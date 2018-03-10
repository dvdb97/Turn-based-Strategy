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
	
	
	@Override
	public void render() {
		
		super.render();
		
		if(label == null) {
			return;
		}
		
		GUIShaderCollection.useFontShader(this.labelMatrix);
		
		RenderEngine.draw(label, font);
		
		GUIShaderCollection.disableFontShader();
		
	}
	

	@Override
	public void update() {
		super.update();
	}


	public void setLabel(String text) {
		
		if (text.equals(labelText)) {
			return;
		}
		
		this.labelText = text;	
		
		this.label = TextGenerator.generateTextModel(labelText, font, null);
		
		this.labelMatrix = GUIMatrixManager.generateRenderingMatrix(0f, 0f, 1f / label.getMaxCharsPerRow(), 1f / label.getMaxCharsPerRow());		
		
	}
	
	
	public String getLabel() {
		return labelText;
	}
	
	
	public void setLabelPosition(Position position) {
		
	}
	
	
	public void setFont(String name) {
		this.font = GUIFontCollection.getFont(name);		
	}
	
}
