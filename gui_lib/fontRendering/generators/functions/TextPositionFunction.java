package fontRendering.generators.functions;

/*
 * An Interface that is used to generate meshes to render font on.
 * 
 * Potential implementations will be bézier curves, functions, etc.
 * 
 */
public interface TextPositionFunction {
	
	public float getX(char[] letters, float startX, float startY, float letterWidth, float letterHeight /* Add necesary parameters... */);
	
	public float getY(char[] letters, float startX, float startY, float letterWidth, float letterHeight/* Add necesary parameters... */);

}
