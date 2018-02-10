#version 330 core

layout(location = 0) in vec2 vPosition;
layout(location = 2) in vec2 vTexPos;

uniform mat3 u_Matrix;
uniform vec4 u_Color;
uniform sampler2D sampler;
uniform int u_textured;

out vec2 out_textureCoords;
out vec4 out_color;

void main() {

	vec3 pos = u_Matrix * vec3(vPosition, 1);

	gl_Position = vec4(pos.x, pos.y, 1.0, 1.0);
	
	out_textureCoords = vTexPos;
	out_color = u_Color;

}
