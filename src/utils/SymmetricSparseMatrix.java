package utils;

import java.util.HashMap;

import javax.print.attribute.IntegerSyntax;

public class SymmetricSparseMatrix {
	
	private final int defaultValue = 0;
	
	private final int n;
	private final int numel; // number of elements 
	
	private HashMap<Integer, Integer> entries;
	
	//***************************** constructor ******************************

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
	
	/**
	 * Symmetric: getValue(i,j)==getValue(j,i)
	 * Sparse: majority of values are zero
	 * 
	 * @param n "width" of the matrix
	 * @param initialCapacity initial capacity of the underlying HashMap
	 */
	public SymmetricSparseMatrix(int n, int initialCapacity) {
		this(n, initialCapacity, 0.75f);
	}
	
	/**
	 * Symmetric: getValue(i,j)==getValue(j,i)
	 * Sparse: majority of values are zero
	 * 
	 * @param n "width" of the matrix
	 * @param initialCapacity initial capacity of the underlying HashMap
	 * @param loadFactor load factor of the underlying HashMap
	 */
	public SymmetricSparseMatrix(int n, int initialCapacity, float loadFactor) {
		this.n = n;
		numel = n*n;
		entries = new HashMap<>(initialCapacity, loadFactor);
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
			throw new IllegalArgumentException("i: "+i+" j: "+j+" n: "+n);
		
		if (value == defaultValue) {
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
	 * @return the value at (i,j). if there is no entry at this coordinates, the default value (probably zero) is returned
	 */
	public int getValue(int i, int j) {

		if (i<0 || i>=n || j<0 || j>=n)
			throw new IllegalArgumentException();
		
		return entries.getOrDefault(i+j*numel, defaultValue);
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
	
	
	public HashMap<Integer, Integer> getRow(int i) {
		
		HashMap<Integer, Integer> sparseRowVector = new HashMap<>();
		for  (int k=0; k<n; k++) {
			int value = entries.getOrDefault(i+k*numel, defaultValue);
			if (value != defaultValue) {
				sparseRowVector.put(k, value);
			}
		}
		
		return sparseRowVector;
		
	}
	
	/**
	 * @return the number of rows/columns
	 */
	public int getN() {
		return n;
	}
	
	/**
	 * @return the total number of elements of this matrix = numRows * numCols
	 */
	public int getNumel() {
		return numel;
	}
	
	/**
	 * @return default value (probably zero)
	 */
	public int getDefaultValue() {
		return defaultValue;
	}
}