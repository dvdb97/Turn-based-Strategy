package logics;

//this code is pretty much the same as "Generation" is the old version with tetragons
//TODO: the generator doesn't recognizes the end of a row

import utils.Const;

public class SeedGenerator {
	
	/************************************************************
	 * 															*
	 * 						thoughts							*
	 * 															*
	 ************************************************************/
	//This class will be instanced by the class Logics to
	//	deliver a byte array called seed, which has as many
	//	elements as there will be tiles on the game board; each
	//	byte representing the landscape of the according tile.
	//	(Every landscape has an ID written down in utils.Const).
	//Instead of picking a random landscape for every tile, we
	//	will follow the idea of biomes, to make our game board
	//	look more realistic. A Biome is an area wherein every
	//	tile has the same landscape.
	
	/************************************************************
	 * 															*
	 * 						fields								*
	 * 															*
	 ************************************************************/
	
	//Most important, the seed contains as many bytes as there
	//	will be tiles on the game board, each byte representing
	//	the landscape of the according tile.
	
	byte[] seed;
	
	//The following integers dimension the game board.
	//	Obviously the length of "byte[] seed" is width*height.
	
	int width, height;
	
	//How many biomes there are.
	
	int numBiomes;
	
	//Every biome has a center. In upcoming code the centers
	//	will set first, before the biomes will expand.
	//As mentioned every tile is represented by a byte in
	//	"byte[] seed". So every tile is allocated to an integer
	//	namely the position in "byte[] seed" called index.
	//This array contains the indices of the biomes' center
	
	int[] centers;
	
	//Biome after biomes a landscapeID is picked to determine
	//	the landscape of every tile of this biome.
	//For every landscape there is a certain changeable
	//	calculated probability, that its landscapeID gets picked
	//	("coast" is an exception, because they do not
	//	make up an entire biome).
	//The sum of all probabilities must be one.
	
	private double[] probs;
	
	//We use a (pseudo) random number between zero and one to
	//	calculate the landscapeID of a biome. To make use of
	//	"double[] probs" we define another array as follows:
	//	partialSums[i] = probs[0] + probs[1] + ... + probs[i];
	//Now our random number will be smaller than at least on
	//	partial sum. We will pick smallest one or rather the
	//	index of the smallest one as the landscapeID.
	
	private double[] partialSums;
	
	//After one landscapeID hast been picked we want it to be
	//	less likely to get picked again. There is a preset
	//	divisor the probability of this landscape is divided
	//	with, while the probability of every other landscapeID
	//	is increased such that the sum of all probabilites is
	//	equal to one.
	
	int probReductionDivisor;
	
	
	
	/****************************************************************
	 * 																*
	 * 						constructor								*
	 * 																*
	 ****************************************************************/
	public SeedGenerator() {
		
		//many fields can set up only in generatedSeed() because
		//	they depend on the width and height of the game board
		
		
		//here it should be mentioned that "Const.NUM_LANDSCAPES"
		//	does not consider "coast" as a landscape
		probs = new double[Const.NUM_LANDSCAPES];
		partialSums = new double[Const.NUM_LANDSCAPES];
	}
	
	/****************************************************************
	 * 																*
	 * 						ergon									*
	 * 																*
	 ****************************************************************/
	
	//this method is the reason for the existence of this class
	public byte[] generatedSeed(int width, int height) {
		
		//receive dimensions
		this.width = width;
		this.height = height;
		
		//create the byte array and set every element to the number
		//	of landscapes which means "no landscape so far". This
		//	will be necessary to determine if a tile already has
		//	a landscape or not.
		seed = new byte[width*height];
		for (int i=0; i<seed.length; i++) {
			seed[i] = Const.NUM_LANDSCAPES;
		}
		
		//formula to calculate the number of biomes
		//pretty much arbitrary, just tried out some values
		numBiomes = (int)((Math.random()*4+10)*Math.pow(width*height/100, 0.5));
		
		//avoid the case the number of biomes is zero. trivial.
		if (numBiomes == 0) {
			numBiomes = 1;
		}
		
		//since we know the number of biomes, we can create "int[] centers"
		centers = new int[numBiomes];
		
		//random positions for the centers of the biomes
		for (int b=0; b<numBiomes; b++) {
			
			centers[b] = (int) (Math.random()*width*height);
		}
		
		//this method picks a landscape for every biome and expands the biomes
		createBiomes();
		
		//coasts do not make up biomes, but are placed around water biomes
		addCoasts();
		
		//there are cases, where coast only touches water or other coasts
		//	then this tile should become a water tile
		removeUselessCoasts();
		
		//finally return the generated seed
		return seed;
	}
	
