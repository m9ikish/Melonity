#version 150

uniform vec2 location, size;
uniform vec4 color;
uniform vec4 corners;
uniform float radius;

out vec4 fragColor;

float roundSDF4(vec2 centerPos, vec2 size, vec4 radius) {
    radius.xy = (centerPos.x > 0.0) ? radius.xy : radius.zw;
    radius.x = (centerPos.y > 0.0) ? radius.x : radius.y;
    vec2 pos = abs(centerPos) - size + radius.x;

    return min(max(pos.x, pos.y), 0.0) + length(max(pos, vec2(0.0))) - radius.x;
}

void main() {
    float d = roundSDF4((gl_FragCoord.xy - location) - (size / 2.0), (size / 2.0) - 1.0, corners);
    float alpha = color.a;

    vec4 c = vec4(color.rgb, 1.0);
    if (d >= 0.0)
    c = vec4(0.0, 0.0, 0.0, 0.0);

    float shadowAlpha = smoothstep(0.0, -radius, d);
    c.a = shadowAlpha * alpha;
    fragColor = c;
}
