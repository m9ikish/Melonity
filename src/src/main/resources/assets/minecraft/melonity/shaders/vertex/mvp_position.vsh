#version 330 core

layout (location = 0) in vec3 i_pos;

uniform mat4 mvp;

out vec4 resultPosition;

void main() {
    resultPosition = mvp * vec4(i_pos, 1.0);
}
