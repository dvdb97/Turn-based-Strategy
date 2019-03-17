#version 460 core

layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec4 vColor;
layout(location = 2) in vec2 vTexCoord;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

uniform mat4 mvpMatrix;

out vec4 fColor;
out vec2 fTexCoord;


void main() {
	fColor = vColor;
	fTexCoord = vTexCoord;
	gl_Position = mvpMatrix * vec4(vPosition, 1.0f);;
}
