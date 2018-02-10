#version 330 core

in vec2 texCoords;

uniform sampler2D tex;

uniform vec4 u_Color;
uniform int u_textured;

out vec4 fColor;


vec4 fetchGUITexels() {

	vec4 color = texture(tex, texCoords);
	
	if (length(color) >= length(vec4(0.3, 0.3, 0.3, 1.0))) {
		color = vec4(u_Color.r, u_Color.g, u_Color.b, 0.0);
	}
	
	return color;

}


void main() {

	vec4 color;

	if(u_textured == 1) {
		color = fetchGUITexels();
	} else {
		color = u_Color;
	}

	fColor = color;

}
