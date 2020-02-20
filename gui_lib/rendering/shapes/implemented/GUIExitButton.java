package rendering.shapes.implemented;

import assets.meshes.geometry.Color;
import math.MathUtils;
import rendering.Renderer2D;
import rendering.shapes.GUIShape;
import target_detection.TargetDetection;

import static math.MathUtils.*;

public class GUIExitButton extends GUIShape {

	private Color xColor;
	
	public GUIExitButton(Color color, Color xColor) {
		super(color);
		
		this.xColor = xColor;
	}

	@Override
	public void render(int x, int y, int width, int height) {
		Renderer2D.beginPath();
		Renderer2D.rect(x, y, width, height);
		Renderer2D.fill(getColor());
		
		int outerCornerOffset = (int)(Math.sin(Renderer2D.toRadians(45)) * height / 6);
		
		int beamLength = (int)sqrt(2 * square(width - outerCornerOffset));
		int innerCornerOffset = (int)(Math.sin(Renderer2D.toRadians(45)) * ((beamLength - height / 6) / 2));
				
		Renderer2D.beginPath();
		Renderer2D.moveTo(x + outerCornerOffset, y);
		Renderer2D.lineTo(x + width / 2, y + innerCornerOffset);
		Renderer2D.lineTo(x + width - outerCornerOffset, y);
		Renderer2D.lineTo(x + width, y + outerCornerOffset);
		Renderer2D.lineTo(x + width - innerCornerOffset, y + height / 2);
		Renderer2D.lineTo(x + width, y + height - outerCornerOffset);
		Renderer2D.lineTo(x + width - outerCornerOffset, y + height);
		Renderer2D.lineTo(x + width / 2, y + height - innerCornerOffset);
		Renderer2D.lineTo(x + outerCornerOffset, y + height);
		Renderer2D.lineTo(x, y + height - outerCornerOffset);
		Renderer2D.lineTo(x + innerCornerOffset, y + height / 2);
		Renderer2D.lineTo(x, y + outerCornerOffset);
		Renderer2D.closePath();
		Renderer2D.fill(xColor);
	}

	@Override
	public boolean isTargeted(int x, int y, int width, int height, int cursorX, int cursorY) {
		return TargetDetection.square(x, y, width, height, cursorX, cursorY);
	}

}
