#version 330 core

layout(location = 0) in vec2 vPosition;
layout(location = 2) in vec2 texPos;

uniform mat3 u_Matrix;

out vec2 textureCoords;

void main() {

	textureCoords = texPos;

	vec3 pos = u_Matrix * vec3(vPosition, 1);

	gl_Position = vec4(pos.x, pos.y, 0.9, 1.0);

}
