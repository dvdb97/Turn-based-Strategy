#version 330 core

layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec4 vColor;
layout(location = 2) in vec2 vTexPos;
layout(location = 3) in vec3 vNormal;


//model, view and projection matrix
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

uniform mat4 mvpMatrix;

//Shadow uniforms:
uniform mat4 lightViewMatrix;
uniform mat4 lightProjectionMatrix;


//Outgoing variables
out VS_OUT {
	vec3 fragCoordModelSpace;
	vec3 fragCoordWorldSpace;
	vec4 fragCoordLightSpace;
	vec4 fragColor;
	vec2 fragTexPos;
	vec3 fragNormalModelSpace;
	vec3 fragNormalWorldSpace;
} vs_out;


void main() {
	//The color of the vertex
	vs_out.fragColor = vColor;

	//The texture coords of the vertex
	vs_out.fragTexPos = vTexPos;
	
	//The normal of the vertex
	vs_out.fragNormalModelSpace = vNormal;

	//The world space normal of the vertex.
	vs_out.fragNormalWorldSpace = mat3(modelMatrix) * vNormal;
	
	//The model-space coordinates of the vertex.
	vs_out.fragCoordModelSpace = vec3(vPosition);

	//The world-space coordinates of the vertex
	vs_out.fragCoordWorldSpace = vec3(modelMatrix * vec4(vPosition, 1.0f));

	//The light-space coordinates of the vertex
	vs_out.fragCoordLightSpace = lightProjectionMatrix * lightViewMatrix * modelMatrix * vec4(vPosition, 1.0f);

	//The projective space coordinates of the vertex
	gl_Position = mvpMatrix * vec4(vPosition, 1.0f);
}
