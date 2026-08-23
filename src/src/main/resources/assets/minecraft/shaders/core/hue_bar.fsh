#version 150

uniform vec2 location, rectSize;
uniform float radius;

in vec2 texCoord;

out vec4 fragColor;

vec3 hsv2rgb(vec3 c) {
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

vec3 hueToRGB(float hue) {
    return hsv2rgb(vec3(hue, 1.0, 1.0));
}

float roundSDF(vec2 p, vec2 b, float r) {
    return length(max(abs(p) - b, 0.0)) - r;
}

void main() {
    vec2 rectHalf = rectSize * 0.5;

    vec2 localPos = texCoord * rectSize - rectHalf;

    float dist = roundSDF(localPos, rectHalf - radius, radius);
    float alpha = smoothstep(1.0, 0.0, dist);

    float hue = clamp(texCoord.x, 0.0, 1.0);
    vec3 color = hueToRGB(hue);

    fragColor = vec4(color, alpha);
}
