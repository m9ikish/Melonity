#version 150

uniform sampler2D tex, texToCheck;
uniform vec2 texelSize, direction;
uniform vec2 resolution;
uniform vec4 color;
uniform float exposure, radius;
uniform float weights[256];
uniform float toggleAlpha;

in vec2 texCoord;

out vec4 fragColor;

#define offset direction * texelSize

#define PI 3.14159265359
#define SAMPLES 20.0
#define MAG 0.002

void main() {
    vec2 uv = gl_FragCoord.xy / resolution;
    vec4 texColor = texture(tex, texCoord);
    float innerAlpha = texColor.a * weights[0];

    for (float r = 1.0; r <= radius; r ++) {
        innerAlpha += texture(tex, texCoord + offset * r).a * weights[int(r)];
        innerAlpha += texture(tex, texCoord - offset * r).a * weights[int(r)];
    }

    fragColor = vec4(color.rgb, min(toggleAlpha, innerAlpha));
}
