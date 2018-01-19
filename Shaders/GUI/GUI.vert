#version 330 core

layout(location = 0) in vec2 vPosition;
layout(location = 1) in vec2 texPos;

out vec2 textureCoords;

void main() {

	textureCoords = texPos;

	vec3 pos = vec3(vPosition, 1.0);

	gl_Position = vec4(pos, 1.0);

}
