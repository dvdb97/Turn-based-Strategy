package pathfinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;

import math.vectors.Vector4f;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.abs;

public class DemoBoard implements ITraversable<Integer, DemoUnit> {

	private char[] terrainTypes = {
		'~', //Water
		'_', //Grasland
		'm', //Forest
		'M', //mountains
	};
	
	private float[] terrainCosts = {
		3f,  //Water
		1f,  //Grasland
		2f,//Forest
		4f   //Mountains
	};
	
	private int[][] board;
	
	private int width;
	private int height;
	
	public DemoBoard(int width, int height) {
		board = new int[height][width];
		
		this.width = width;
		this.height = height;
		
		generate(10f);
	}
	
	private void generate(float weight) {
		Random random = new Random();
		
		int waterX = random.nextInt(width);
		int waterY = random.nextInt(height);
		
		int graslandX = random.nextInt(width);
		int graslandY = random.nextInt(height);
		
		int forestX = random.nextInt(width);
		int forestY = random.nextInt(height);
		
		int mountainX = random.nextInt(width);
		int mountainY = random.nextInt(height);
		
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				
				//Compute the Manhattan Distances between the current tile and the terrain cores.
				Vector4f distances = new Vector4f((float)sqrt(pow(x - waterX, 2) + pow(y - waterY, 2)), 
												  (float)sqrt(pow(x - graslandX, 2) + pow(y - graslandY, 2)), 
												  (float)sqrt(pow(x - forestX, 2) + pow(y - forestY, 2)),
												  (float)sqrt(pow(x - mountainX, 2) + pow(y - mountainY, 2)));
				
				distances.times(1f / (distances.getA() + distances.getB() + distances.getC() + distances.getD()));
				
				Vector4f probabilities = new Vector4f(1f, 1f, 1f, 1f).minus(distances);
				
				probabilities.setA((float)pow(probabilities.getA(), weight));
				probabilities.setB((float)pow(probabilities.getB(), weight));
				probabilities.setC((float)pow(probabilities.getC(), weight));
				probabilities.setD((float)pow(probabilities.getD(), weight));
				
				probabilities.times(1f / (probabilities.getA() + probabilities.getB() + probabilities.getC() + probabilities.getD()));				
				float[] probabilityArray = probabilities.toArray();
				
				float prob = random.nextFloat();
				float lowerBoundary = 0f;
				
				for (int i = 0; i < probabilityArray.length; ++i) {
					float upperBoundary = lowerBoundary + probabilityArray[i];
					
					if (prob > lowerBoundary && prob < upperBoundary) {
						board[y][x] = i;
					}
					
					lowerBoundary = upperBoundary;
				}
			}
		}
	}
	
	
	@Override
	public List<Integer> getSuccessors(Integer node) {
		List<Integer> successors = new ArrayList<Integer>();
		
		int x = node % width;
		int y = node / height;
		
		if (x + 1 < width) {
			successors.add(y * width + x + 1);
		}
		
		if (x - 1 >= 0) {
			successors.add(y * width + x - 1);
		}
		
		if (y + 1 < height) {
			successors.add((y + 1) * width + x);
		}
		
		if (y - 1 >= 0) {
			successors.add((y - 1) * width + x);
		}
		
		return successors;
	}

	@Override
	public float getCosts(Integer start, Integer end) {
		int startX = start % width;
		int startY = start / width;
		
		int endX = end % width;
		int endY = end / width;
		
		return 0.5f * terrainCosts[board[startY][startX]] + 0.5f * terrainCosts[board[endY][endX]];
	}

	@Override
	public float getHeuristic(Integer start, Integer end) {
		int startX = start % width;
		int startY = start / width;
		
		int endX = end % width;
		int endY = end / width;
		
		return (float)abs(endX - startX) + (float)abs(endY - startY);
	}

	@Override
	public List<Integer> getSuccessors(Integer node, DemoUnit unit) {
		return getSuccessors(node);
	}

	@Override
	public float getCosts(Integer start, Integer end, DemoUnit unit) {
		int startX = start % width;
		int startY = start / width;
		
		int endX = end % width;
		int endY = end / width;
		
		float startWeight = getCosts(board[startY][startX], unit);
		float endWeight = getCosts(board[endY][endX], unit);
		
		return 0.5f * startWeight + 0.5f * endWeight;
	}
	
	private float getCosts(int type, DemoUnit unit) {
		switch (type) {
			case 0:
				return unit.getWater();
			case 1:
				return unit.getGrasland();
			case 2:
				return unit.getForest();
			case 3:
				return unit.getMountains();
			default:
				return 1f;
		}
	}

	@Override
	public float getHeuristic(Integer start, Integer end, DemoUnit unit) {
		return getHeuristic(start, end);
	}
	
	
	public void display() {		
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				System.out.print(" " + terrainTypes[board[y][x]] + " ");
			}
			
			System.out.println();
		}
	}
	
	public void display(Collection<Integer> tiles) {
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				int tile = y * width + x;
				
				if (tiles.contains(tile)) {
					System.out.print("[" + terrainTypes[board[y][x]] + "]");
				} else {
					System.out.print(" " + terrainTypes[board[y][x]] + " ");
				}
			}
			
			System.out.println();
		}
	}
	
}
