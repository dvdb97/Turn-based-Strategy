#version 330 core

in vec2 texCoords;

uniform sampler2D tex;
uniform vec4 u_Color;

out vec4 fColor;


vec4 fetchTexels() {
	vec4 color = texture(tex, texCoords);

	float alpha = pow(step(0.2, color.a) * color.a, 1f);

	return vec4(u_Color.rgb, alpha);
}


void main() {


	fColor = texture(tex, texCoords);//fetchTexels();
}
