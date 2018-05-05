package assets.meshes.fileLoaders;


//IO stuff
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


//Data structures:
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import org.lwjgl.BufferUtils;


//Imports of our engine
import assets.meshes.geometry.VertexLegacy;
import assets.models.Element_Model;


//Opengl stuff
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;


/*
 * TODO List:
 * 
 * - load normals
 * 
 */


public class PLY_FileLoader {
		
	
	private PLY_FileLoader() {
		
	}
		
	
	public static Element_Model loadPLY_File(String path) {
		
		ArrayList<Float> positionDataList = new ArrayList<Float>();
		
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		
		
		Element_Model outputModel = new Element_Model(GL_TRIANGLES);
		
		
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
			
			String nextLine = "";
			
			VertexLegacy vertex;
			
			boolean header = true;
			
			while((nextLine = reader.readLine()) != null) {
				
				if (header) {
					
					if (nextLine.startsWith("end_header")) {
						
						header = false;
						
					}
					
				} else {
					
					if (nextLine.startsWith("3")) {
						
						int[] data = convertToIndexArray(nextLine);
						
						indexList.add(data[0]);
						indexList.add(data[1]);
						indexList.add(data[2]);
						
					} else {
						
						vertex = convertToVertex(nextLine);
						
						positionDataList.add(vertex.getA());
						positionDataList.add(vertex.getB());
						positionDataList.add(vertex.getC());
						
					}
										
				}
					
			}
				
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		outputModel.setVertexPositionData(toFloatBuffer(positionDataList), 3, GL_STATIC_DRAW);
		
		outputModel.setElementArrayData(toIntBuffer(indexList));
		
		return outputModel;
				
	}
	
	
	private static VertexLegacy convertToVertex(String line) {
		
		VertexLegacy vertex = new VertexLegacy(0.0f, 0.0f, 0.0f, null);
		
		int lastStartingPosition = 0;
		int numberCounter = 0;
		String number = "";
		
		for (int i = 0; i < line.length(); ++i) {
			
			if (line.charAt(i) == ' ') {
				
				number = line.substring(lastStartingPosition, i);
				
				if (numberCounter == 0) {
					vertex.setA(Float.parseFloat(number));
				}
				
				if (numberCounter == 1) {
					vertex.setB(Float.parseFloat(number));
				}
				
				if (numberCounter == 2) {
					vertex.setC(Float.parseFloat(number));
				}
								
				numberCounter++;
				lastStartingPosition = i + 1;
				number = "";
							
			}
			
		}
		
		return vertex;
	}
	
	
	public static int[] convertToIndexArray(String line) {
		
		int[] data = new int[3];
		
		int lastIndex = 2;
		
		int numberOfIndices = 0;
		
		String number = "";
		
		for (int i = lastIndex; i < line.length(); ++i) {
			
			if (line.charAt(i) == ' ') {
				
				if (numberOfIndices == 0) {
					
					number = line.substring(lastIndex, i);
					
					data[numberOfIndices] = Integer.parseInt(number);
					
				}
				
				if (numberOfIndices == 1) {
					
					number = line.substring(lastIndex, i);
					
					data[numberOfIndices] = Integer.parseInt(number);
					
				}
				
				if (numberOfIndices == 2) {
					
					number = line.substring(lastIndex, i);
					
					data[numberOfIndices] = Integer.parseInt(number);
					
				}
				
				numberOfIndices++;
				lastIndex = i + 1;
				number = "";
				
			}
				
		}
				
		return data;
		
	}
	
	
	public static FloatBuffer toFloatBuffer(ArrayList<Float> data) {
		
		FloatBuffer outputBuffer = BufferUtils.createFloatBuffer(data.size());
		
		for (float value : data) {
			outputBuffer.put(value);
		}
		
		outputBuffer.flip();
		
		return outputBuffer;
		
	}
	
	
	public static IntBuffer toIntBuffer(ArrayList<Integer> data) {
		
		IntBuffer outputBuffer = BufferUtils.createIntBuffer(data.size());
		
		for (int value : data) {
			outputBuffer.put(value);
		}
		
		outputBuffer.flip();
		
		return outputBuffer;
		
		
	}

}

