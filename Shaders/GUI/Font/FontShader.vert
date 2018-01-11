#version 330 core

layout(location = 0) in vec3 vPosition;
layout(location = 2) in vec2 vTexCoords;

uniform sampler2D tex;
uniform mat4 mvpMatrix;

out vec2 texCoords;


void main() {

	texCoords = vTexCoords;

	vec4 pos = mvpMatrix * vec4(vPosition, 1.0);

	gl_Position = pos;

}
