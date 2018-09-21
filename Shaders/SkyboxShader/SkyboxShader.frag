#version 330 core

in vec3 texCoords;

uniform samplerCube skybox;

out vec4 fColor;

void main() {
	fColor = texture(skybox, texCoords);
}
