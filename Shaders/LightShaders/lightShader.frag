#version 330 core


//Defines the properties of light on this object
struct Material {
	vec4 color;
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
	vec3 fragCoordModelSpace;
	vec3 fragCoordWorldSpace;
	vec3 fragCoordLightSpace;
	vec4 fragColor;
	vec2 fragTexPos;
	vec3 fragNormalModelSpace;
	vec3 fragNormalWorldSpace;
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


//The final color
out vec4 fColor;


vec4 color() {
	return material.color;
}

vec3 computeAmbientLight() {
	return ambientLight * material.ambient;
}


float computeShadow() {
	if (shadowsActive == 0) {
		return 1.0f;
	}

	//TODO: Move to vertex shader.
	vec3 shadowTexCoords = 0.5f * fs_in.fragCoordLightSpace.xyz + vec3(0.5f, 0.5f, 0.5f);

	vec3 normalizedNormal = normalize(fs_in.fragNormalWorldSpace);
	vec3 lightDirection = normalize(light.direction);

	float bias = 0.001 + 0.005*tan(acos(dot(lightDirection, normalizedNormal)));

	bias = clamp(bias, 0, 0.01);

	float visibility = 1f;

	if (shadowTexCoords.z - bias > texture(shadowMap, shadowTexCoords.xy).z) {
		visibility = 0.5f;
	}

	return visibility;
}


vec4 computeLight() {
	vec3 normalizedNormal = normalize(fs_in.fragNormalWorldSpace);

	//********* diffuse light *********

	vec3 lightDirection = normalize(light.direction);

	float diffuse = max(0.0, -dot(lightDirection, normalizedNormal));

	vec3 diffuseLight = diffuse * material.diffuse * light.color;


	//********* specular light *********

	/*
	 * The vector from the fragment's position to the camera. Will be used for reflection.
	 * If the reflected light at this fragment matches this vector there will be maximum reflected light.
	 */
	vec3 viewDirection = normalize(cameraPosition - fs_in.fragCoordModelSpace);
	
	//Reflect the incoming light
	vec3 reflectionDirection = normalize(reflect(lightDirection, normalizedNormal));
	
	float specular = max(0.0, dot(viewDirection, reflectionDirection));
	
	if (diffuse == 0) {
		specular = 0;
	}

	vec3 specularLight = material.specular * pow(specular, material.shininess) * light.color;


	//********* final result *********

	vec4 col = color();

	float shadow = computeShadow();

	vec3 finalColor = (computeAmbientLight() + shadow * (diffuseLight + specularLight)) * col.rgb;

	return vec4(min(vec3(1.0f, 1.0f, 1.0f), finalColor), material.color.a);
}


void main() {
	vec2 shadowTexCoords = 0.5f * fs_in.fragCoordLightSpace.xy + vec2(0.5f, 0.5f);

	fColor = computeLight();
}
