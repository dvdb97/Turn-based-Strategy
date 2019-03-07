package assets.meshes;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;

import assets.Deletable;
import assets.buffers.VertexBuffer;
import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import assets.meshes.geometry.Vertex;
import assets.scene.Scene;
import assets.textures.Texture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public abstract class Mesh extends Deletable implements IRenderable {
	
	private enum Attribute {
		POSITION, COLOR, TEXCOORD, NORMAL
	};
	
	protected HashMap<Attribute, FloatBuffer> vertexData;
	
	protected HashMap<Attribute, VertexBuffer> vertexBuffers;
	
	private VertexArrayObject vao;
	
	private IntBuffer indexBuffer;
	
	public final Transformable transformable;
	
	private Material material;
	
	private Texture texture;
	
	private int drawMode = GL_TRIANGLES;
	
	
	public Mesh() {
		this.vao = new VertexArrayObject();
		this.transformable = new Transformable();
		vertexData = new HashMap<Attribute, FloatBuffer>();
		vertexBuffers = new HashMap<Attribute, VertexBuffer>();
	}
	
	
	public Mesh(Material material) {
		this();
		this.material = material;
	}
	
	
	public Mesh(int drawMode) {
		this(Material.standard);
		this.drawMode = drawMode;
	}
	
	
	public Mesh(int drawMode, Material material, Texture texture) {
		this(drawMode);
		
		this.material = material;
		this.texture = texture;
	}
	
	
	public Mesh(Material material, Texture texture) {
		this(material);
		
		this.texture = texture;
	}
	
	
	public void setData(Vertex[] vertices, IntBuffer indices) {
		FloatBuffer posBuffer = BufferUtils.createFloatBuffer(vertices.length * 3);
		FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(vertices.length * 4);
		FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(vertices.length * 3);
		
		for (int i = 0; i < vertices.length; ++i) {
			posBuffer.put(vertices[i].getA());
			posBuffer.put(vertices[i].getB());
			posBuffer.put(vertices[i].getC());
			
			colorBuffer.put(vertices[i].getRed());
			colorBuffer.put(vertices[i].getGreen());
			colorBuffer.put(vertices[i].getBlue());
			colorBuffer.put(vertices[i].getAlpha());
			
			normalBuffer.put(vertices[i].getNormal().getA());
			normalBuffer.put(vertices[i].getNormal().getB());
			normalBuffer.put(vertices[i].getNormal().getC());
		}
		
		posBuffer.flip();
		colorBuffer.flip();
		normalBuffer.flip();
		
		this.setPositionData(posBuffer);
		this.setColorData(colorBuffer);
		this.setNormalData(normalBuffer, 3);
		this.setIndexBuffer(indices);
		
		if (vertices[0].isTextured()) {
			FloatBuffer texBuffer = BufferUtils.createFloatBuffer(vertices.length * 2);
			
			for (int i = 0; i < vertices.length; ++i) {
				texBuffer.put(vertices[i].getTexturePositions().getA());
				texBuffer.put(vertices[i].getTexturePositions().getB());
			}
			
			texBuffer.flip();
			this.setTexCoordData(texBuffer, 2);
		}
	}
	
	
	/**
	 * 
	 * Stores vertex data on the GPU and makes those vertices available
	 * for rendering.
	 * 
	 * @param vertices The vertices to be stored.
	 * @param indices The indices that describe the mesh's triangles.
	 */
	public void setData(Vertex[] vertices, IntBuffer indices, boolean normals) {
		if(normals) {
			setData(vertices, indices);
			return;
		}
		FloatBuffer posBuffer = BufferUtils.createFloatBuffer(vertices.length * 3);
		FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(vertices.length * 4);
		
		for (int i = 0; i < vertices.length; ++i) {
			posBuffer.put(vertices[i].getA());
			posBuffer.put(vertices[i].getB());
			posBuffer.put(vertices[i].getC());
			
			colorBuffer.put(vertices[i].getRed());
			colorBuffer.put(vertices[i].getGreen());
			colorBuffer.put(vertices[i].getBlue());
			colorBuffer.put(vertices[i].getAlpha());
			
		}
		
		posBuffer.flip();
		colorBuffer.flip();
		
		this.setPositionData(posBuffer);
		this.setColorData(colorBuffer);
		this.setIndexBuffer(indices);
		
		if (vertices[0].isTextured()) {
			FloatBuffer texBuffer = BufferUtils.createFloatBuffer(vertices.length * 2);
			
			for (int i = 0; i < vertices.length; ++i) {
				texBuffer.put(vertices[i].getTexturePositions().getA());
				texBuffer.put(vertices[i].getTexturePositions().getB());
			}
			
			texBuffer.flip();
			this.setTexCoordData(texBuffer, 2);
		}
	}
	
	/**
	 * 
	 * Sets this Mesh's position data and stores it on the gpu.
	 * 
	 * @param buffer A buffer containing position data.
	 * @param size The number of floats representing a position.
	 */
	public void setPositionData(FloatBuffer buffer, int size) {
		this.setAttribute(buffer, Attribute.POSITION, 0, size);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's position data and stores it on the gpu.
	 * This method assumes that a position attribut always consists
	 * of three values.
	 * 
	 * @param buffer A buffer containing position data.
	 */
	public void setPositionData(FloatBuffer buffer) {
		this.setPositionData(buffer, 3);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's position data by setting a pointer to data that
	 * is already stored on the gpu.
	 * 
	 * @param buffer A vertex buffer containing position data.
	 * @param size The number of floats representing a position.
	 */
	public void setPositionVertexBuffer(VertexBuffer buffer, int size) {
		this.setVertexBuffer(buffer, Attribute.POSITION, 0, size);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's position data as a FloatBuffer.
	 */
	public FloatBuffer getPositionData() {
		return vertexData.get(Attribute.POSITION);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's position data as a VertexBuffer.
	 */
	public VertexBuffer getPositionVertexBuffer() {
		return vertexBuffers.get(Attribute.POSITION);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's color data and stores it on the gpu.
	 * 
	 * @param buffer A buffer containing color data.
	 * @param size The number of floats representing a color.
	 */
	public void setColorData(FloatBuffer buffer, int size) {
		this.setAttribute(buffer, Attribute.COLOR, 1, size);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's color data and stores it on the gpu.
	 * This method assumes that a color attribut always consists
	 * of four values.
	 * 
	 * @param buffer A buffer containing color data.
	 */
	public void setColorData(FloatBuffer buffer) {
		this.setColorData(buffer, 4);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's color data by setting a pointer to data that
	 * is already stored on the gpu.
	 * 
	 * @param buffer A vertex buffer containing color data.
	 * @param size The number of floats representing a color.
	 */
	public void setColorVertexBuffer(VertexBuffer buffer, int size) {
		this.setVertexBuffer(buffer, Attribute.COLOR, 1, size);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's color data as a FloatBuffer.
	 */
	public FloatBuffer getColorData() {
		return vertexData.get(Attribute.COLOR);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's color data as a VertexBuffer.
	 */
	public VertexBuffer getColorVertexBuffer() {
		return vertexBuffers.get(Attribute.COLOR);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's texture coordinate data and stores it on the gpu.
	 * 
	 * @param buffer A buffer containing texture coordinate data.
	 * @param size The number of floats representing a texture coordinate.
	 */
	public void setTexCoordData(FloatBuffer buffer, int size) {
		this.setAttribute(buffer, Attribute.TEXCOORD, 2, size);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's texture coordinate data by setting a pointer to data that
	 * is already stored on the gpu.
	 * 
	 * @param buffer A vertex buffer containing texture coordinate data.
	 * @param size The number of floats representing a texture coordinate.
	 */
	public void setTexCoordVertexBuffer(VertexBuffer buffer, int size) {
		this.setVertexBuffer(buffer, Attribute.TEXCOORD, 2, size);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's texture coordinate data as a FloatBuffer.
	 */
	public FloatBuffer getTexCoordData() {
		return vertexData.get(Attribute.TEXCOORD);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's texture coordinate data as a VertexBuffer.
	 */
	public VertexBuffer getTexCoordVertexBuffer() {
		return vertexBuffers.get(Attribute.TEXCOORD);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's normal data and stores it on the gpu.
	 * 
	 * @param buffer A buffer containing normal data.
	 * @param size The number of floats representing a normal.
	 */
	public void setNormalData(FloatBuffer buffer, int size) {
		this.setAttribute(buffer, Attribute.NORMAL, 3, size);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's normal data by setting a pointer to data that
	 * is already stored on the gpu.
	 * 
	 * @param buffer A vertex buffer containing normal data.
	 * @param size The number of floats representing a normal.
	 */
	public void setNormalVertexBuffer(VertexBuffer buffer, int size) {
		this.setVertexBuffer(buffer, Attribute.NORMAL, 3, size);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's normal data as a FloatBuffer.
	 */
	public FloatBuffer getNormalData() {
		return vertexData.get(Attribute.NORMAL);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's normal data as a VertexBuffer.
	 */
	public VertexBuffer getNormalVertexBuffer() {
		return vertexBuffers.get(Attribute.NORMAL);
	}
	
	
	private void setAttribute(FloatBuffer buffer, Attribute attribute, int pos, int size) {
		this.vertexData.put(attribute, buffer);
		VertexBuffer vertexBuffer = new VertexBuffer(buffer);
		this.setVertexBuffer(vertexBuffer, attribute, pos, size);
	}
	
	
	private void setVertexBuffer(VertexBuffer buffer, Attribute attribute, int pos, int size) {
		vao.setVertexAttributePointer(buffer, pos, size, 0, 0);
		this.vertexBuffers.put(attribute, buffer);
	}
	
	
	/**
	 * 
	 * Sets the index buffer of this mesh.
	 * 
	 * @param buffer The buffer containing the indices.
	 */
	public void setIndexBuffer(IntBuffer buffer) {
		this.indexBuffer = buffer;
	}
	
	
	/**
	 * 
	 * @return Returns the mesh's index buffer.
	 */
	public IntBuffer getIndexBuffer() {
		return indexBuffer;
	}
	
	
	public Transformable getTransformable() {
		return transformable;
	}
	
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	
	public Material getMaterial() {
		return material;
	}	
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	
	public Texture getTexture() {
		return texture;
	}

	@Override
	public void render() {
		vao.enableVertexAttribArray();
		
		glDrawElements(drawMode, indexBuffer);
		
		vao.disableVertexAttribArray();
	}


	@Override
	public void render(Camera camera, DirectionalLight light) {
		onDrawStart(camera, light);
		
		this.render();
		
		onDrawEnd(camera, light);
	}
	
	
	@Override
	public void render(Scene scene) {
		onDrawStart(scene);
		
		this.render();
		
		onDrawEnd(scene);
	}
	
	
	/**
	 * 
	 * @param camera The camera that is used to look at the mesh.
	 * @param light The light source that is used to render this mesh.
	 */
	protected abstract void onDrawStart(Camera camera, DirectionalLight light);
	
	
	/**
	 * 
	 * Clean up after rendering.
	 * 
	 * @param camera The camera that is used to look at the mesh.
	 * @param light The light source that is used to render this mesh.
	 */
	protected abstract void onDrawEnd(Camera camera, DirectionalLight light);
	
	
	/**
	 * 
	 * @param scene The scene that is currently being rendered.
	 */
	protected abstract void onDrawStart(Scene scene);
	
	
	/**
	 * 
	 * Clean up after rendering.
	 * 
	 * @param scene The scene that is currently being rendered.
	 */
	protected abstract void onDrawEnd(Scene scene);


	@Override
	public void delete() {
		for (VertexBuffer buffer : vertexBuffers.values()) {
			buffer.delete();
		}
		
		vao.delete();		
	}

}
