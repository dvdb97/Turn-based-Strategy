#version 460 core

in vec2 fTexCoord;

uniform sampler2D sampler;

out vec4 color;

void main() {
	vec4 col = texture(sampler, fTexCoord);
	color = 0.2 * round(col * 5.0);
}
