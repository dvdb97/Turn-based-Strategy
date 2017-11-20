#version 330 core

in vec2 vs_tex_coord;

uniform sampler2D textureSampler;

out vec4 fColor;

void main() {

	fColor = texture(textureSampler, vs_tex_coord);

}
