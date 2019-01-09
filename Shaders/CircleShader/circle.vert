#version 460 core

layout(location = 0) in vec3 vPosition;
layout(location = 2) in vec2 vTexCoord;

uniform mat4 mvpMatrix;

out vec2 fTexCoord;

void main() {
	gl_Position = mvpMatrix * vec4(vPosition, 1.0f);
	fTexCoord = vTexCoord;
}
