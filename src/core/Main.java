package core;

import core.saves.StartParams;
import datastructures.trees.BinaryTree;

public class Main {
	
	public static void main(String[] args) {	
		//Application.init(new StartParams("Files/StartParameters"));
		
		int[] is = {1,3,2,5,4};
		BinaryTree<Integer, Integer> tree = new BinaryTree<Integer, Integer>();
		
		for (int i : is) {
			tree.insert(i, i);
		}
		
		System.out.println(tree);
	}
	
}

