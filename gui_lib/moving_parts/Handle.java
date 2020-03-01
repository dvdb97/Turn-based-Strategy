package moving_parts;

import assets.meshes.geometry.Color;
import fundamental.Element;
import gui_core.Input;
import math.MathUtils;
import math.vectors.Vector2f;
import rendering.shapes.implemented.GUIQuad;

public class Handle extends Element {
	
	private Vector2f min;
	private Vector2f max;
	private float range;
	
	private Vector2f position;
	
	public Handle(Color color, int width, int height, Vector2f startPos, Vector2f endPos) {
		super(new GUIQuad(color), width, height);
		
		this.min = startPos;
		this.position = min;
		this.max = endPos;
		this.range = new Vector2f(max.getA() - min.getA(), max.getB() - min.getB()).norm();
		
		addDragListener((Input input) -> move(input));
	}
	
	
	public Handle(Color color, float widthPercent, float heightPercent, Vector2f startPos, Vector2f endPos) {
		super(new GUIQuad(color), widthPercent, heightPercent);
		
		this.min = startPos;
		this.position = min;
		this.max = endPos;
		this.range = new Vector2f(max.getA() - min.getA(), max.getB() - min.getB()).norm();
		
		addDragListener((Input input) -> move(input));
	}
	
	
	private void move(Input input) {
		/**
		 * Calculate the new position of the handle by finding the intersection of
		 * the line from min to max and its perpendicular that goes through the cursor position.
		 * 
		 * (Lotfuﬂpunktverfahren)
		 */
		
		//The cursor position.
		Vector2f c = new Vector2f(input.cursorX, input.cursorY);
		
		//The vector between min and max.
		Vector2f ab = new Vector2f(max.getA() - min.getA(), max.getB() - min.getA()).normalized();
		
		//Compute the final value.
		float numerator = ab.dot(c) - ab.dot(min);
		float denominator = ab.dot(ab);
		float r = numerator / denominator;
		
		//Make sure that the final value lies between min and max.
		r = MathUtils.clamp(r, 0, range);
		
		//Compute the final position.
		position = new Vector2f(min.getA() + r * ab.getA(), min.getB() + r + min.getB());
	}


	@Override
	public int getLocalXPosition() {
		return super.getLocalXPosition() + (int)position.getA();
	}


	@Override
	public int getLocalYPosition() {
		return super.getLocalYPosition() + (int)position.getB();
	}
	
}
