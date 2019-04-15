package datastructures;

public class Tuple<X, Y> {
	public X x;
	public Y y;
	
	public Tuple(X x, Y y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "(" + x + ", " + y + ")";
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		Tuple<X, Y> other = (Tuple<X, Y>)obj;
		
		return other.x.equals(x) && other.y.equals(y);
	}
	
}
