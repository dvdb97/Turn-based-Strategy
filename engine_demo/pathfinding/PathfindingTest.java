package pathfinding;

import datastructures.Tuple;

public class PathfindingTest {
	
	public static void main(String[] args) {
		run();
	}
	
	
	private static void run() {		
		DemoBoard board = new DemoBoard(30, 30);	
		//board.display(AStarSearch.getPath(board, new Tuple<Integer, Integer>(0, 0), new Tuple<Integer, Integer>(10, 10)));
		board.display(AStarSearch.getReachableNodes(board, new Tuple<Integer, Integer>(13, 13), 20f));
	}

}
