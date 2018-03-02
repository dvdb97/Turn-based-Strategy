package elements;

import assets.models.Element_Model;
import assets.textures.Texture2D;
import fontRendering.font.GUIFontCollection;
import fontRendering.font.FontTexture;
import fontRendering.generation.TextGenerator;
import gui_core.GUIShaderCollection;
import math.vectors.Vector4f;
import rendering.RenderEngine;
import rendering.shapes.GUIShape;

public abstract class GUILabeledElement extends GUIElement {
	
	private String labelText = "";
	
	private FontTexture font = null;
	
	private Element_Model label = null;
	

	public GUILabeledElement(GUIShape shape, Texture2D texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);
		
		this.font = GUIFontCollection.getFont("NewTimesRoman");
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
		
		GUIShaderCollection.useFontShader(this.getRenderingMatrix());
		
		RenderEngine.draw(label, font);
		
		GUIShaderCollection.disableFontShader();
		
	}
	

	public void setLabel(String text) {
		
		if (text.equals(labelText)) {
			return;
		}
		
		this.labelText = text;	
		
		this.label = TextGenerator.generateTextModel(labelText, this.getX(), this.getY(), -0.9f, 0.1f, this.getWidth(), this.getWidth() / labelText.length(), font, null);
		
	}
	
	
	public String getLabel() {
		return labelText;
	}
	
	
	public void setFont(String name) {
		this.font = GUIFontCollection.getFont(name);		
	}
	
}
