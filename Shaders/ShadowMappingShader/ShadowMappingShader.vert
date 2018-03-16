#version 330 core

layout(location = 0) in vec3 vPosition;

uniform mat4 u_mvpMatrix;

void main() {
	gl_Position = u_mvpMatrix * vec4(vPosition, 1.0);
}
