package font;

public class KeyStringConverter {
	
	private static String[] strings = new String[] {
			
//	"'",
//	",",
//	"-",
//	".",
//	"/",
//	"0",
//	"1",
//	"2",
//	"3",
//	"4",
//	"5",
//	"6",
//	"7",
//	"8",
//	"9",
//	";",
//	"=",
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
		
		switch(key) {
		case 32 : return " ";
		case 46 : return ".";
		case 257: return "\n";
		case 258: return "\t";
		}
		
		if (key-65 > 25 || key-65 < 0) {
			return "NON-VALID";
		}
		return strings[key-65];
	}
	
}
