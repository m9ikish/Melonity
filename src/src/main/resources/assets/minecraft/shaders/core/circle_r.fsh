#version 150

uniform vec2 rectSize;
uniform float radius;
uniform float startAngle;
uniform float endAngle;
uniform float lineWidth;
uniform int filled;
uniform vec4 color;

in vec2 texCoord;
out vec4 fragColor;

float aa = 1.0;

void main() {
    vec2 center = rectSize * 0.5;
    vec2 fragPos = texCoord * rectSize;
    vec2 toFrag = fragPos - center;

    float dist = length(toFrag);
    float angle = degrees(atan(toFrag.y, toFrag.x));
    if (angle < 0.0) angle += 360.0;

    float fadeInAngle = smoothstep(startAngle, startAngle + aa, angle);
    float fadeOutAngle = 1.0 - smoothstep(endAngle, endAngle + aa, angle);
    float angleMask = fadeInAngle * fadeOutAngle;

    float radialMask;
    if (filled == 1) {
        radialMask = 1.0 - smoothstep(radius, radius + aa, dist);
    } else {
        float fadeInRad = smoothstep((radius - lineWidth) - aa, (radius - lineWidth), dist);
        float fadeOutRad = 1.0 - smoothstep(radius, radius + aa, dist);
        radialMask = fadeInRad * fadeOutRad;
    }

    float alpha = angleMask * radialMask * color.a;
    fragColor = vec4(color.rgb, alpha);
}
