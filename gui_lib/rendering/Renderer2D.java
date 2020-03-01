package rendering;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAddress;
import static org.lwjgl.system.MemoryUtil.memUTF8;
import static org.lwjgl.system.MemoryStack.*;

import java.nio.ByteBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NVGTextRow;
import org.lwjgl.system.MemoryStack;

import assets.meshes.geometry.Color;
import interaction.Window;
import math.vectors.Vector2f;
import utils.CustomBufferUtils;
import utils.FileUtils;

/**
 * 
 * The NanoVG API abstracted to be used for rendering a GUI.
 *
 */
public class Renderer2D {
	
	private static long nvg;
	private static boolean initialized = false;
	
	private static NVGColor strokeColor, fillColor, startColor, endColor;
	private static NVGPaint gradientPaint;
	
	private static HashMap<String, Integer> fonts;
	
	public static void init() {
		nvg = nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES);
		
		if (nvg == NULL) {
			throw new RuntimeException("Could not init nvg!");
		}
		
		strokeColor = NVGColor.create();
		fillColor = NVGColor.create();
		startColor = NVGColor.create();
		endColor = NVGColor.create();
		gradientPaint = NVGPaint.create();
		
		fonts = new HashMap<String, Integer>();
		
		loadFont("FreeMono", "res/fonts/FreeMono.TTF");
		
