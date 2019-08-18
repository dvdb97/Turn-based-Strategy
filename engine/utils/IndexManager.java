package utils;

import java.util.HashSet;

/**
 * 
 * This class can be used whenever you need to need to work with indices or IDs
 * that will change multiple times during runtime. It offers a simple interface
 * to reserve / free indices while ensuring that an index will never be used twice.
 * <br>
 * <br>
 * Use cases:
 * 
 * <ul>
 * 	<li> Instanced Rendering as the instances will be identified by their index. </li>
 * 	<li> Layout management using Yoga as all the children of a node will be identified by an index. </li>
 * </ul> 
 *
 * TODO: Limitless IndexManager.
 *
 */
public class IndexManager {
	
	private class IndexManagerOverflowException extends RuntimeException {}
	
	private int limit;
	
	private int pointer;
	private HashSet<Integer> unused;
	
	/**
	 * 
	 * @param limit The largest integer that can be used as an index.
	 */
	public IndexManager(int limit) {
		this.limit = limit;
		this.pointer = -1;
		this.unused = new HashSet<Integer>();
	}
	
	
	/**
	 * 
	 * @return Returns true if there are unused indices.
	 */
	public boolean indexAvailable() {
		return unusedIndices() != 0;
	}
	
	
	/**
	 * 
	 * @return Returns the number of unused indices.
	 */
	public int unusedIndices() {
		return limit - usedIndices();
	}
	
	
	/**
	 * 
	 * @return Returns the number of used indices.
	 */
	public int usedIndices() {
		return pointer + 1 - unused.size();
	}
	
	
	/**
	 * 
	 * @param index The index whoose availability to check.
	 * @return Returns true if the index is currently unused.
	 */
	public boolean isAvailable(int index) {
		return index > pointer || unused.contains(index);
	}
	
	
	/**
	 * 
	 * Reserves an index that is currently unused.
	 * 
	 * Throws an IndexManagerOverflowException if all indices are
	 * reserved.
	 * 
	 * @return Returns the reserved index.
	 */
	public int getNewIndex() {
		if (indexAvailable()) {
			if (!unused.isEmpty()) {
				return unused.iterator().next();
			}
			
			return ++pointer;
		}
		
		throw new IndexManagerOverflowException();
	}
	
	
	/**
	 * Recalculate the pointer by iterating backwards through 
	 * all indices to find the largest index that is currently used. 
	 */
	private void recalculatePointer() {
		for (int i = pointer - 1; i >= 0; --i) {
			if (!unused.contains(i)) {
				pointer = i;
				break;
			}
		}
	}
	
	
	/**
	 * 
	 * Frees an index making it available for later reservation.
	 * 
	 * Throws an IndexOutOfBoundsException if the index is to large to
	 * actually be an index that can be reserved.
	 * 
	 * @param index The index to free.
	 */
	public void freeIndex(int index) {
		//Check if the index is actually reserved.
		if (!isAvailable(index)) {
			if (index < limit) {
				if (index == pointer) {
					recalculatePointer();
				}
				
				unused.add(index);
			}
			
			throw new IndexOutOfBoundsException();
		}
	}

}
