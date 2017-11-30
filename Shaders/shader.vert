#version 330 core

layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec4 vColor;


uniform mat4 mvpMatrix;

out vec4 out_Color;


void main() {
	vec4 pos = mvpMatrix * vec4(vPosition, 1.0f);

	out_Color = vColor;

	gl_Position = pos;
}
