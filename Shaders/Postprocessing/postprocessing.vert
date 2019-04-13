#version 460 core

layout(location = 0) in vec3 vPosition;
layout(location = 2) in vec2 vTexCoord;

out vec2 fTexCoord;
out vec4 fColor;

void main() {
	fTexCoord = vTexCoord;
	gl_Position = vec4(vPosition, 1.0);
}

