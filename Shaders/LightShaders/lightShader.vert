#version 330 core


layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec4 vColor;
layout(location = 2) in vec3 vTexPos;
layout(location = 3) in vec3 vNormal;


uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

out vec3 fPosition;
out vec4 color;
out vec3 texturePos;
out vec3 normal;
out mat3 out_viewMatrix;


void main() {

	color = vColor;

	texturePos = vTexPos;
	
	normal = normalize(mat3(modelMatrix) * vNormal);
	
	vec4 pos = projectionMatrix * viewMatrix * modelMatrix * vec4(vPosition, 1.0); 
	
	out_viewMatrix = mat3(viewMatrix);

	gl_Position = pos;

}
