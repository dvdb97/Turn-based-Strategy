#version 330 core

in vec2 pass_tex_pos;

uniform sampler2D tex;

out vec4 pass_color;

void main() {

	//TODO: Temporary for testing purposes
	float d = texture(tex, pass_tex_pos).r;

	pass_color = vec4(d, d, d, 1.0);

}
