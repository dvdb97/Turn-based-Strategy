package assets.meshes.algorithms;

import assets.meshes.geometry.Vertex;
import assets.models.Element_Model;
import lwlal.Vector2i;
import utils.ColorFunction;

import static org.lwjgl.opengl.GL11.*;

public class MidpointDisplacement {
	
	private static Vertex[] vertices;
	
	private static int iterations;
	
	private static float width;
	
	private static float height;
	
	private static int size;
	
	private static ColorFunction colorFunc;
	
	
	public static Element_Model generateMesh(int iterations, float width, float height, ColorFunction colorFunc) {
		
		MidpointDisplacement.iterations = iterations;
		
		MidpointDisplacement.width = width;
		
		MidpointDisplacement.height = height;
		
		size = iterations + 2;
		
		MidpointDisplacement.colorFunc = colorFunc;
		
		
		generateVertices();
		
		midpointDisplacement(0, new Vector2i(0, 0), new Vector2i(size - 1, 0), new Vector2i(0, size - 1), new Vector2i(size - 1, size - 1));
		
		
		Element_Model output = new Element_Model(GL_TRIANGLES);
		
		
		return output;
		
	}
	
	
	private static void generateVertices() {
		
		
		
		vertices = new Vertex[size * size];
		
		
		for (int y = 0; y < size; ++y) {
			
			for (int x = 0; x < size; ++x) {
				
				float xPos = x *(width / size);
				
				float yPos = y * (height / size);
				
				vertices[y * size + x] = new Vertex(xPos, yPos, (float)Math.random(), colorFunc.color(0, 0, 0.0f));
				
			}
			
		}
		
	}
	
	
	private static void midpointDisplacement(int iteration, Vector2i upperLeft, Vector2i upperRight, Vector2i lowerLeft, Vector2i LowerRight) {
		
		if (iteration == iterations) {
			//Save the indices in the IndexBuffer
			
			return;
		}
		
		//Compute the indices of the center vertex:
		Vector2i center = lowerLeft.add(upperRight).multiply(0.5f);
		
		//Computer the IDs for all vertices necessary:
		int centerID = center.getY() * size + center.getX();
		int upperLeftID = upperLeft.getY() * size + upperLeft.getX();
		int upperRightID = upperRight.getY() * size + upperRight.getX();
		int lowerLeftID = lowerLeft.getY() * size + lowerLeft.getX();
		int lowerRightID = LowerRight.getY() * size + LowerRight.getX();
		
		
		//Set the center vertex height:
		float surroundingMeanHeight = (vertices[upperLeftID].getC() + vertices[upperRightID].getC() + vertices[lowerLeftID].getC() + vertices[lowerRightID].getC()) / 4f;
		
		
		
		
	}
	
	
	private static Vertex getVertexByCoords(int x, int y) {
		return MidpointDisplacement.vertices[y * size + x];
	}
	
	
	
	
	
	

}