		initialized = true;
	}
	
	
	public static void delete() {
		strokeColor.free();
		fillColor.free();
		startColor.free();
		endColor.free();
		gradientPaint.free();
		
		nvgDelete(nvg);
		initialized = false;
	}
	
	
	public static void loadFont(String name, String path) {
		int fontID = nvgCreateFont(nvg, name, path);
		
		if (fontID == -1) {
			System.err.println("Could not load font " + name + "!");
		}
		
		fonts.put(name, fontID);
	}
	
	
	public static void beginFrame(Window window) {
		assert initialized : "Renderer2D not initialized!";
		
		nvgBeginFrame(nvg, window.getWidth(), window.getHeight(), 1);
	}
	
	
	public static void endFrame() {
		assert initialized : "Renderer2D not initialized!";
		
		nvgEndFrame(nvg);
	}
	
	
	public static void beginPath() {
		assert initialized : "Renderer2D not initialized!";
		
		nvgBeginPath(nvg);
	}
	
	
	/**
	 * Pushes and saves the current render state into a state stack.
	 * Use {@link #restoreState() restoreState} to restore this state.
	 */
	public static void saveState() {
		nvgSave(nvg);
	}
	
	
	/**
	 * Pops and restores the current render state.
	 */
	public static void restoreState() {
		nvgRestore(nvg);
	}
	
	
	/**
	 * 
	 * Clip the rendering into a rectangle defined by the parameters.
	 * 
	 * @param x The x coordinate of the clipping window.
	 * @param y The y coordinate of the clipping window.-
	 * @param width The width of the clipping window.
	 * @param height The height of the clipping window.
	 */
	public static void scissor(int x, int y, int width, int height) {
		nvgScissor(nvg, x, y, width, height);
	}
	
	
	/**
	 * 
	 * Reset and disable scissoring. 
	 */
	public static void resetScissor() {
		nvgResetScissor(nvg);
	}
	
	
	////////////////////////////////////////////////////////////////
	//////////////////////////// Shapes ////////////////////////////
	////////////////////////////////////////////////////////////////
	
	
	/**
	 * 
	 * Set the current start point from which the next line will be drawn.
	 * 
	 * @param x The x position of the point.
	 * @param y The y position of the point.
	 */
	public static void moveTo(int x, int y) {
		nvgMoveTo(nvg, x, y);
	}
	
	
	/**
	 * 
	 * Draws a line from the last point to a new point with the given position.
	 * 
	 * @param x The x position of the end point.
	 * @param y The y position of the end point.
	 */
	public static void lineTo(int x, int y) {
		nvgLineTo(nvg, x, y);
	}
	
	
	/**
	 * Closes the path by drawing a line from the last end point to the first point. 
	 */
	public static void closePath() {
		nvgClosePath(nvg);
	}
	
	
	/**
	 * 
	 * Draws a rectangle onto the screen.
	 * 
	 * @param x The x coordinate of the rectangle.
	 * @param y The y coordinate of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 */
	public static void rect(int x, int y, int width, int height) {
		assert initialized : "Renderer2D not initialized!";
	
		nvgRect(nvg, x, y, width, height);
	}
	
	
	public static void image(int x, int y, int width, int height) {
		assert initialized : "Renderer2D not initialized!";
	
		//TODO: Add rendering for textures.
	}
	
	
	/**
	 * 
	 * Draws an ellipse onto the screen.
	 * 
	 * @param x The x coordinate of the ellipse. This is not the x coordinate of the center!
	 * @param x The y coordinate of the ellipse. This is not the y coordinate of the center!
	 * @param radiusX The x radius of the ellipse.
	 * @param radiusY The y radius of the ellipse.
	 */
	public static void ellipse(int x, int y, int radiusX, int radiusY) {
		assert initialized : "Renderer2D not initialized!";
	
		nvgEllipse(nvg, x + radiusX, y + radiusY, radiusX, radiusY);
	}
	
	
	/**
	 * 
	 * Draws a circle onto the screen.
	 * 
	 * @param x The x coordinate of the circle.
	 * @param y The y coordinate of the circle.
	 * @param radius The radius of the circle.
	 */
	public static void circle(int x, int y, int radius) {
		ellipse(x, y, radius, radius);
	}
	
	
	/**
	 * 
	 * Draws a rounded rectangle onto the screen.
	 * 
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 * @param radius The corner radius.
	 */
	public static void roundedRect(int x, int y, int width, int height, int radius) {
		assert initialized : "Renderer2D not initialized!";
	
		nvgRoundedRect(nvg, x, y, width, height, radius);
	}
	
	
	/**
	 * 
	 * @param x The x coordinate of the upper left corner.
	 * @param y The y coordinate of the upper left corner.
	 * @param text The text to render.
	 * @param font The font to use for rendering.
	 * @param color The text color.
	 * @param size The font size.
	 */
	public static void text(int x, int y, String text, String font, Color color, int size) {		
		nvgTextAlign(nvg, NVG_ALIGN_TOP | NVG_ALIGN_LEFT);
		nvgFontSize(nvg, size);
		nvgFontFace(nvg, font);
		Renderer2D.fillStyle(color);
		
		ByteBuffer textUtf8 = memUTF8(text, false);
		nvgText(nvg, x, y, textUtf8);
	}
	
	
	/**
	 * 
	 * The text is drawn on multiple lines if it is too long to
	 * fit in a single line.
	 * 
	 * @param x The x coordinate of the upper left corner.
	 * @param y The y coordinate of the upper left corner.
	 * @param width The maximum width of a line.
	 * @param text The text to render.
	 * @param The font to use for rendering.
	 * @param color The text color.
	 * @param size The font size.
	 */
	public static void multilineText(int x, int y, int width, String text, String font, Color color, int size) {
		nvgTextAlign(nvg, NVG_ALIGN_TOP | NVG_ALIGN_LEFT);
		nvgFontSize(nvg, size);
		nvgFontFace(nvg, font);
		Renderer2D.fillStyle(color);
		
		ByteBuffer textUtf8 = memUTF8(text, false);
		nvgTextBox(nvg, x, y, width, textUtf8);
	}
	
	
	///////////////////////////////////////////////////////////////
	//////////////////////////// Style ////////////////////////////
	///////////////////////////////////////////////////////////////
	
	
	private static NVGColor rgba(Color color, NVGColor nvgColor) {
		return nvgRGBAf(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), nvgColor);
	}
	
	
	/**
	 * 
	 * Set the stroke style.
	 * 
	 * @param color The color of the stroke.
	 * @param width The width of the stroke.
	 */
	public static void strokeStyle(Color color, float width) {
		assert initialized : "Renderer2D not initialized!";
	
		nvgStrokeColor(nvg, rgba(color, strokeColor));
		nvgStrokeWidth(nvg, width);
	}
	
	
	public static void stroke() {
		assert initialized : "Renderer2D not initialized!";
	
		nvgStroke(nvg);
	}
	
	
	public static void stroke(Color color, float width) {
		strokeStyle(color, width);
		stroke();
	}
	
	
	public static void fillStyle(Color color) {
		assert initialized : "Renderer2D not initialized!";
		
		nvgFillColor(nvg, rgba(color, fillColor));
	}
	
	
	public static void fill() {
		nvgFill(nvg);
	}
	
	
	public static void fill(Color color) {
		fillStyle(color);
		fill();
	}
	
	
	public static void linearGradient(Vector2f start, Vector2f end, Color c0, Color c1) {
		nvgLinearGradient(nvg, start.getA(), start.getB(), end.getA(), end.getB(), 
						  rgba(c0, startColor), rgba(c1, endColor), gradientPaint);
		nvgFillPaint(nvg, gradientPaint);
	}
	
	
	///////////////////////////////////////////////////////////////
	/////////////////////// Transformations ///////////////////////
	///////////////////////////////////////////////////////////////
	
	
	public static void rotate(float angle) {
		nvgRotate(nvg, angle);
	}
	
	
	public static float toRadians(float degree) {
		return nvgDegToRad(degree);
	}
	
	
	public static float toDegree(float radians) {
		return nvgRadToDeg(radians);
	}
	
	
	public static void translate(float x, float y) {
		nvgTranslate(nvg, x, y);
	}
	
}
