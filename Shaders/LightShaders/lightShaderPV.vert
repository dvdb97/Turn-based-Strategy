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


//Input:
layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec4 vColor;
layout(location = 2) in vec3 vTexPos;
layout(location = 3) in vec3 vNormal;


//Matrix uniforms:
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

//Light uniforms:
uniform vec3 ambientLight;
uniform Material material;
uniform LightSource light;


void main() {

	//Compute the position:
	vec4 pos = projectionMatrix * viewMatrix * modelMatrix * vec4(vPosition , 1.0);


	//Compute the normals:
	vec3 normal = normalize(mat3(viewMatrix) * mat3(modelMatrix) * vNormal);


	//CameraPosition (Constant):
	vec3 cameraPosition = vec3(0.0, 0.0, 1.0);


	//Light direction:
	vec3 lightDirection = normalize(mat3(viewMatrix) * light.direction);



	//************ Scattered lighting ************


	float diffuse = max(0.0, dot(lightDirection, normal));

	vec3 scatteredLight = ambientLight * material.ambient + light.color * material.diffuse * diffuse;



	//************ Reflected lighting ************


	/*
	 * The vector from the fragment's position to the camera. Will be used for reflection.
	 * If the reflected light at this fragment matches this vector there will be maximum reflected light.
	 */
	vec3 optimalReflection = normalize(cameraPosition - vPosition);

	//Reflect the incoming light
	vec3 reflectionDirection = normalize(reflect(lightDirection, normal));


	float specular = max(0.0, dot(optimalReflection, reflectionDirection));

	if (diffuse == 0) {
		specular = 0;
	} else {
		specular *= specular;
	}


	vec3 reflectedLight = light.color * material.specular * specular;



	//************ Compute the final light ************


	vec3 rgb = min(vColor.rgb * scatteredLight + reflectedLight, vec3(1.0));



	//************ Output ************


	color = vec4(rgb, vColor.a);

	gl_Position = pos;
}



