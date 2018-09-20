#version 330 core

/*
 * Standard shader that will be used to render a bounding box onto the screen.
 */

layout(location = 0) in vec3 vPosition;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

uniform mat4 mvpMatrix;

void main() {
	gl_Position = mvpMatrix * vec4(vPosition, 1.0f);
}

