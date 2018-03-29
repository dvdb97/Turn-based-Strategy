package models.worldModels;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDisable;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import assets.material.Material;
import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;
import assets.models.Illuminated_Model;
import math.vectors.Vector3f;
import utils.Const;
import utils.CustomBufferUtils;
import models.seeds.ColorFunction;
import models.seeds.SuperGrid;

public class TriangleGrid extends Illuminated_Model {
	
	private int length, width;
	
	private int lengthMod2;
	
	private SuperGrid superGrid;
	
	private float edgeLength;
	private float triangleAltitude;
	
	private float[][] elevation;
	
	ColorFunction colorFunc;
	
	private final int PRI;			//primitive restart index
	
	private Vertex[] vertices;
	
	private boolean sea;
	
	//************************************** constructor *************************************
	
	/**
	 * @param colorFunc a function that gives a vertex a color depending on its position
	 * @param material
	 */
	public TriangleGrid(SuperGrid superGrid, ColorFunction colorFunc, Material material, boolean sea) {
		
		this(superGrid, material, sea);
		
		this.colorFunc = colorFunc;
		
		processVertices();
	}
	
	/**
	 * @param color the color of the grid
	 * @param material
	 */
	public TriangleGrid(SuperGrid superGrid, Color color, Material material, boolean sea) {
		
		this(superGrid, material, sea);
		
		this.colorFunc = new ColorFunction() {
			@Override
			public Color color(int x, int y, float height) {
				return color;
			}
		};
		
		processVertices();
		
	}
	
	
	private TriangleGrid(SuperGrid superGrid, Material material, boolean sea) {
		
		super(GL_TRIANGLE_STRIP, material);
		
		length = superGrid.getLengthInVectors();
		width  = superGrid.getWidthInVectors();
		
		lengthMod2 = length%2;
		
		PRI = -1;
		
		this.superGrid = superGrid;
		
		this.sea = sea;
	}
	
	//*************************************** prime method ****************************************
	
	private void processVertices() {
		
		Vector3f[] positions = superGrid.getVectors();
		elevation = new float[length][width];
		for (int y=0; y<width; y++) {
			for (int x=0; x<length; x++) {
				
				if (sea) {
					positions[y*length + x].setC(0);
				}
				elevation[x][y] = positions[y*length + x].getC();
				
			}
		}
		
		ArrayList<Vector3f> normals   = processNormalVectors();
		
		vertices = new Vertex[positions.length];
		
		for (int v=0; v<vertices.length; v++) {
			vertices[v] = new Vertex(positions[v], colorFunc.color(v%length, v/length, positions[v].getC()));
			vertices[v].setNormalVector(normals.get(v));
		}
		
		setData(vertices, processElementBuffer());
		
	}
	
	
	
	//*************************************** secondary methods **********************************
	
	
	private IntBuffer processElementBuffer() {
		
		IntBuffer elementBuffer = BufferUtils.createIntBuffer( (width*2+1)*(length-1));
		
		for (int col=0; col<length-1; col++) {
			
			for (int row=0; row<width; row++) {
				
				elementBuffer.put(col+row*length);
				elementBuffer.put(col+row*length+1);
				
			}
			
			elementBuffer.put(PRI);
			
		}
		
		elementBuffer.flip();
		
		return elementBuffer;
		
	}
	
	
	private ArrayList<Vector3f> processNormalVectors() {
		
		ArrayList<Vector3f> normals = new ArrayList<>(length*width);
		
		//first row
		
		normals.add(avgNormal_IV(elevation[0][1], elevation[1][0], elevation[0][0]));
		for (int x=1; x<length-1-lengthMod2; x++) {
			normals.add(avgNormal_II_III_IV_V(elevation[x-1][0], elevation[x-1][1], elevation[x][1], elevation[x+1][1], elevation[x+1][0], elevation[x][0]));
			x++;
			normals.add(avgNormal_III_IV(elevation[x-1][0], elevation[x][1], elevation[x+1][0], elevation[x][0]));
		}
		if (lengthMod2 == 1) {
			int x = length-2;
			normals.add(avgNormal_II_III_IV_V(elevation[x-1][0], elevation[x-1][1], elevation[x][1], elevation[x+1][1], elevation[x+1][0], elevation[x][0]));
		}
		normals.add(avgNormal_II_III(elevation[length-2][0], elevation[length-2][1], elevation[length-1][1], elevation[length-1][0]));
		
		
		//middle rows
		
		for (int y=1; y<width-1; y++) {
			
			normals.add(avgNormal_IV_V_VI(elevation[0][y-1], elevation[0][y+1], elevation[1][y], elevation[1][y-1], elevation[0][y]));
			for (int x=1; x<length-1-lengthMod2; x++) {
				normals.add(avgNormal(elevation[x][y-1], elevation[x-1][y], elevation[x-1][y+1], elevation[x][y+1], elevation[x+1][y+1], elevation[x+1][y]));
				x++;
				normals.add(avgNormal(elevation[x][y-1], elevation[x-1][y-1], elevation[x-1][y], elevation[x][y+1], elevation[x+1][y], elevation[x+1][y-1]));
			}
			if (lengthMod2 == 1) {
				int x = length-2;
				normals.add(avgNormal(elevation[x][y-1], elevation[x-1][y], elevation[x-1][y+1], elevation[x][y+1], elevation[x+1][y+1], elevation[x+1][y]));
			}
			
			normals.add(avgNormal_I_II_III(elevation[length-1][y-1], elevation[length-1][y-lengthMod2], elevation[length-2][y+1-lengthMod2], elevation[length-1][y+1], elevation[length-1][y]));
		}
		
		
		//last row
		
		normals.add(avgNormal_V_VI(elevation[0][width-2], elevation[1][width-1], elevation[1][width-2], elevation[0][width-1]));
		for (int x=1; x<length-1-lengthMod2; x++) {
			normals.add(avgNormal_VI_I(elevation[x][width-2], elevation[x-1][width-1], elevation[x+1][width-1], elevation[x][width-1]));
			x++;
			normals.add(avgNormal_I_II_V_VI(elevation[x][width-2], elevation[x-1][width-2], elevation[x-1][width-1], elevation[x+1][width-1], elevation[x+1][width-2], elevation[x][width-1]));
		}
		if (lengthMod2 == 1) {
			int x = length-2;
			normals.add(avgNormal_VI_I(elevation[x][width-2], elevation[x-1][width-1], elevation[x+1][width-1], elevation[x][width-1]));
		}
		normals.add(avgNormal_I(elevation[length-1][width-2], elevation[length-2][width-1], elevation[length-1][width-1]));
		
		return normals;
		
	}


	
	
	
	
