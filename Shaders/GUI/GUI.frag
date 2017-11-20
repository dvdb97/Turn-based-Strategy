#version 330 core

in vec2 textureCoords;
in vec3 color;

uniform sampler2D sampler;

out vec4 fColor;

void main() {
	fColor = texture(sampler, textureCoords);
}
