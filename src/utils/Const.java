
package utils;

import lwlal.Vector3f;
import world.GoodsVector;

public class Const {
	
	
	private static final float LATERAL_DISTANCE_TO_TEXTURE_EDGE = (float)(Math.sqrt(3) / 2.0f) * 0.5f - 0.5f;
	public static final float[] HEXAGON_TEXTURE_OFFSETS = {
		0.5f, 									 0.5f,  //Mittelpunkt
		1.0f + LATERAL_DISTANCE_TO_TEXTURE_EDGE, 0.25f, //rechts oben
		1.0f + LATERAL_DISTANCE_TO_TEXTURE_EDGE, 0.75f, 
		0.5f, 									 1.0f, 
		0.0f - LATERAL_DISTANCE_TO_TEXTURE_EDGE, 0.75f, 
		0.0f - LATERAL_DISTANCE_TO_TEXTURE_EDGE, 0.25f,  
		0.5f, 									 0.0f, 
	};
	
	//graphics... kinda
	//distance between center and corner
	public static final float radiusOfHexagons = 0.5f;
	
	//numbers
	public static final float SQRT2			= (float) Math.pow(2, 0.5);
	public static final float SQRT3			= (float) Math.pow(3,  0.5);
	
	public static final float PI			= (float) Math.PI;
	public static final float HALF_PI		= (float) Math.PI * 0.5f;
	public static final float DOUBLE_PI		= (float) Math.PI * 2.0f;
	
	
	//TODO: actually its 7
	public static final int NUM_LANDSCAPES	= 6;	//coast doesn't count
	
	//landscape IDs
	//TODO: originally this is: 0x10...0x17
	public static final int DESERT_ID 		= 0;
	public static final int GRASSLAND_ID 	= 1;
	public static final int HILLS_ID 		= 2;
	public static final int COAST_ID 		= 3;
	public static final int OCEAN_ID 		= 4;
	public static final int MOUNTAINS_ID 	= 5;
	public static final int FOREST_ID 		= 6;
	public static final int UNKNOWN_ID		= 7;
	//TODO: temporary landscape colors
	
	//end temporary
	
	//game rules
	public static final int MAX_NUM_CITIES = 32;
	public static final int MAX_NUM_TRIBES = 4;
	
	//tribe colors
	public static final Vector3f[] tribeColors = new Vector3f[]
	{	
				
		new Vector3f(0.545f, 0.0f, 0.545f),		//1	dark magenta	139   0 139
		new Vector3f(0.545f, 0.047f, 0.047f),	//0	red brown 		139  12  12
		new Vector3f(0.0f, 0.0f, 0.855f),		//2 navy blue		  0   0 218
		new Vector3f(1.0f, 0.843f, 0.0f),		//3	gold			255 215   0
		new Vector3f(1.0f, 0.843f, 0.0f),		//4
		new Vector3f(1.0f, 0.843f, 0.0f),		//5
		new Vector3f(1.0f, 0.843f, 0.0f),		//6
		new Vector3f(1.0f, 0.843f, 0.0f) 		//7
			
	};
	
	public static Vector3f SELECTION_COL = new Vector3f(0.5f, 0.5f, 0.5f);
	
	public static String[] landscapeTypes = {
		"Desert",
		"Grassland",
		"Hills",
		"Mountains",
		"Forest",
		"Ocean",
		"Coast"
	};
	
	//goods
	public static final int NUM_GOODS = 2;
	
	//costs
	public static final int[] CITY_COSTS = new int[] {-2, 0};
	
	//others
	public static final int[] START_INVENTORY = new int[] {10, 10};
	
	
	
	//menu IDs
	public static final int NO_MENU			= 0x1F;
	public static final int MAIN_MENU_ID	= 0x20;
	public static final int MENU1_ID		= 0x21;
	public static final int MENU2_ID		= 0x22;
	public static final int MENU3_ID		= 0x23;
	public static final int MENU4_ID		= 0x24;
	
	//menu select
	//no select
	public static final int NO_MENU_SELECT	= 0x1FF;
	//main menu
	public static final int MM_NEWGAME		= 0x200;
	public static final int MM_LOADGAME		= 0x201;
	public static final int MM_SETTINGS		= 0x202;
	public static final int MM_CREDITS		= 0x203;
	public static final int MM_EXIT			= 0x204;
	//menu 1
	public static final int M1_OPTION_0		= 0x210;
	public static final int M1_OPTION_1		= 0x211;
	public static final int M1_OPTION_2		= 0x212;
	public static final int M1_OPTION_3		= 0x213;
	public static final int M1_OPTION_4		= 0x214;
	
	//tileSelectingProcessKey
	public static final int TSP_NONE		= 0x9F;
	public static final int TSP_payingCity	= 0xA0;
	
}
