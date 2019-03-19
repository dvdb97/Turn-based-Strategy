#version 460 core


//Defines the properties of light on this object
struct Material {
	vec4 color;
	sampler2D texture;
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
	vec4 fragColor;
	vec2 fragTexPos;
	vec3 shadowTexCoords;
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


//The subroutine that is used to compute the base color.
subroutine vec4 ColorFunction();
subroutine uniform ColorFunction colorFunc;


//The subroutine that is used to compute the final color.
subroutine vec4 FinalColorFunction(vec4 color);
subroutine uniform FinalColorFunction finalColorFunc;


//The subroutine that uses the material's color
subroutine (ColorFunction) vec4 materialColor() {
	return material.color;
}

//The subroutine that reads the color for this fragment from a texture.
subroutine (ColorFunction) vec4 textureColor() {
	return texture(material.texture, fs_in.fragTexPos);
}

//The subroutine that uses the attribute's color value.
subroutine (ColorFunction) vec4 attribColor() {
	return fs_in.fragColor;
}


//The subroutine that just takes the final light color as the fragment's final color.
subroutine(FinalColorFunction) vec4 finalLightColor(vec4 color) {
	return color;
}

//The subroutine that applies toon shading to the final fragment color.
subroutine(FinalColorFunction) vec4 toonShading(vec4 color) {
	return 0.2 * round(color * 5.0);
}


/*********************** functions to compute light ***********************/


//function for computing ambient light.
vec3 computeAmbientLight() {
	return ambientLight * material.ambient;
}


//function for computing diffuse light.
vec3 computeDiffuseLight() {
	vec3 normalizedNormal = normalize(fs_in.fragNormalWorldSpace);

	vec3 lightDirection = normalize(light.direction);

	float diffuse = max(0.0, -dot(lightDirection, normalizedNormal));

	return diffuse * material.diffuse * light.color;
}


//function for computing specular light.
vec3 computeSpecularLight() {
	vec3 normalizedNormal = normalize(fs_in.fragNormalWorldSpace);
	vec3 viewDirection = normalize(cameraPosition - fs_in.fragCoordModelSpace);

	vec3 lightDirection = normalize(light.direction);

	//Reflect the incoming light
	vec3 reflectionDirection = normalize(reflect(lightDirection, normalizedNormal));

	float specular = max(0.0, dot(viewDirection, reflectionDirection));

	/*if (diffuse == 0) {
		specular = 0;
	}*/

	return material.specular * pow(specular, material.shininess) * light.color;
}


//function for computing shadows.
float computeShadow() {
	if (shadowsActive == 0) {
		return 1.0f;
	}

	vec3 normalizedNormal = normalize(fs_in.fragNormalWorldSpace);
	vec3 lightDirection = normalize(light.direction);

	float bias = clamp(0.01 + 0.005*tan(acos(dot(lightDirection, normalizedNormal))), 0.0, 0.01);

	float visibility = 1f;

	if (fs_in.shadowTexCoords.z - bias > texture(shadowMap, fs_in.shadowTexCoords.xy).z) {
		visibility = 0.5f;
	}

	return visibility;
}


/*********************** main function ***********************/


void main() {
	vec4 color = colorFunc();
	vec3 finalLight = (computeAmbientLight() + computeShadow() * (computeDiffuseLight() + computeSpecularLight())) * color.rgb;
	vec4 finalColor = vec4(min(vec3(1.0f, 1.0f, 1.0f), finalLight), color.a);
	fColor = finalColorFunc(finalColor);
}
