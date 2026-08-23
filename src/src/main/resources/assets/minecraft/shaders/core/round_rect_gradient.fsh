#version 150

uniform vec2 location;
uniform vec2 rectSize;
uniform vec4 color1, color2, color3, color4;
uniform float radius;

in vec2 texCoord;

out vec4 fragColor;

#define NOISE 0.5 / 255.0

float roundSDF(vec2 p, vec2 b, float r) {
    return length(max(abs(p) - b , 0.0)) - r;
}

vec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){
    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);
    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));

    return color;
}

void main() {
    vec2 st = texCoord;
    vec2 halfSize = rectSize * 0.5;

    float smoothedAlpha = (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (texCoord * rectSize), halfSize - radius - 1., radius))) * color1.a;
    fragColor = vec4(createGradient(st, color1.rgb, color2.rgb, color3.rgb, color4.rgb), smoothedAlpha);
}
