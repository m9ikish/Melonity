#version 150

uniform sampler2D tex;
uniform vec2 texelSize, direction;
uniform float radius;
uniform vec3 color;
uniform float toggleAlpha;

in vec2 texCoord;

out vec4 fragColor;

#define offset direction * texelSize

void main() {
    float centerAlpha = texture(tex, texCoord).a;
    float innerAlpha = centerAlpha;
    for (float r = 1.0; r <= radius; r++) {
        float alphaCurrent1 = texture(tex, texCoord + offset * r).a;
        float alphaCurrent2 = texture(tex, texCoord - offset * r).a;

       innerAlpha += alphaCurrent1 + alphaCurrent2;
    }

    vec4 cc = vec4(color, innerAlpha) * step(0.0, -centerAlpha);
    cc.a = min(cc.a, toggleAlpha);
    fragColor = cc;
}
