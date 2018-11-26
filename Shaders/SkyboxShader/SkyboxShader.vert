#version 330 core

layout (location = 0) in vec3 vPosition;

//model, view and projection matrix
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

out vec3 texCoords;

void main() {
	texCoords = vPosition;
	gl_Position = projectionMatrix * viewMatrix * vec4(vPosition, 1.0);
}
