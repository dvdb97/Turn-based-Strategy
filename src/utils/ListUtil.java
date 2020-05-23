package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
}
