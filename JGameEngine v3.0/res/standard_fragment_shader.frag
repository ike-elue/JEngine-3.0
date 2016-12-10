#version 400 core

in vec2 pass_textureCoordinates;

out vec4 out_Color;

uniform sampler2D modelTexture;
uniform vec3 color;
uniform float isTexture;

void main(void) {
	vec4 textureColor = texture(modelTexture, pass_textureCoordinates);
	if(isTexture == 0.0) {
		out_Color = vec4(color, 1.0);
	}
	if(isTexture == 1.0) {
		if(textureColor.a == 0.0) {
			discard;
		}
		out_Color = textureColor * vec4(color, 1.0);
	}
}