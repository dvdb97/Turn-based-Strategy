#version 460 core

struct Material {
	vec4 color;
	sampler2D texture;
};

//The subroutine that is used to compute the base color.
subroutine vec4 ColorFunction();

in vec2 fTexCoord;

uniform Material material;
subroutine uniform ColorFunction colorFunc;

out vec4 color;


//The subroutine that uses the material's color
subroutine (ColorFunction) vec4 materialColor() {
	return material.color;
}

//The subroutine that reads the color for this fragment from a texture.
subroutine (ColorFunction) vec4 textureColor() {
	return texture(material.texture, fTexCoord);
}


vec2 toGLCoords(vec2 texCoords) {
	return 2f * texCoords - vec2(1f, 1f);
}

void main() {
	vec2 coord = toGLCoords(fTexCoord);
	vec4 col = colorFunc();
	float a = 0f;

	float l = length(coord) - 1f;

	if (l < 0) {
		a = mix(0, 1, 100 * -l);
	}

	color = vec4(col.rgb, a);
}
