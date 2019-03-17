#version 330 core

layout(location = 0) in vec3 vPosition;
layout(location = 3) in vec3 vNormal;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform mat4 mvpMatrix;

out vec3 normal;
out vec3 position;


void main() {
	normal = mat3(modelMatrix) * vNormal;
	position = vec3(modelMatrix * vec4(vPosition, 1.0));
	gl_Position = mvpMatrix * vec4(vPosition, 1.0);
}
