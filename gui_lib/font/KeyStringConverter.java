package font;

public class KeyStringConverter {
	
	private static String[] strings = new String[] {
			
	"'",
	",",
	"-",
	".",
	"/",
	"0",
	"1",
	"2",
	"3",
	"4",
	"5",
	"6",
	"7",
	"8",
	"9",
	";",
	"=",
	"A",
	"B",
	"C",
	"D",
	"E",
	"F",
	"G",
	"H",
	"I",
	"J",
	"K",
	"L",
	"M",
	"N",
	"O",
	"P",
	"Q",
	"R",
	"S",
	"T",
	"U",
	"V",
	"W",
	"X",
	"Y",
	"Z"};
	
	public static String getStringOf(int key) {
		if (key-39 > 43) {
			System.out.println(key + " bzw " + (key-39));
			return "|";
		}
		return strings[key-39];
	}
	
}
