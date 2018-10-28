package assets.meshes.prefabs;

import java.util.Arrays;

import assets.buffers.Buffer;
import assets.meshes.Mesh;
import assets.meshes.MeshConst.BufferLayout;
import assets.meshes.geometry.Vertex;
import math.vectors.Vector2f;
import math.vectors.Vector3f;

public class Plane extends Mesh {

	private static Vertex[] vertices = {
		new Vertex(new Vector3f(-1f, 1f, 0f), new Vector2f(0f, 1f)),
		new Vertex(new Vector3f(1f, 1f, 0f), new Vector2f(1f, 1f)),
		new Vertex(new Vector3f(-1f, -1f, 0f), new Vector2f(0f, 0f)),
		new Vertex(new Vector3f(1f, -1f, 0f), new Vector2f(1f, 0f))
	};
	
	
	private static int[] indices = {
		0, 1, 3, 3, 2, 0
	};
	
	
	public Plane() {
		super(vertices, indices);
		
		this.storeOnGPU(BufferLayout.INTERLEAVED, vertices[0].getDataLayout(), Buffer.DYNAMIC_STORAGE);
	}

}
