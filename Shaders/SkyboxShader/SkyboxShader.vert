#version 330 core

layout (location = 0) in vec3 vPosition;

//model, view and projection matrix
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

out vec3 texCoords;

void main() {
	texCoords = vPosition;
	gl_Position = projectionMatrix * vec4(mat3(viewMatrix) * vPosition, 1.0);
}
