#version 330 core

layout(location = 0) in vec3 vPosition;
layout(location = 2) in vec2 vTexCoords;

uniform mat4 u_Matrix;
uniform mat4 u_ProjectionMatrix;

out vec2 texCoords;


void main() {

	texCoords = vTexCoords;

	vec4 pos = u_ProjectionMatrix * u_Matrix * vec4(vPosition, 1.0);

	gl_Position = pos;

}
