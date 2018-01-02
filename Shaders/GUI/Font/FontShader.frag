#version 330 core

in vec2 texCoords;

uniform sampler2D tex;

uniform vec4 fontColor;

out vec4 fColor;


void main() {

	vec4 color = texture(tex, texCoords);

	if (length(color) >= length(vec4(0.3, 0.3, 0.3, 1.0))) {
		color = vec4(color.r, color.g, color.b, 0.0);
	}

	fColor = color;

}