	/****************************************************************
	 * 																*
	 * 						main methods							*
	 * 																*
	 ****************************************************************/
	private void createBiomes() {
		
		//the probability is spread equally
		for (int c=0; c<probs.length; c++ ) {
			probs[c] = 1.0 / (Const.NUM_LANDSCAPES);
		}
		
		//set the probReductionDivisor to 2, but in case there are just few biomes
		//	increase it, because we want every biome, preferalby just one single time 
		if (numBiomes >= Const.NUM_LANDSCAPES) {
			probReductionDivisor = 2;
		} else {
			probReductionDivisor = 2*Const.NUM_LANDSCAPES/numBiomes;
		}
		
		
		//----------------------------------------------------------------------
		//one biome center after another
		//----------------------------------------------------------------------
		for (int b = 0; b < numBiomes; b++) {
			
			//sum up the probabilities anew because they will have changed
			updatePartialSums();
			
			//create a random number between zero and one
			double ran = Math.random();
			
			//pick the smallest landscapeID, which related partial sum is
			//	smaller than "double ran" and quit the loop
			for (int l=0; l < Const.NUM_LANDSCAPES; l++) {
				
				if(ran < partialSums[l]) {
					seed[centers[b]] = (byte)l;
					break;
				}
			}
			
			//to avoid exceptions set the landscapeID to 1 if not set yet
			if (seed[centers[b]] == Const.NUM_LANDSCAPES) {
				seed[centers[b]] = 1;
			}
			
			//update the probabilities according to which landscape was picked
			updateProbs(seed[centers[b]]);
		}
		//----------------------------------------------------------------------
		//now every biome has a landscape
		//----------------------------------------------------------------------
		
		
		//----------------------------------------------------------------------
		//extend biomes
		//----------------------------------------------------------------------
		
		//the radius of the biome, first set to 1
		int radius = 1;
		
		//exit the loop, when the radius is going to become greater than the
		//	dimensions of the game board because that would not make sense
		while (radius < width || radius < height) {
			
			//extend every biome equally
			//every biome will be different sized and shaped anyway (see code of
			//	extendBiome(...))
			for (int b=0; b<numBiomes; b++) {
				
				extendBiome(centers[b], radius);
			}
			
			//after increased the biomes' radius (theoretical) by one tile,
			//	increase radius, and do it again
			radius++;
		}
	}
	
	//----------------------------------------------------------------------
	
	private void addCoasts() {
		
		//go through every tile and check for non-water tiles, that are surrounded
		//	 nothing else than water
		for (int t=0; t<seed.length; t++) {
			
			if (seed[t] != 5 && waterNextTo(t)) {
				seed[t] = 6;
			}
		}
		
	}
	
	//----------------------------------------------------------------------
	
	private void removeUselessCoasts() {
		
		//check
		for (int t=0; t<seed.length; t++) {
			
			//if the coast is useless
			if (seed[t] == 6 && isUselessCoast(t)) {
				
				//useless coasts become water
				seed[t] = 5;
				
			}
		}
		
	}
	
	//----------------------------------------------------------------------
	
	private void extendBiome(int center, int radius) {
		
		//top left, then row after row
		for (int row = -radius; row <= radius; row++) {
			for (int col = -radius; col <= radius; col++) {
				
				int index = center-width*row+col;
				if (index >=0 && index < seed.length) {
					
					if (seed[index] == Const.NUM_LANDSCAPES) {
						//set tile
						seed[index] = seed[center];
					}
				}
			}
		}
		
	}
	
	//---------------------------------- used methods --------------------------------------
	
	private void updateProbs(int usedLS) {				//LS: landscape
		
		double tempChance = probs[usedLS];
		probs[usedLS] = probs[usedLS] / probReductionDivisor;
		
		for (int i = 0; i < probs.length; i++) {
			
			if (i != usedLS) {
				//chances[i] = chances[i] + (chances[i]/(1-tempChance)) * (tempChance/chanceReductionFactor);
				probs[i] = probs[i] + (tempChance-probs[usedLS])/(probs.length-1);
			}
		}
		
	}
	
	private void updatePartialSums() {
		
		for (int i = 0; i < partialSums.length; i++) {
			
			double partialSum = 0;
			
			for (int j = 0; j <= i; j++) {
				
				partialSum = partialSum + probs[j];
			}
			
			partialSums[i] = partialSum;
		}
	}
	
	private boolean waterNextTo(int index) {
		
		int[] surrounding = surroundingTiles(index);
		
		for (int s=0; s<surrounding.length; s++) {
			if (surrounding[s] >= 0 && surrounding[s] < width*height && seed[surrounding[s]] == 5) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isUselessCoast(int index) {
		
		int [] surrounding = surroundingTiles(index);
		
		for (int s=0; s<surrounding.length; s++) {
			
			//check if this tile exists
			if (surrounding[s] >= 0 && surrounding[s] < width*height) {
				
				//if the tile is next to at least one non-water/non-coast-tile its not a useless coast
				if (seed[surrounding[s]] != 5 && seed[surrounding[s]] != 6) {
					return false;
				}
			}
		}
		return true;
		
	}
	
	private int[] surroundingTiles(int tilesIndex) {
		
		int row = tilesIndex / width;
		
		return new int[] {	tilesIndex-width-1+(row%2), tilesIndex-width+(row%2), tilesIndex-1,
							tilesIndex+1, tilesIndex+width-1+(row%2), tilesIndex+width+(row%2)};
		
	}
	
}
