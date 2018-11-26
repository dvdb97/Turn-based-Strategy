#version 330 core

layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec4 vColor;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

uniform mat4 mvpMatrix;

out vec4 out_Color;


void main() {
	out_Color = vColor;

	gl_Position = mvpMatrix * vec4(vPosition, 1.0f);;
}
