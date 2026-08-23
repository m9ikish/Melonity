#version 330 core

in vec2 uv;

out vec4 color;

uniform sampler2D tex;
uniform sampler2D tex2;
uniform float mixI;

void main() {
    vec4 col = texture(tex, uv);
    vec4 col2 = texture(tex2, uv);

    color = mix(col, col2, mixI);
}
