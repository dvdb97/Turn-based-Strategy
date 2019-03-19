#version 460 core

struct Material {
	vec4 color;
	sampler2D texture;
};

//The subroutine that is used to compute the base color.
subroutine vec4 ColorFunction();

in vec4 fColor;
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

//The subroutine that uses the attribute's color value.
subroutine (ColorFunction) vec4 attribColor() {
	return fColor;
}


void main() {
	color = colorFunc();
}
