#version 330 core

in vec2 texCoords;

uniform sampler2D tex;

uniform vec4 u_Color;
uniform int u_textured;
uniform int u_fontRendering;

out vec4 fColor;


vec4 fetchTexels() {
	
	vec4 color = texture(tex, texCoords);

	if (u_fontRendering == 1) {

		return color;

	}
	
	return color;

}


void main() {

	vec4 color;

	if(u_textured == 1) {
		color = fetchTexels();
	} else {
		color = u_Color;
	}

	fColor = color;

}
