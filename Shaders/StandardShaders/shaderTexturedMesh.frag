#version 330 core

in vec2 pass_tex_pos;

uniform sampler2D tex;

out vec4 pass_color;

void main() {

	pass_color = texture(tex, pass_tex_pos);

}
