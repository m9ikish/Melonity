#version 150

in vec2 TexCoord;

uniform sampler2D Sampler0;
uniform vec2 rectMin;
uniform vec2 rectSize;
uniform float cornerRadius;
uniform float smoothness;
uniform float blurRadius;
uniform vec2 textureSize;
uniform vec4 color;

out vec4 OutColor;

float sdfRect(vec2 center, vec2 halfSize, vec2 p, float r) {
    vec2 q = abs(p - center) - halfSize + r;
    vec2 clampQ = max(q, 0.0);
    return min(max(q.x, q.y), 0.0) + length(clampQ) - r;
}

vec3 sampleRadialBlur(vec2 uv, vec2 texelSize, float radius) {
    const float TWO_PI = 6.28318530718;
    const float STEP = TWO_PI / 16.0;

    vec3 accum = texture(Sampler0, clamp(uv, vec2(0.0), vec2(1.0))).rgb;
    float samples = 1.0;

    for (float angle = 0.0; angle < TWO_PI; angle += STEP) {
        vec2 dir = vec2(cos(angle), sin(angle));
        for (float strength = 0.25; strength <= 1.0; strength += 0.25) {
            vec2 offset = dir * texelSize * radius * strength;
            accum += texture(Sampler0, clamp(uv + offset, vec2(0.0), vec2(1.0))).rgb;
            samples += 1.0;
        }
    }

    return accum / samples;
}

void main() {
    vec2 frag = gl_FragCoord.xy;
    vec2 local = frag - rectMin;
    vec2 halfSize = rectSize * 0.5;
    vec2 center = rectMin + halfSize;

    float sd = sdfRect(center, halfSize, frag, cornerRadius);
    float alpha = 1.0 - smoothstep(0.0, smoothness, sd);
    if (alpha <= 0.0) {
        discard;
    }

    vec2 uv = frag / textureSize;
    vec2 texel = 1.0 / textureSize;

    float radius = max(blurRadius, 0.5);
    vec3 blurred = sampleRadialBlur(uv, texel, radius);
    vec3 base = texture(Sampler0, clamp(uv, vec2(0.0), vec2(1.0))).rgb;
    vec3 finalRgb = mix(base, blurred, 0.85) * color.rgb;

    OutColor = vec4(finalRgb, alpha * color.a);
}

