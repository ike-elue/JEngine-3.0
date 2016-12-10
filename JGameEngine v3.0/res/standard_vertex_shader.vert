#version 400 core

layout (location=0) in vec3 position;
layout (location=1) in vec2 textureCoordinates;

out vec2 pass_textureCoordinates;

uniform mat4 transformationMatrix;
// uniform mat4 viewMatrix; add in later
uniform mat4 worldMatrix;

void main(void){
	pass_textureCoordinates = textureCoordinates;
	gl_Position = transformationMatrix * vec4(position, 1.0);	
}