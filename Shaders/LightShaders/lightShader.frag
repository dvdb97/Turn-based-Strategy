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


//Incoming variables variables:
in VS_OUT {
	vec3 fragCoord;
	vec4 fragCoordLightSpace;
	vec4 fragColor;
	vec3 fragTexPos;
	vec3 fragNormal;
} fs_in;


//Uniform variables:
uniform vec3 cameraPosition;


//Light information
uniform vec3 ambientLight;
uniform Material material;
uniform LightSource light;


//Shadow information
uniform int shadowsActive;
uniform sampler2D shadowMap;


out vec4 fColor;


vec3 computeAmbientLight() {
	return ambientLight * material.ambient;
}


float computeShadow() {

	if (shadowsActive == 0) {
		return 1.0f;
	}

	vec3 projCoords = vec3(fs_in.fragCoordLightSpace) * 0.5f + vec3(0.5f, 0.5f, 0.5f);

	float bias = 0.005f;//max(0.05 * (1.0 - dot(normalize(fs_in.fragNormal), normalize(light.direction))), 0.05);

	float shadowMapDepth = texture(shadowMap, projCoords.xy).r + bias;

	float currentDepth = projCoords.z;

	return currentDepth > shadowMapDepth ? 0.2f : 1.0f;

}


vec3 computeLight() {

	vec3 normalizedNormal = normalize(fs_in.fragNormal);

	//********* diffuse light *********

	vec3 lightDirection = normalize(light.direction);

	float diffuse = max(0.0, -dot(lightDirection, normalizedNormal));

	vec3 diffuseLight = diffuse * material.diffuse * light.color;


	//********* specular light *********

	/*
	 * The vector from the fragment's position to the camera. Will be used for reflection.
	 * If the reflected light at this fragment matches this vector there will be maximum reflected light.
	 */
	vec3 viewDirection = normalize(cameraPosition - fs_in.fragCoord);
	
	//Reflect the incoming light
	vec3 reflectionDirection = normalize(reflect(lightDirection, normalizedNormal));
	
	float specular = max(0.0, dot(viewDirection, reflectionDirection));
	
	if (diffuse == 0) {
		specular = 0;
	}

	vec3 specularLight = material.specular * pow(specular, 64f) * light.color;


	//********* final result *********


	vec3 finalColor = (computeAmbientLight() + computeShadow() * (diffuseLight + specularLight)) * fs_in.fragColor.rgb;


	return min(vec3(1.0f, 1.0f, 1.0f), finalColor);

}


void main() {

	vec3 finalColor = computeLight();
	
	fColor = vec4(finalColor, fs_in.fragColor.a);

}
