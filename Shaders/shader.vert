#version 330 core

layout(location = 0) in vec3 vPosition;
layout(location = 2) in vec2 in_tex_coord;


uniform mat4 mvpMatrix;

out vec2 vs_tex_coord;


void main() {
	vec4 pos = mvpMatrix * vec4(vPosition, 1.0f);

	vs_tex_coord = in_tex_coord;

	gl_Position = pos;
}