	//********************************** util methods *********************************************
	
	private Vector3f avgNormal(float a, float b, float c, float d, float e, float f) {
		
		Vector3f result = new Vector3f(edgeLength*(b+c-e-f)/4.0f,
							triangleAltitude*(2*a+b-c-2*d-e+f)/6.0f,
							edgeLength*triangleAltitude);
		return result.normalize();
		
	}	
	
	
	private Vector3f avgNormal_I(float a, float b, float m) {
		
		Vector3f result = new Vector3f(edgeLength*(2*b-m-a)/2.0f,
							triangleAltitude*(a-m),
							edgeLength*triangleAltitude);
		return result.normalize();
		
	}
	
	private Vector3f avgNormal_II_III(float b, float c, float d, float m) {
		
		Vector3f result = new Vector3f(edgeLength*(b+3*c-d-3*m)/4.0f,
							triangleAltitude*(b-c+m-d)/2.0f,
							edgeLength*triangleAltitude);
		return result.normalize();
		
	}
	
	private Vector3f avgNormal_I_II_III(float a, float b, float c, float d, float m) {
		
		Vector3f result = new Vector3f(edgeLength*(3*b-a+3*c-d-4*m)/6.0f,
							triangleAltitude*(a+b-c-d)/3.0f,
							edgeLength*triangleAltitude);
		return result.normalize();
		
	}
	
	private Vector3f avgNormal_IV(float d, float e, float m) {
		
		Vector3f result = new Vector3f(edgeLength*(d+m-2*e)/2.0f,
							triangleAltitude*(m-d),
							edgeLength*triangleAltitude);
		return result.normalize();
		
	}
	
	private Vector3f avgNormal_III_IV(float c, float d, float e, float m) {
		
		Vector3f result = new Vector3f(edgeLength*(c-e)/2.0f,
							triangleAltitude*(m-d),
							edgeLength*triangleAltitude);
		return result.normalize();
		
	}
		
	private Vector3f avgNormal_II_III_IV_V(float b, float c, float d, float e, float f, float m) {
		
		Vector3f result = new Vector3f(edgeLength*(b+3*c-3*e-f)/8.0f,
							triangleAltitude*(b-c-2*d-e+f+2*m)/4.0f,
							edgeLength*triangleAltitude);
		return result.normalize();
		
	}
	
	private Vector3f avgNormal_V_VI(float a, float e, float f, float m) {
	
		Vector3f result = new Vector3f(edgeLength*(a-e-3*f+3*m)/4.0f,
							triangleAltitude*(a-e+f-m)/2.0f,
							edgeLength*triangleAltitude);
		return result.normalize();
		
	}
	
	private Vector3f avgNormal_IV_V_VI(float a, float d, float e, float f, float m) {
	
		Vector3f result = new Vector3f(edgeLength*(a+d-3*e-3*f+4*m)/6.0f,
							triangleAltitude*(a-d-e+f)/3.0f,
							edgeLength*triangleAltitude);
		return result.normalize();
		
	}
	
	private Vector3f avgNormal_VI_I(float a, float b, float f, float m) {
		
		Vector3f result = new Vector3f(edgeLength*(b-f)/2.0f,
							triangleAltitude*(a-m),
							edgeLength*triangleAltitude);
		return result.normalize();
		
	}
	
	private Vector3f avgNormal_I_II_V_VI(float a, float b, float c, float e, float f, float m) {
		
		Vector3f result = new Vector3f(edgeLength*(-a+3*b+c-e-3*f-m)/8.0f,
							triangleAltitude*(2*a+b-c-e+f-2*m)/4.0f,
							edgeLength*triangleAltitude);
		return result.normalize();
		
	}
	
	//********************************** other stuff **********************************************
	public void onDrawStart() {
		super.onDrawStart();
		
		glEnable(GL_PRIMITIVE_RESTART);
		glPrimitiveRestartIndex(PRI);
	}
	
	
	public void onDrawStop() {
		super.onDrawStop();
		
		glDisable(GL_PRIMITIVE_RESTART);
		
	}
	
	//******************************************** get & set **************************************
	
	/**
	 * @return the length of the mesh in knots (vertices)
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return the width of the mesh in knots (vertices)
	 */
	public int getWidth() {
		return width;
	}
	
	//TODO: bad method names
	//TODO: java doc
	public float getTotalWidth() {
		return width * edgeLength;
	}
	
	public float getTotalHeight() {
		return length * edgeLength;
	}
	
}
