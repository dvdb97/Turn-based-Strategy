#version 330 core

layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec4 vColor;

uniform mat4 mvpMatrix;
uniform vec4 u_Color;


void main() {
	vec4 pos = mvpMatrix * vec4(vPosition, 1.0f);

	gl_Position = pos;
}
