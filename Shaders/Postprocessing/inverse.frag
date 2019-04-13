#version 460 core

in vec2 fTexCoord;

uniform sampler2D sampler;

out vec4 color;

void main() {
	vec4 col = texture(sampler, fTexCoord);
	color = vec4(1 - col.r, 1 - col.g, 1 - col.b, col.a);
}
