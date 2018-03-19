#version 330 core


layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec4 vColor;
layout(location = 2) in vec3 vTexPos;
layout(location = 3) in vec3 vNormal;


//model, view and projection matrix TODO: Reduce them to one matrix to save computations
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;


//Shadow uniforms:
uniform mat4 lightViewMatrix;


//Outgoing variables
out VS_OUT {
	vec3 fragCoord;
	vec4 fragCoordLightSpace;
	vec4 fragColor;
	vec3 fragTexPos;
	vec3 fragNormal;
	mat3 fragViewMatrix;
} vs_out;


void main() {

	//The color of the vertex
	vs_out.fragColor = vColor;

	//The texture coords of the vertex
	vs_out.fragTexPos = vTexPos;
	
	//The normal of the vertex
	vs_out.fragNormal = mat3(modelMatrix) * vNormal;
	
	//The model-space coordinates of the vertex
	vs_out.fragCoord = vec3(modelMatrix * vec4(vPosition, 1.0f));

	//The light-space coordinates of the vertex
	vs_out.fragCoordLightSpace = lightViewMatrix * vec4(vs_out.fragCoord, 1.0f);

	//The view matrix to rotate the light direction into view space
	vs_out.fragViewMatrix = mat3(viewMatrix);

	//The projective space coordinates of the vertex
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vPosition, 1.0f);

}
