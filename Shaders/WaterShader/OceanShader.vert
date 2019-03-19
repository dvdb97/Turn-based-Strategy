#version 330 core

layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec4 vColor;
layout(location = 3) in vec2 waveMovement;


uniform mat4 mvpMatrix;

