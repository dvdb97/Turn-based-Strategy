package models.seeds.noise;


public class TrigonalNoise {
	
	/************************************************************
	 * 															*
	 * 						variables							*
	 * 															*
	 ************************************************************/
	//dimension of the map
	private int length;
	private int width;
	
	//----------------------------------------------------------------------
	//perlin noise related
	
	//the actual values at each knot written in an array
	
	private float[][] values;
	
	//the value of each knot is the sum of many noises with different frequencies.
	//	the frequency will be doubled each iteration. Because an octave of a f. is
	//	this f. doubled, we call the number of frequency duplications maxOctave.
	
	private int minOctave;
	private int maxOctave;
	
	//reusable array sized like "float[] values" where data is temporary written in
	
	private float[][] temp;
	
	
	
	/************************************************************
	 * 															*
	 * 						constructor							*
	 * 															*
	 ************************************************************/
	public TrigonalNoise(int length, int width, int minOctave, int maxOctave) {
		
		this.length  = length;
		this.width = width;
		
		this.maxOctave = maxOctave;
		this.minOctave = minOctave;
		
		values = new float[length][width];
		temp = new float[length][width];
		createPerlinNoise();
		
		norm();
		
	}
	
	
	/************************************************************
	 * 															*
	 * 						ergon								*
	 * 															*
	 ************************************************************/
	private void createPerlinNoise() {
		
		int wavelength = (int)Math.pow(2, minOctave);
		
		for (int octave = minOctave; octave<=maxOctave; octave++) {
			
			setArray(temp, 0);
			
			createNoise(wavelength);
			
			//add temp to values
			for (int i=0; i<length; i++) {
				for (int j=0; j<width; j++) {
					
					values[i][j] += temp[i][j]*wavelength;
				}
			}
			
			wavelength *= 2;
			
		}
		
	}
	
	private void norm() {
		
		//every value should lay between one and zero
		
		float a = (float)Math.pow(2, maxOctave+1) - (float)Math.pow(2, minOctave) + maxOctave - minOctave;
		
		for (int i=0; i<length; i++) {
			for (int j=0; j<width; j++) {
				
				values[i][j] /= a;
				
			}
		}
	}
	
	/************************************************************
	 * 															*
	 * 						prime methods						*
	 * 															*
	 ************************************************************/
	private void createNoise(int wavelength) {
		
		//a knot's position in "float[] temp"
		int x, y;
		
		//note: the critical knots (knots a values is given/non-critical knots get
		//	their values through interpolation) are labeled with the coords k and i
		for (int k=0; k<length/wavelength; k++) {
			for(int i=0; i<width/wavelength; i++) {
				
				//this formula calculates the position of the critical knot depending
				//	on its coords
				x = wavelength*k;				
				y = (int)(wavelength*(i + (k%2)*0.5));
				
				if (x<length && y<width) {
					addCone(x, y, wavelength);
				}
			}
		}
		
	}
	
	private void addCone(int x, int y, int wavelength) {
		
		float ran = (float)Math.random();
		
		int startRow = 1-wavelength;
		int numRows = 2*wavelength-1;
		
		for (int col=0; col<wavelength; col++) {
			
			int factor = 0;
			int extraFactor = wavelength-col;
			
			for (int dRow=0; dRow<wavelength; dRow++) {
				
				if (factor < extraFactor) {
					factor++;
				}
				
				if (x+col >= 0 && x+col < length && y+startRow+dRow >= 0 && y+startRow+dRow < width) {
					temp[x+col][y+startRow+dRow] += factor*ran/wavelength;
				}
				if (col != 0 && x-col >= 0 && x-col < length && y+startRow+dRow >= 0 && y+startRow+dRow < width) {
					temp[x-col][y+startRow+dRow] += factor*ran/wavelength;
				}
				
			}
			for (int dRow=wavelength; dRow<numRows; dRow++) {
				
				//TODO: get rid of this if
				if (factor>0) {
					factor--;
				}
				
				if (x+col >= 0 && x+col < length && y+startRow+dRow >= 0 && y+startRow+dRow < width) {
					temp[x+col][y+startRow+dRow] += factor*ran/wavelength;
				}
				if (col != 0 && x-col >= 0 && x-col < length && y+startRow+dRow >= 0 && y+startRow+dRow < width) {
					temp[x-col][y+startRow+dRow] += factor*ran/wavelength;
				}
				
			}
			
			//------------------
			numRows--;
			if (col%2 == 1) {
				startRow++;
			}
			
		}
		
	}
	
	
	
	/************************************************************
	 * 															*
	 * 						util methods						*
	 * 															*
	 ************************************************************/
	private void setArray(float[][] array, float value) {
		
		for (int i=0; i<array.length; i++) {
			for (int j=0; j<array[i].length; j++) {
				
				array[i][j] = value;
				
			}
		}
		
	}
	
	/************************************************************
	 * 															*
	 * 						getter								*
	 * 															*
	 ************************************************************/
	public float getValue(int x, int y) {
		
		return values[x][y];
		
	}
}
