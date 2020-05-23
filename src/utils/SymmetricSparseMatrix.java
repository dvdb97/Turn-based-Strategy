package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import world.Tile;
import world.agents.Agent;

public class SymmetricSparseMatrix {
	
	final int n;
	final int numel; // number of elements 
	
	HashMap<Integer, Integer> entries;
	
	//***************************** constructor ******************************
	
	/**
	 * Symmetric: getValue(i,j)==getValue(j,i)
	 * Sparse: majority of values are zero
	 * 
	 * @param n "width" of the matrix
	 * @param initialCapacity initial capacity of the underlying HashMap
	 */
	public SymmetricSparseMatrix(int n, int initialCapacity) {
		this.n = n;
		numel = n*n;
		entries = new HashMap<>(initialCapacity);
	}

	/**
	 * Symmetric: getValue(i,j)==getValue(j,i)
	 * Sparse: majority of values are zero
	 * Initial capacity of the underlying HashMap will be 16 (default for HashMaps)
	 * 
	 * @param m "height" of the matrix
	 * @param n "width" of the matrix
	 */
	public SymmetricSparseMatrix(int n) {
		this(n, 16);
	}
	
	//****************************** get & set *******************************
	
	/**
	 * 
	 * @param i must be in interval [0,m] (inclusive)
	 * @param j must be in interval [0,n] (inclusive)
	 * @param value 
	 */
	public void setValue(int i, int j, int value) {
		
		if (i<0 || i>=n || j<0 || j>=n)
			throw new IllegalArgumentException();
		
		if (value == 0) {
			if (entries.containsKey(i+j*numel)) {
				entries.remove(i+j*numel);
				entries.remove(j+i*numel);
			} else {
				return;
			}
		} else {
			entries.put(i+j*numel, value);
			entries.put(j+i*numel, value);
		}
	}
	
	/**
	 * 
	 * @param i must be in interval [0,m] (inclusive)
	 * @param j must be in interval [0,n] (inclusive)
	 * @return the value at (i,j). if there is no entry at this coordinates, zero (0) is returned ( because this is the default value of a sparse matrix)
	 */
	public int getValue(int i, int j) {

		if (i<0 || i>=n || j<0 || j>=n)
			throw new IllegalArgumentException();
		
		if (entries.containsKey(i+j*numel))
			return entries.get(i+j*numel);;
		return 0;
	}
	
//	public ArrayList<int[]> getEntriesByValue(int value) {
//		ArrayList<int[]> entryList = new ArrayList<>();
//		for (Iterator<Integer> iterator = entries.keySet().iterator(); iterator.hasNext(); ) {
//			Integer i = iterator.next();
//			if (entries.get(i) == value)
//				entryList.add(new int[] {i%n, i/n});
//		}
//		return entryList;
//	}
	
}