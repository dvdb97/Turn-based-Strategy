#version 330 core

layout(location = 0) in vec3 vPosition;
layout(location = 2) in vec2 vTexPos;

uniform mat4 u_mvpMatrix;

out vec2 pass_tex_pos;


void main() {
	pass_tex_pos = vTexPos;

	gl_Position = u_mvpMatrix * vec4(vPosition, 1);
}
