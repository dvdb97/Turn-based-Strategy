#version 460 core

layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec4 vColor;
layout(location = 2) in vec2 vTexCoord;
layout(location = 4) in mat4 mMatrix;

uniform mat4 globalMMatrix;
uniform mat4 vpMatrix;

out vec2 fTexCoord;
out vec4 fColor;

void main() {
	fColor = vColor;
	fTexCoord = vTexCoord;

	gl_Position = vpMatrix * globalMMatrix * mMatrix * vec4(vPosition, 1.0);
}
