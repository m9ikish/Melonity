#version 150

in vec2 TexCoord;

uniform sampler2D Sampler0;
uniform vec2 rectSize;
uniform vec2 location;
uniform float radius;
uniform float thickness;
uniform vec4 fillColor;
uniform vec4 borderColor;
uniform float borderThickness;
uniform vec2 screenSize;

out vec4 OutColor;

float roundedRectSDF(vec2 p, vec2 size, float r) {
    vec2 q = abs(p) - size + r;
    return length(max(q, 0.0)) + min(max(q.x, q.y), 0.0) - r;
}

vec4 sampleScreen(vec2 pos) {
    vec2 uv = pos / screenSize;
    return texture(Sampler0, uv);
}

void main() {
    vec2 fragPos = vec2(location.x + TexCoord.x * rectSize.x,
                         (screenSize.y - location.y) - TexCoord.y * rectSize.y);

    vec2 halfSize = rectSize * 0.5;
    vec2 center = vec2(location.x + halfSize.x, (screenSize.y - location.y) - halfSize.y);

    float dist = roundedRectSDF(fragPos - center, halfSize - vec2(radius), radius);
    float edgeWidth = fwidth(dist);

    float fillMask = smoothstep(0.0, edgeWidth, -dist);
    float borderMask = smoothstep(-borderThickness - edgeWidth, -(borderThickness - edgeWidth), dist);

    vec4 color = fillColor;
    color = mix(color, borderColor, borderMask);

    OutColor = color * fillMask;
}

