#version 330 core


//Attributes 
layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec4 vColor;
layout(location = 2) in vec3 vNormal;


//Uniforms
uniform mat4 mvpMatrix;
uniform mat4 normalMatrix;


//Output
out vec3 normal;
out vec3 color;

void main() {
	
	normal = normalMatrix * normal;
	
	color = vColor;
	
	gl_Position = mvpMatrix * vec4(vPosition, 1.0);

}