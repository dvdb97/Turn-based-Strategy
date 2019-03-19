#version 330 core

struct Material {
	vec4 color;
	vec3 emission;
	vec3 ambient;
	vec3 diffuse;
	vec3 specular;
	float shininess;
};

in vec3 normal;
in vec3 position;

uniform Material material;
uniform vec3 cameraPosition;
uniform samplerCube skybox;

out vec4 fColor;


void main() {
	vec3 lightDirection = normalize(position - cameraPosition);
	vec3 texCoord = reflect(lightDirection, normalize(normal));
	vec4 reflection = vec4(texture(skybox, texCoord).rgb, 1.0);

	fColor = mix(material.color, reflection, vec4(material.specular, 1.0));

}
