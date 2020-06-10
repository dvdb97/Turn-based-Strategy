package pathfinding;

public class UnionFind {
	
	private int num;
	private int[] id;
	private int[] sz;
	
	/**
	 * 
	 * @param num The number of nodes to work with.
	 */
	public UnionFind(int num) {
		this.num = num;
		
		this.id = createIDs(num);
		this.sz = new int[num];
	}
	
	
	private int[] createIDs(int num) {
		int[] id = new int[num];
		
		for (int i = 0; i < num; i++) {
			id[i] = i;
		}
		
		return id;
 	}
	
	
	/**
	 * 
	 * Connect nodes i and j.
	 * 
	 * @param i The id of the first node.
	 * @param j The id of the second node.
	 */
	public void union(int i, int j) {
		i = find(i);
		j = find(j);
		
		if (sz[i] < sz[j]) {
			id[i] = j;
			sz[j] += sz[i]+1;
		} else {
			id[j] = i;
			sz[i] += sz[j]+1;
		}
	}
	
	/**
	 * 
	 * Find the root node for node i.
	 * 
	 * @param i The node to find the root node for.
	 * @return Returns the id of the root node.
	 */
	public int find(int i) {
		while (id[i] != i) {
			id[i] = id[id[i]];
			i = id[i];
		}
		
		return i;
	}
	
	/**
	 * 
	 * Checks whether nodes i and j are connected.
	 * 
	 * @param i The id of the node i.
	 * @param j The id of the node j.
	 * @return Returns true if both nodes are connected.
	 */
	public boolean connected(int i, int j) {
		return find(i) == find(j);
	}
	
	
	public static void main(String[] args) {
		UnionFind uf = new UnionFind(10);
		
		uf.union(1, 0);
		uf.union(9, 2);
		
		System.out.println(uf.connected(1, 9));
		
		uf.union(2, 0);
		
		System.out.println(uf.connected(1, 9));
	}
		
}
