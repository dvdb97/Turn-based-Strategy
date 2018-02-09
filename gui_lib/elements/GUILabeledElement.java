package elements;

import assets.models.Element_Model;
import assets.textures.Texture2D;
import fontRendering.font.GUIFontCollection;
import fontRendering.font.texture.FontTexture;
import fontRendering.generation.TextGenerator;
import fontRendering.rendering.TextModel;
import gui_core.GUIShaderCollection;
import math.vectors.Vector4f;
import rendering.RenderEngine;
import rendering.shapes.GUIShape;

public abstract class GUILabeledElement extends GUIElement {
	
	private String labelText = "";
	private FontTexture font;
	private Element_Model label = null;		

	public GUILabeledElement(GUIShape shape, Texture2D texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);
		
		this.font = GUIFontCollection.getFont("NewTimesRoman");
	}
	
	public GUILabeledElement(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		
		this.font = GUIFontCollection.getFont("NewTimesRoman");
	}
	
	@Override
	public void render() {
		
		GUIShaderCollection.useGuiShader(this.getRenderingMatrix());
		
		//TODO: Only works when fonts have the same layout!
		RenderEngine.draw(label, font);
		
		GUIShaderCollection.disableGuiShader();
		
		super.render();
	}

	public void setLabel(String text) {
		
		if (text.equals(labelText)) {
			return;
		}
		
		this.labelText = text;	
		
		this.label = TextGenerator.generateTextModel(labelText, this.getX(), this.getY(), 1.0f, this.getWidth(), this.getHeight(), font, null);
		
	}
	
	
	public String getLabel() {
		return labelText;
	}
	
}
