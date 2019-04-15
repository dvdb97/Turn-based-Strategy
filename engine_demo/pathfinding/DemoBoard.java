package pathfinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import datastructures.Tuple;
import math.vectors.Vector4f;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.abs;

public class DemoBoard implements ITraversable<Tuple<Integer, Integer>, DemoUnit> {

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
	public List<Tuple<Integer, Integer>> getSuccessors(Tuple<Integer, Integer> node) {
		List<Tuple<Integer, Integer>> successors = new ArrayList<Tuple<Integer, Integer>>();
		
		if (node.x + 1 < width) {
			successors.add(new Tuple<Integer, Integer>(node.x + 1, node.y));
		}
		
		if (node.x - 1 >= 0) {
			successors.add(new Tuple<Integer, Integer>(node.x - 1, node.y));
		}
		
		if (node.y + 1 < height) {
			successors.add(new Tuple<Integer, Integer>(node.x, node.y + 1));
		}
		
		if (node.y - 1 >= 0) {
			successors.add(new Tuple<Integer, Integer>(node.x, node.y - 1));
		}
		
		return successors;
	}

	@Override
	public float getCosts(Tuple<Integer, Integer> start, Tuple<Integer, Integer> end) {
		return 0.5f * terrainCosts[board[start.y][start.y]] + 0.5f * terrainCosts[board[end.y][end.x]];
	}

	@Override
	public float getHeuristic(Tuple<Integer, Integer> start, Tuple<Integer, Integer> end) {
		return (float)abs(end.x - start.x) + (float)abs(end.y - start.y);
	}

	@Override
	public List<Tuple<Integer, Integer>> getSuccessors(Tuple<Integer, Integer> node, DemoUnit unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getCosts(Tuple<Integer, Integer> start, Tuple<Integer, Integer> end, DemoUnit unit) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeuristic(Tuple<Integer, Integer> start, Tuple<Integer, Integer> end, DemoUnit unit) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public void display() {		
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				System.out.print(" " + terrainTypes[board[y][x]] + " ");
			}
			
			System.out.println();
		}
	}
	
	public void display(Collection<Tuple<Integer, Integer>> tiles) {
		System.out.println(tiles);
		
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				Tuple<Integer, Integer> tile = new Tuple<Integer, Integer>(x, y);
				
				if (contains(tile, tiles)) {
					System.out.print("[" + terrainTypes[board[y][x]] + "]");
				} else {
					System.out.print(" " + terrainTypes[board[y][x]] + " ");
				}
			}
			
			System.out.println();
		}
	}
	
	
	public boolean contains(Tuple<Integer, Integer> tile, Collection<Tuple<Integer, Integer>> tiles) {
		for (Tuple<Integer, Integer> t : tiles) {
			if (t.x == tile.x && t.y == tile.y) {
				return true;
			}
		}
		
		return false;
	}

}
