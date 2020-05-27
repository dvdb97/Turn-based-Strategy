package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ListUtil {

	public static <T> void removeNullInPlace(List<T> list) {
		while(list.remove(null)) {
		}
	}
	
	public static <T> ArrayList<T> asList(T[] a) {
		ArrayList<T> l = new ArrayList<>(a.length);
		l.addAll(Arrays.asList(a));
		return l;
	}
	
	public static <T> ArrayList<T> asList(Set<T> s) {
		ArrayList<T> l = new ArrayList<>(s.size());
		l.addAll(s);
		return l;
	}
	

	public static ArrayList<Integer> asListI(int[] a) {
		ArrayList<Integer> l = new ArrayList<>(a.length);
		for (int i : a)
			l.add(i);
		return l;
	}
}
