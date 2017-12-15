#version 330 core

layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec2 vTexCoords;

uniform mat4 mvpMatrix;

out vec2 texCoords;


void main() {

	texCoords = vTexCoords;

	gl_Position = mvpMatrix * vec4(vPosition, 1.0);

}

