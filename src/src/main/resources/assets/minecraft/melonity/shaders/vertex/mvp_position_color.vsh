#version 330 core

layout (location = 0) in vec3 i_pos;
layout (location = 1) in vec4 i_color;

uniform mat4 mvp;

out vec4 o_color; // Передаваемый аттрибут цвета во фрагментный шейдер
out vec4 resultPosition;

void main() {
    o_color = i_color;

    //vec4 p = mvp * vec4(i_pos.xy, 0.0, 1.0);
    //resultPosition = vec4(p.xy, 0.2, 1.0);
    resultPosition = mvp * vec4(i_pos, 1.0);
}
