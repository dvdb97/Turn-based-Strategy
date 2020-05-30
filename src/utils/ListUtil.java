package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
		for (int e : a)
			l.add(e);
		return l;
	}
	
	public static ArrayList<Integer> asListI(int[] a, Predicate<Integer> condition) {
		ArrayList<Integer> l = new ArrayList<>(a.length);
		for (Integer e : a)
			if(condition.test(e)) {
				l.add(e);
	}
		return l;
	}
}
