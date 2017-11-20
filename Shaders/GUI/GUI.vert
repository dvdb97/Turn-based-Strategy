#version 330 core

layout(location = 0) in vec2 vPosition;
layout(location = 1) in vec2 texPos;
layout(location = 2) in vec3 vColor;

out vec2 textureCoords;
out vec3 color;

void main() {

	textureCoords = texPos;

	color = vColor;

	vec3 pos = vec3(vPosition, 1.0);

	gl_Position = vec4(pos, 1.0);

}
