package assets.light;

import assets.IDeletable;
import assets.cameras.Camera;
import assets.meshes.IRenderable;
import assets.meshes.Mesh;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

import static math.MathUtils.*;


public class DirectionalLight extends Camera implements IDeletable {
	
	private Vector3f color;
	
	private Vector3f ambient;
	
	private DepthBuffer shadowMap;
	
	
	//****************************** constructor ******************************
	
	
	/**
	 * 
	 * Creates a directional light.
	 * 
	 * @param color The color of the light source.
	 */
	public DirectionalLight(Vector3f color) {
		this(new Vector3f(0f, 0f, -1f), color);
		this.color = color;
	}
	
	
	/**
	 * 
	 * Creates a directional light.
	 * 
	 * @param direction The light's direction.
	 * @param color The color of the light source.
	 */
	public DirectionalLight(Vector3f direction, Vector3f color) {
		super(new Vector3f(0f, 0f, -1f), direction, ProjectionType.ORTHOGRAPHIC);
		this.color = color;
		this.ambient = new Vector3f(0.2f, 0.2f, 0.2f);
	}
	
	
	/**
	 * 
	 * Creates a directional light.
	 * 
	 * @param direction The light's direction.
	 * @param smWidth The width of the light's shadow map.
	 * @param smHeight The height of the light's shadow map.
	 */
	public DirectionalLight(Vector3f direction, Vector3f color, int smWidth, int smHeight) {
		this(direction, color);
		this.initializeShadowMap(smWidth, smHeight);
	}
	
	
	//****************************** Shadow Mapping ******************************
	
	
	/**
	 * 
	 * Initializes this light's Shadow Map to make this light available
	 * for shadow mapping.
	 * 
	 * @param smWidth The Shadow Map's width in pixels.
	 * @param smHeight The Shadow Map's height in pixels.  
	 */
	public void initializeShadowMap(int smWidth, int smHeight) {
		this.shadowMap = new DepthBuffer(smWidth, smHeight);
	}
	
	
	/**
	 * 
	 * @return Returns the light source's shadow map. Returns null if the
	 * shadow map isn't initialized yet.
	 */
	public DepthBuffer getShadowMap() {
		return shadowMap;
	}
	
	
	/**
	 * Prepares the shadow map for rendering meshs to it.
	 */
	public void startShadowMapPass() {
		if (shadowMap == null) {
			System.err.println("The light source " + this + " doesn't support shadow mapping.");
			return;
		}
		
		shadowMap.startRenderPass(this);
	}
	
	
	/**
	 * 
	 * Renders a number of meshes to the Shadow Map.
	 * 
	 * @param meshes The meshes to render to the Shadow Map.
	 */
	public void passToShadowMap(Mesh[] meshes) {
		if (shadowMap == null) {
			System.err.println("The light source " + this + " doesn't support shadow mapping.");
			return;
		}
		
		shadowMap.passToDepthBuffer(meshes, this);
	}
	
	
	/**
	 * 
	 * Renders a mesh to the Shadow Map.
	 * 
	 * @param mesh The mesh to render to the Shadow Map.
	 */
	public void passToShadowMap(Mesh mesh) {
		if (shadowMap == null) {
			System.err.println("The light source " + this + " doesn't support shadow mapping.");
			return;
		}
		
		shadowMap.passToDepthBuffer(mesh);
	}
	
	
	/**
	 * Ends the current pass to the Shadow Map.
	 */
	public void endShadowMapPass() {
		if (shadowMap == null) {
			System.err.println("The light source " + this + " doesn't support shadow mapping.");
			return;
		}
		
		shadowMap.endRenderPass();
	}
	
	
	public boolean hasShadowMap() {
		return shadowMap != null;
	}
	
	
	//****************************** Transformations ******************************
	
	
	public void fitToFrustrum(Camera camera) {
		//Take the center point of the camera's view frustrum.
		Vector3f center = camera.getFrustrumCenter();
		
		//The distance between the near and far clipping plane of the camera's projection matrix.
		float d = camera.getProjectionMatrix().getFarPlane() - camera.getProjectionMatrix().getNearPlane();
		
		//This light's light direction.
		Vector3f ld = getLightDirection();
		
		//Define a position for this light source.
		Vector3f position = new Vector3f(center.getA() + d * -ld.getA(), center.getB() + d * -ld.getB(), center.getC() + d * -ld.getC());
		this.setPosition(position);
		
		
		float minX, minY, minZ, maxX, maxY, maxZ;
		minX = minY = minZ = Float.MAX_VALUE;
		maxX = maxY = maxZ = Float.MIN_VALUE;
		
		Vector4f[] corners = camera.getFrustrumCorners();
		
		for (int i = 0; i < corners.length; ++i) {
			corners[i] = this.getViewMatrix().times(corners[i]);
			
			if (corners[i].getA() < minX)
				minX = corners[i].getA();
			
			if (corners[i].getA() > maxX)
				maxX = corners[i].getA();
			
			if (corners[i].getB() < minY)
				minY = corners[i].getB();
			
			if (corners[i].getB() > maxY)
				maxY = corners[i].getB();
			
			if (corners[i].getC() < minZ)
				minZ = corners[i].getC();
			
			if (corners[i].getC() > maxZ)
				maxZ = corners[i].getC();
		}
		
		System.out.println("X: " + minX + " to " + maxX + "; " + 
						   "Y: " + minY + " to " + maxY + "; " +
						   "Z: " + minZ + " to " + maxZ);
		
		this.setOrthographicProjection(maxZ, minZ, minX, maxX, minY, maxY);		
	}

	
	public void fitToBoundingBox(Mesh mesh) {
		Vector4f[] meshBoundaries = toViewSpace(mesh.getTransformable().getBoundaries());
		
		float xMin, yMin, zMin, xMax, yMax, zMax;
		xMin = yMin = zMin = Float.MAX_VALUE;
		xMax = yMax = zMax = Float.MIN_VALUE;
		
		Vector4f current = null;
		
		for (int i = 0; i < meshBoundaries.length; ++i) {
			current = meshBoundaries[i];
			
			xMin = min(current.getA(), xMin);
			yMin = min(current.getB(), yMin);
			zMin = min(current.getC(), zMin);
			
			xMax = max(current.getA(), xMax);
			yMax = max(current.getB(), yMax);
			zMax = max(current.getC(), zMax);
		}
		
		System.out.println("Min: " + "x=" + xMin + "; y=" + yMin + "; z=" + zMin);
		System.out.println("Max: " + "x=" + xMax + "; y=" + yMax + "; z=" + zMax);
		
		Vector4f center = new Vector4f(xMin + (xMax - xMin) / 2f, yMin + (yMax - yMin) / 2f, zMin + (zMax - zMin) / 2f, 1f);
		Vector4f camPosViewSpace = center.plus(new Vector4f(0f , 0f, (zMin - zMax) / 2f + zMax, 1f));
		Vector4f camPosWorldSpace = toWorldSpace(camPosViewSpace);
		System.out.println(camPosWorldSpace);
		setPosition(camPosWorldSpace);
		
		setZoom(1f / (xMax - xMin) * 2f, 1f / (yMax - yMin) * 2f, 1f / (zMax - zMin) * 2f);
	}
	
	
	public void fitToBoundingBox(Mesh[] models) {
		
	}
	
		
	public Vector3f getLightDirection() {
		return getViewDirection();
	}
	
	
	public Vector3f getColor() {
		return color;
	}


	public void setColor(Vector3f color) {
		this.color = color;
	}
	
	
	public Vector3f getAmbient() {
		return ambient;
	}
	
	
	public void setAmbient(Vector3f ambient) {
		this.ambient = ambient;
	}


	@Override
	public void delete() {
		shadowMap.delete();
	}
	
}
