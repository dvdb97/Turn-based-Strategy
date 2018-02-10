#version 330 core

in vec2 out_textureCoords;
in vec4 out_color;

uniform sampler2D sampler;
uniform int u_textured;

out vec4 fColor;

void main() {

	if (u_textured == 1) {
		fColor = texture(sampler, out_textureCoords);
	} else {
		fColor = out_color;
	}
	
}
