package target_detection;

import math.MathUtils;

public class TargetDetection {
	
	/**
	 * 
	 * Checks if the cursor is inside the quad with the given size and position.
	 * 
	 * @param x The x coordinate of the quad.
	 * @param y The y coordinate of the quad.
	 * @param width The width of the quad.
	 * @param height The height of the quad.
	 * @param cursorX The x coordinate of the cursor.
	 * @param cursorY The y coordinate of the cursor.
	 * @return Returns true if the cursor is targeting the quad.
	 */
	public static boolean square(int x, int y, int width, int height, int cursorX, int cursorY) {
		if (cursorX < x || cursorX > x + width) {
			return false;
		}
		
		if (cursorY < y || cursorY > y + height) {
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * 
	 * Checks if the cursor is inside the rounded rectangle with the given size and position.
	 * 
	 * @param x The x coordinate of the rounded rectangle.
	 * @param y The y coordinate of the rounded rectangle.
	 * @param width The width of the rounded rectangle.
	 * @param height The height of the rounded rectangle.
	 * @param radius The radius of the circle that is used to draw the corners.
	 * @param cursorX The x coordinate of the cursor.
	 * @param cursorY The y coordinate of the cursor.
	 * @return Returns true if the cursor is targeting the rounded rectangle.
	 */
	public static boolean roundedRect(int x, int y, int width, int height, int radius, int cursorX, int cursorY) {
		if (square(x + radius, y, width - 2 * radius, height, cursorX, cursorY)) {
			return true;
		}
		
		if (square(x, y + radius, width, height - 2 * radius, cursorX, cursorY)) {
			return true;
		}
		
		if (cursorX < x + width / 2) {
			if (cursorY < y + height / 2) {
				return circle(x, y, 2 * radius, cursorX, cursorY);
			} else {
				return circle(x, y + height - 2 * radius, 2 * radius, cursorX, cursorY);
			}
		} else {
			if (cursorY < y + height / 2) {
				return circle(x + width - 2 * radius, y, 2 * radius, cursorX, cursorY);
			} else {
				return circle(x + width - 2 * radius, y + height - 2 * radius, 2 * radius, cursorX, cursorY);
			}
		}
	}
	
	
	/**
	 * 
	 * Checks if the cursor is inside the cricle with the given size and position.
	 * 
	 * @param x The x coordinate of the circle. This is NOT the x coordinate of the center!
	 * @param y The y coordinate of the circle. This is NOT the y coordinate of the center!
	 * @param diameter The diameter of the circle.
	 * @param cursorX The x coordinate of the cursor.
	 * @param cursorY The y coordinate of the cursor.
	 * @return Returns true if the cursor is targeting the circle.
	 */
	public static boolean circle(int x, int y, int diameter, int cursorX, int cursorY) {
		return ellipse(x, y, diameter, diameter, cursorX, cursorY);
	}
	
	
	/**
	 * 
	 * @param x The x coordinate of the ellipse. This is NOT the x coordinate of the center!
	 * @param y The y coordinate of the ellipse. This is NOT the y coordinate of the center!
	 * @param width The width of the ellipse.
	 * @param height The height of the ellipse.
	 * @param cursorX The x coordinate of the cursor.
	 * @param cursorY The y coordinate of the cursor.
	 * @return Returns true if the cursor is targeting the ellipse.
	 */
	public static boolean ellipse(int x, int y, int width, int height, int cursorX, int cursorY) {
		int radiusX = width / 2;
		int radiusY = height / 2;
		
		int centerX = x + radiusX;
		int centerY = y + radiusY;
		
		float v1 = MathUtils.square(cursorX - centerX) / MathUtils.square(radiusX);
		float v2 = MathUtils.square(cursorY - centerY) / MathUtils.square(radiusY);
		
		return v1 + v2 <= 1; 
	}

}
