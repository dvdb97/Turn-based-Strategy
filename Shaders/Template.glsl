#.vert
#version 330 core

layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec4 vColor;
layout(location = 2) in vec3 vTexPos;
layout(location = 3) in vec3 vNormal;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

uniform mat4 mvpMatrix;

void main() {

}


#.frag
#version 330 core

struct Material {
	vec3 emission;
	vec3 ambient;
	vec3 diffuse;
	vec3 specular;
	float shininess;
};


struct LightSource {
	vec3 direction;
	vec3 color;
};


struct Camera {
	vec3 cameraPosition;
};


uniform Material material;


out vec4 fColor;

void main() {

}

