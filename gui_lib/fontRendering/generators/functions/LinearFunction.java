package fontRendering.generators.functions;

public class LinearFunction implements TextPositionFunction {
	
	private float totalGrowth;
	
	private float distanceBetweenLetters;
	
	
	public LinearFunction(float totalGrowth, float distanceBetweenLetters) {
		this.totalGrowth = totalGrowth;
		
		this.distanceBetweenLetters = distanceBetweenLetters;
	}
	

	@Override
	public float getX(char[] letters, float startX, float startY, float letterWidth, float letterHeight) {
		
		//TODO
		return 0;
		
	}

	@Override
	public float getY(char[] letters, float startX, float startY, float letterWidth, float letterHeight) {
		
		// TODO
		return 0;
		
	}

}
