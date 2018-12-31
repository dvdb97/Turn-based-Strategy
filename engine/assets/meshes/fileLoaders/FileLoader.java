package assets.meshes.fileLoaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.BufferUtils;

import assets.material.Material;
import assets.meshes.Mesh;
import assets.textures.Texture2D;
import math.vectors.Vector2f;
import math.vectors.Vector3f;
import utils.CustomBufferUtils;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class FileLoader {
	
	private static class Vertex {
		public Vector3f position;
		public Vector2f texCoords;
		public Vector3f normal;
		
		public Vertex(Vector3f pos, Vector2f tex, Vector3f normal) {
			this.position = pos;
			this.texCoords = tex;
			this.normal = normal;
		}

		@Override
		public int hashCode() {
			int hash = 17;
			
			hash += position.getA() * 37;
			hash += position.getB() * 37;
			hash += position.getC() * 37;
			
			if (texCoords != null) {
				hash += texCoords.getA() * 37;
				hash += texCoords.getB() * 37;
			} else {
				hash += 207;
			}
			
			hash += normal.getA() * 37;
			hash += normal.getB() * 37;
			hash += normal.getC() * 37;
			
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}
		
	}
	
	
	private static final int POS = 3;
	private static final int TEX_COORD = 2;
	private static final int NORMAL = 3;
	
	
	/**
	 * 
	 * Loads a model from a .obj-File. The implementation is heavily inspired by 
	 * ThinMatrix' tutorial: 
	 * 
	 * https://www.youtube.com/watch?v=YKFYtekgnP8&index=10&list=PLRIWtICgwaX0u7Rf9zkZhLoLuZVfUksDP
	 * 
	 * Requirements:
	 * 
	 * 	- The model has to be triangulated in Blender as this algorithm isn't able to process quads.
	 *  - No lines have been added manually to the file as it might cause the programm to crash.
	 * 
	 * @param path The path of the file.
	 * @param The mesh to load the data to.
	 * @return Returns the mesh Mesh
	 */
	public static void loadObjFile(Mesh mesh, String path) {
		ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
		ArrayList<Vector2f> texCoords = new ArrayList<Vector2f>();
		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
		
		HashMap<Vertex, Integer> vertexTable = new HashMap<Vertex, Integer>();
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		ArrayList<Integer> indices = new ArrayList<Integer>();		
		
		String line = "";
		
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
			
			while ((line = reader.readLine()) != null) {
				String[] currentLines = line.split(" ");
				
				if (line.startsWith("v ")) {
					positions.add(new Vector3f(parseFloat(currentLines[1]), parseFloat(currentLines[2]), parseFloat(currentLines[3])));
				} else if (line.startsWith("vt ")) {
					texCoords.add(new Vector2f(parseFloat(currentLines[1]), 1f - parseFloat(currentLines[2])));
				} else if (line.startsWith("vn ")) {
					normals.add(new Vector3f(parseFloat(currentLines[1]), parseFloat(currentLines[2]), parseFloat(currentLines[3])));
				} else if (line.startsWith("f ")) {
					String[] currentLine = line.split(" ");
					
					for (int i = 1; i < currentLine.length; ++i) {
						String[] vertexData = currentLine[i].split("/");
						
						Vector3f vPosition = positions.get(parseInt(vertexData[0]) - 1);		
						Vector2f vTexCoord = vertexData[1].equals("") ? null : texCoords.get(parseInt(vertexData[1]) - 1);
						Vector3f vNormal = normals.get(parseInt(vertexData[2]) - 1);
						Vertex vertex = new Vertex(vPosition, vTexCoord, vNormal);
						
						if (vertexTable.containsKey(vertex)) {
							indices.add(vertexTable.get(vertex));
							System.out.println(vertex.position);
						} else {
							vertices.add(vertex);
							indices.add(vertices.size() - 1);
							vertexTable.put(vertex, positions.size() - 1);
						}
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//#################### Position data ####################
		FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(vertices.size() * POS);
		
		for (int i = 0; i < vertices.size(); ++i) {
			positionBuffer.put(vertices.get(i).position.toArray());
		}
		
		positionBuffer.flip();
		mesh.setPositionData(positionBuffer, POS);
		
		//#################### tex coords data ####################
		if (vertices.get(0).texCoords != null) {			
			FloatBuffer texPosBuffer = BufferUtils.createFloatBuffer(vertices.size() * TEX_COORD);
			
			for (int i = 0; i < vertices.size(); ++i) {
				texPosBuffer.put(vertices.get(i).texCoords.toArray());
			}
			
			texPosBuffer.flip();
			mesh.setTexCoordData(texPosBuffer, TEX_COORD);
		}
		
		//#################### normal data ####################
		FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(vertices.size() * NORMAL);
		
		for (int i = 0; i < vertices.size(); ++i) {			
			normalBuffer.put(vertices.get(i).normal.toArray());
		}
		
		normalBuffer.flip();
		mesh.setNormalData(normalBuffer, NORMAL);
		
		//#################### index data ####################
		mesh.setIndexBuffer(CustomBufferUtils.createIntBuffer(indices));
	}
	
	
	public static void loadObjFile(Mesh mesh, String path, String texture) {
		loadObjFile(mesh, path);
		mesh.setTexture(new Texture2D(texture));
	}
	
	
	public static Mesh loadObjFile(String path) {
		Mesh mesh = new Mesh();
		loadObjFile(mesh, path);		
		return mesh;
	}
	
	
	public static Mesh loadObjFile(String path, String texture) {
		Mesh mesh = loadObjFile(path);
		mesh.setTexture(new Texture2D(texture));
		return mesh;
	}
	
}
