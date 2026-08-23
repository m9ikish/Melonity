#version 330 core

layout (location = 0) in vec3 inPosition;
layout (location = 1) in vec2 inUv;

uniform mat4 modelView;

out vec2 uv;

void main() {
    gl_Position = modelView * vec4(inPosition, 1.0);
    uv = inUv;
}
