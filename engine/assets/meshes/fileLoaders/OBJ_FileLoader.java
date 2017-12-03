package assets.meshes.fileLoaders;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.io.File;


import java.util.ArrayList;
import org.lwjgl.BufferUtils;

import assets.models.Illuminated_Model;
import assets.material.Material;
import assets.material.StandardMaterial;
import assets.meshes.geometry.*;
import math.vectors.Vector2f;
import math.vectors.Vector3f;


import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;


public class OBJ_FileLoader {	
	
	private OBJ_FileLoader() {
		
	}
	
	
	public static Illuminated_Model loadOBJ_File(String objPath, String matPath) {
		return loadOBJ_File(objPath, MTL_Loader.loadMaterialFromMTL(matPath), null);
	}
	
	
	
	public static Illuminated_Model loadOBJ_File(String objPath, Material material, Color color) {
		
		//List of all vertices in this mesh
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		
		
		//List of normals in this mesh
		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
		
		
		//List of all texture coordinates in this mesh
		ArrayList<Vector2f> textureCoordinates = new ArrayList<Vector2f>();
		
		
		//List of the indices of the vertices to be drawn
		ArrayList<Integer> indices = new ArrayList<Integer>();		
		
		
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(objPath)))) {
			
			String nextLine = "";
			
			ArrayList<FaceBuffer> faceBuffers;
			Vertex vertex;
			
			while((nextLine = reader.readLine()) != null) {
				
				if (nextLine.startsWith("vn")) {
					
					//The line read by the BufferedReader contains normal data that will be extracted and added to the list of normals					
					normals.add(extractNormalData(nextLine));
					
					continue;
				} 
				
				if (nextLine.startsWith("vt")) {
					
					//Extract the texture position coordinates
					textureCoordinates.add(extractTexturePositionData(nextLine));
					
					continue;					
				}
				
				if (nextLine.startsWith("v")) {

					
					vertices.add(extractVertexPositionData(nextLine));
					
					
					continue;
				}
				
				
				if (nextLine.startsWith("f")) {
					//System.out.println("Face: Line number: " + lineNumber++);
					
					faceBuffers = extractFaceData(nextLine);
					
					
					//If this face is a triangle run this code...
					if (faceBuffers.size() == 3) {
						
						for (FaceBuffer buf : faceBuffers) {
							
							if (buf == null) {
								break;
							}
							
							vertex = vertices.get(buf.getPosIndex() - 1);
							
							if (color != null) 
								vertex.setColor(color);
													
							if (buf.getTexPosIndex() != -1)
								vertex.setTexturePositions(textureCoordinates.get(buf.getTexPosIndex() - 1));
							
							Vector3f normal = normals.get(buf.getNormalIndex() - 1);							
													
							vertex.addSurfaceNormal(normal);
																			
							indices.add(buf.getPosIndex() - 1);
							
						}

					//...if it is a quad run this code
					} else {
						
						if (faceBuffers.size() == 4) {
							
							for (FaceBuffer buf : faceBuffers) {
								
								if (buf == null) {
									break;
								}
								
								vertex = vertices.get(buf.getPosIndex() - 1);
								
								if (color != null)
									vertex.setColor(color);
								
								if (buf.getTexPosIndex() != -1)
									vertex.setTexturePositions(textureCoordinates.get(buf.getTexPosIndex()));
								
								Vector3f normal = normals.get(buf.getNormalIndex() - 1);
														
								vertex.addSurfaceNormal(normal);
								
							}
							
							
							indices.add(faceBuffers.get(0).getPosIndex() - 1);
							indices.add(faceBuffers.get(1).getPosIndex() - 1);
							indices.add(faceBuffers.get(2).getPosIndex() - 1);
							
							indices.add(faceBuffers.get(0).getPosIndex() - 1);
							indices.add(faceBuffers.get(3).getPosIndex() - 1);
							indices.add(faceBuffers.get(2).getPosIndex() - 1);
							
							
							
						}
						
					}
										
				}
									
			}
			
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		//The Model that will be created
		Illuminated_Model outputModel = new Illuminated_Model(GL_TRIANGLES, material);
		
		
		//Store the position data
		outputModel.setVertexPositionData(vertexPosToFloatBuffer(vertices), 3, GL_STATIC_DRAW);
		
		
		//Store the normal data
		outputModel.setVertexNormalData(vertexNormalsToFloatBuffer(vertices), GL_STATIC_DRAW);
		
		if (color != null) {
			outputModel.setVertexColorData(vertexColToFloatBuffer(vertices), 4, GL_STATIC_DRAW);
		}
		
		//Store the texture position data if there is any...
		FloatBuffer texPosBuffer;
		if ((texPosBuffer = vertexTexPosToFloatBuffer(vertices)) != null) {
			outputModel.setVertexTexturePositionData(texPosBuffer, 2, GL_STATIC_DRAW);
		}
		
		
		//Store the element data
		outputModel.setElementArrayData(indexDataToIntBuffer(indices));	
		
		
		return outputModel;
				
	}

	
	//*********************************** Convert ArrayLists to Buffers ***********************************
	

	private static FloatBuffer vertexPosToFloatBuffer(ArrayList<Vertex> list) {
		
		FloatBuffer output = BufferUtils.createFloatBuffer(list.size() * 3);
		
		for (Vertex vert : list) {
			
			output.put(vert.getA());
			output.put(vert.getB());
			output.put(vert.getC());
			
		}
		
		output.flip();
		
		System.out.println("Vertex data has been stored!");
		
		return output;
		
	}
	
	
	private static FloatBuffer vertexColToFloatBuffer(ArrayList<Vertex> list) {
		
		FloatBuffer output = BufferUtils.createFloatBuffer(list.size() * 4);
		
		for (Vertex vert : list) {
			
			output.put(vert.getRed());
			output.put(vert.getGreen());
			output.put(vert.getBlue());
			output.put(vert.getAlpha());
			
		}
		
		output.flip();
		
		return output;
	}
	
	
	private static FloatBuffer vertexTexPosToFloatBuffer(ArrayList<Vertex> list) {
		
		FloatBuffer output = BufferUtils.createFloatBuffer(list.size() * 2);
		
		Vector2f vec;
		
		for (Vertex vert : list) {
			
			if (!vert.isTextured()) {
				return null;
			}
			
			vec = vert.getTexturePositions();
			
			output.put(vec.getA());
			output.put(vec.getB());
			
		}
		
		output.flip();
		
		return output;
		
		
	}
	
	
	private static FloatBuffer vertexNormalsToFloatBuffer(ArrayList<Vertex> list) {
		
		FloatBuffer output = BufferUtils.createFloatBuffer(list.size() * 3);
		
		Vector3f vec;
		
		for (Vertex vert : list) {
			
			vec = vert.getNormal();
			
			output.put(vec.getA());
			output.put(vec.getB());
			output.put(vec.getC());
			
		}
		
		output.flip();
		
		return output;
		
		
	}
	
	
	public static IntBuffer indexDataToIntBuffer(ArrayList<Integer> list) {
		
		IntBuffer indexData = BufferUtils.createIntBuffer(list.size());
		
		for (int index : list) {
			
			indexData.put(index);
			
		}
		
		indexData.flip();
		
		return indexData;
		
	}
	
	
	//*********************************** Data extraction ***********************************
	
	
	private static Vertex extractVertexPositionData(String line) {
		
		Vertex vertex = new Vertex();
		
		int firstIndex = 2;
		int lastIndex = 2;
	
		String number;
		
		int numberCounter = 0;
		
		while(lastIndex < line.length()) {
			
			while(line.charAt(lastIndex) != ' ' && lastIndex < line.length() - 1) {
				
				lastIndex++;
				
			}

			number = line.substring(firstIndex, lastIndex + 1);
			
			if (numberCounter == 0) {
				vertex.setA(Float.parseFloat(number));
			}
			
			if (numberCounter == 1) {
				vertex.setB(Float.parseFloat(number));
			}
			
			if (numberCounter == 2) {
				vertex.setC(Float.parseFloat(number));
			}
			
			firstIndex = ++lastIndex;
			
			numberCounter++;
			
		}
		
		return vertex;
		
	}
	
	
	private static Vector2f extractTexturePositionData(String line) {
		Vector2f vector = new Vector2f(0f, 0f);
		
		int firstIndex = 3;
		int lastIndex = 3;
	
		String number;
		
		int numberCounter = 0;
		
		while(lastIndex < line.length()) {
			
			while(line.charAt(lastIndex) != ' ' && lastIndex < line.length() - 1) {
				
				lastIndex++;
				
			}

			number = line.substring(firstIndex, lastIndex);
			
			if (numberCounter == 0) {
				vector.setA(Float.parseFloat(number));
			}
			
			if (numberCounter == 1) {
				vector.setB(Float.parseFloat(number));
			}
			
			numberCounter++;
			
			firstIndex = ++lastIndex;
			
		}
		
		return vector;
	}
	
	
	private static Vector3f extractNormalData(String line) {

		Vector3f vector = new Vector3f(0, 0, 0);
		
		int firstIndex = 3;
		int lastIndex = 3;
	
		String number;
		
		int numberCounter = 0;
		
		while(lastIndex < line.length()) {
			
			while(line.charAt(lastIndex) != ' ' && lastIndex < line.length() - 1) {
				
				lastIndex++;
				
			}

			number = line.substring(firstIndex, lastIndex);
			
			if (numberCounter == 0) {
				vector.setA(Float.parseFloat(number));
			}
			
			if (numberCounter == 1) {
				vector.setB(Float.parseFloat(number));
			}
			
			if (numberCounter == 2) {
				vector.setC(Float.parseFloat(number));
			}
			
			numberCounter++;
			
			firstIndex = ++lastIndex;
			
		}
		
		return vector;
		
	}
	
	
	public static ArrayList<FaceBuffer> extractFaceData(String line) {
		
		line += " ";
		
		ArrayList<FaceBuffer> output = new ArrayList<FaceBuffer>();
		
		int firstIndex = 2;
		int lastIndex = 2;

		
		while(lastIndex < line.length()) {
			
			while(line.charAt(lastIndex) != ' ' && lastIndex < line.length() - 1) {
				
				lastIndex++;
				
			}
			
			output.add(extractVertexData(line.substring(firstIndex, lastIndex) + " "));
			
			firstIndex = ++lastIndex;
			
		}
		
		//System.out.println();
		
		return output;
			
	}
	
	
	public static FaceBuffer extractVertexData(String line) {
		
		FaceBuffer output = new FaceBuffer();
		
		int firstIndex = 0;
		int lastIndex = 0;
		
		int value = 0;
		
		int index;
		
		while (lastIndex < line.length() - 1) {
			
			while(line.charAt(lastIndex) != '/' && line.charAt(lastIndex) != ' ') {
				
				lastIndex++;
								
			}
			
			if (value == 0) {
				
				index = Integer.parseInt(line.substring(firstIndex, lastIndex));
				
				output.setPosIndex(index);
			}
			
			if (value == 1) {
				index = Integer.parseInt(line.substring(firstIndex, lastIndex));
				
				output.setTexPosIndex(index);
			}
			
			if (value == 2) {
				index = Integer.parseInt(line.substring(firstIndex, lastIndex));
				
				output.setNormalIndex(index);
			}
								
			
			while(line.charAt(lastIndex) == '/') {
				value++;
				
				lastIndex++;
			}
			
			firstIndex = lastIndex;
			
		}
		
		return output;		
		
	}
	
}


//A small data structure to store a position data index, a tex pos index and an normal index that belong together
class FaceBuffer {
	
	private int posIndex = -1;
	
	private int texPosIndex = -1;
	
	private int normalIndex = -1;
	
	
	public FaceBuffer() {
		
	}
	

	public FaceBuffer(int posIndex, int texPosIndex, int normalIndex) {
		
		this.posIndex = posIndex;
		
		this.texPosIndex = texPosIndex;
		
		this.normalIndex = normalIndex;				
		
	}


	public int getPosIndex() {
		return posIndex;
	}


	public int getTexPosIndex() {
		return texPosIndex;
	}


	public int getNormalIndex() {
		return normalIndex;
	}
	
	
	public void setPosIndex(int posIndex) {
		this.posIndex = posIndex;
	}


	public void setTexPosIndex(int texPosIndex) {
		this.texPosIndex = texPosIndex;
	}


	public void setNormalIndex(int normalIndex) {
		this.normalIndex = normalIndex;
	}

	
}
