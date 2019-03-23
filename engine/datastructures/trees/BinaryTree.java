package datastructures.trees;

public class BinaryTree<K extends Comparable<K>, V> {
	
	private class Node<K extends Comparable<K>, V> {
		public K key;
		public V value;
		
		public Node<K, V> left;
		public Node<K, V> right;
		
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public String toString() {
			return "( {" + key + ", " + value + "} " + (left != null ? left : "") + " " + 
								 (right != null ? right : "") + ")";
		}
		
	}
	
	
	private Node<K, V> head;
	
	
	public BinaryTree() {
		this.head = null;
	}
	
	
	public void insert(K key, V value) {
		if (head == null) {
			head = new Node<K, V>(key, value);
		} else {
			insert(key, value, head);
		}
	}
	
	
	private void insert(K key, V value, Node<K, V> node) {
		int cmp = node.key.compareTo(key);
		
		if (cmp == 0) {
			return;
		}
		
		if (cmp < 0) {
			if (node.left == null) {
				node.left = new Node<K, V>(key, value);
			} else {
				insert(key, value, node.left);
			}
		} else {
			if (node.right == null) {
				node.right = new Node<K, V>(key, value);
			} else {
				insert(key, value, node.right);
			}
		}
	}
	
	
	public V get(K key) {
		if (head == null) {
			return null;			
		}
		
		return get(key, head);
	}
	
	
	private V get(K key, Node<K, V> node) {
		if (node == null) {
			return null;
		}
		
		int cmp = node.key.compareTo(key);
		
		if (cmp == 0) {
			return node.value;
		}
		
		if (cmp < 0) {
			return get(key, node.left);
		} else {
			return get(key, node.right);
		}
	}
	
	
	public void remove(K key) {
		int cmp = head.key.compareTo(key);
		
		if (cmp == 0) {
			head = null;
			return;
		}
		
		Node<K, V> current;
		Node<K, V> last;
		
		if (cmp < 0) {
			current = head.left;
		} else {
			current = head.right;
		}
		
		last = head;
		
		while (current != null) {
			cmp = current.key.compareTo(key);
			
			if (cmp == 0) {
				remove(current, last);
			}
			
			if (cmp < 0) {
				last = current;
				current = current.left;
			} else {
				last = current;
				current = current.right;
			}
		}
	}
	
	
	private void remove(Node<K, V> node, Node<K, V> last) {
		//If the node has no successors, just remove it.
		if (node.left == null && node.right == null) {
			replaceNode(last, node, null);			
			return;
		}
		
		if (hasOneChild(node)) {
			//If the node has only one successor use it to replace the node with.
			if (node.left != null) {
				replaceNode(last, node, node.left);
			} else {
				replaceNode(last, node, node.right);
			}
		} else {
			//Find a node that is smaller than all nodes on the right side
			//but bigger than the nodes on the right side.
			Node<K, V> successor = getMin(node.right);
			
			//Remove the successor node from the tree.
			remove(successor.key);
			
			//Replace the values of the node to be removed with the successor's values.
			node.key = successor.key;
			node.value = successor.value;
		}
	}
	
	
	private void replaceNode(Node<K, V> target, Node<K, V> oldN, Node<K, V> newN) {
		if (target.left == oldN) {
			target.left = newN;
		} else {
			target.right = newN;
		}
	}
	
	
	private boolean hasOneChild(Node<K, V> node) {
		return node.left == null ^ node.right == null;
	}
	
	
	private Node<K, V> getMin(Node<K, V> node) {
		while (node != null) {
			node = node.left;
		}
		
		return node;
	}


	@Override
	public String toString() {
		return head.toString();
	}
	
	
	public void main(String[] args) {
		int[] is = {1,3,2,5,4};
		BinaryTree<Integer, Integer> tree = new BinaryTree<Integer, Integer>();
		
		for (int i : is) {
			tree.insert(i, i);
		}
		
		System.out.println(tree);
	}

}
