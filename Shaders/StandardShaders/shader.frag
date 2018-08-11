#version 330 core

struct Material {
	vec4 color;
	vec3 emission;
	vec3 ambient;
	vec3 diffuse;
	vec3 specular;
	float shininess;
};

in vec4 out_Color;

uniform Material material;

out vec4 fColor;

void main() {
	fColor = vec4(1, 0, 0, 1);
}
