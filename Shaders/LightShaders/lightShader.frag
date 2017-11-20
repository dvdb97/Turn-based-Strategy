#version 330 core


//Defines the properties of light on this object
struct Material {
	vec3 emission;
	vec3 ambient;
	vec3 diffuse;
	vec3 specular;
	float shininess;
};

//Defines the properties of a light source:
struct LightSource {
	vec3 direction;
	vec3 color;
};


//Input variables:
in vec4 color;
in vec3 fPosition;
in vec3 texturePosition;
in vec3 normal;
in mat3 out_viewMatrix;


//Uniform variables:
uniform sampler2D texture;
uniform vec3 cameraPosition;
uniform vec3 ambientLight;

uniform Material material;
uniform LightSource light;


out vec4 fColor;


void main() {

	//vec3 cameraPosition = vec3(0.0, 0.0, 1.0);
	vec3 lightDirection = normalize(light.direction);
	
	//************ Scattered lighting ************

	
	float diffuse = max(0.0, dot(lightDirection, normal));
	
	vec3 scatteredLight = ambientLight * material.ambient + light.color * material.diffuse * diffuse;
	

	//************ Reflected lighting ************

	
	/*
	 * The vector from the fragment's position to the camera. Will be used for reflection.
	 * If the reflected light at this fragment matches this vector there will be maximum reflected light.
	 */
	vec3 optimalReflection = normalize(cameraPosition - fPosition);
	
	//Reflect the incoming light
	vec3 reflectionDirection = normalize(reflect(lightDirection, normal));
	
	float specular = max(0.0, dot(optimalReflection, reflectionDirection));
	
	if (diffuse == 0) {
		specular = 0;
	} else {
		specular *= 0.05; //TODO: Change it back!
	}
	
	
	vec3 reflectedLight = light.color * material.specular * specular;
	
	
	//************ Compute the final light ************
	
	
	vec3 rgb = min(color.rgb * scatteredLight + reflectedLight, vec3(1.0));
	
	fColor = vec4(rgb, color.a);

}
