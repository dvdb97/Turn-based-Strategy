#version 330 core

in vec2 textureCoords;

uniform sampler2D sampler;
uniform vec4 u_color;
uniform int u_textured;

out vec4 fColor;

void main() {

	if (u_textured == 1) {
		fColor = texture(sampler, textureCoords);
	} else {
		fColor = u_color;
	}
	
}
