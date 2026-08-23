#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    vec4 col = texture(DiffuseSampler, texCoord);
    col *= col;

    float luminosity = 0.299 * col.r + 0.587 * col.g + 0.114 * col.b;
    vec4 RGB = vec4(luminosity);

    vec4 sRGB = sqrt(RGB);
    fragColor = sRGB;
}
