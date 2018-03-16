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


vec3 computeDiffuseLight() {
	vec3 lightDirection = normalize(light.direction);

	float diffuse = max(0.0, dot(lightDirection, normalize(fs_in.fragNormal)));

	return material.diffuse * diffuse * light.color;
}


vec3 computeAmbientLight() {
	return ambientLight * material.ambient;
}


vec3 computeSpecularLight() {
	
	vec3 lightDirection = normalize(light.direction);

	/*
	 * The vector from the fragment's position to the camera. Will be used for reflection.
	 * If the reflected light at this fragment matches this vector there will be maximum reflected light.
	 */
	vec3 viewDirection = normalize(cameraPosition - fs_in.fragCoord);
	
	//Reflect the incoming light
	vec3 reflectionDirection = normalize(reflect(lightDirection, normalize(fs_in.fragNormal)));
	
	float specular = max(0.0, dot(viewDirection, reflectionDirection));
	
	return material.specular * pow(specular, 64.0f) * light.color;

}


float computeShadow() {

	if (shadowsActive == 0) {
		return 0.0f;
	}

	vec3 projCoords = (fs_in.fragCoordLightSpace.xyz / fs_in.fragCoordLightSpace.w) * 0.5f + 0.5f;

	float shadowMapDepth = texture(shadowMap, projCoords.xy).r;

	float currentDepth = projCoords.z;

	return currentDepth > shadowMapDepth ? 0.8f : 0.0f;

}


void main() {

	vec3 scatteredLight = computeDiffuseLight();

	vec3 reflectedLight = computeSpecularLight();
	
	vec3 ambient = computeAmbientLight();

	float shadow = computeShadow();

	vec3 rgb = (ambient + (1.0 - shadow) * (scatteredLight + reflectedLight)) * fs_in.fragColor.rgb;
	
	fColor = vec4(rgb, fs_in.fragColor.a);

}
