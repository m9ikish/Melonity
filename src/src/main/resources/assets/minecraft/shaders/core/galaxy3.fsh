#version 150

uniform sampler2D tex;
uniform float time;
uniform vec2 resolution;

in vec2 texCoord;

out vec4 fragColor;

#define iTime time
#define iResolution resolution

precision highp float;

float rand(vec2 coords)
{
    return fract(sin(dot(coords, vec2(56.3456,78.3456)) * 5.0) * 10000.0);
}

float noise(vec2 coords)
{
    vec2 i = floor(coords);
    vec2 f = fract(coords);

    float a = rand(i);
    float b = rand(i + vec2(1.0, 0.0));
    float c = rand(i + vec2(0.0, 1.0));
    float d = rand(i + vec2(1.0, 1.0));

    vec2 cubic = f * f * (3.0 - 2.0 * f);

    return mix(a, b, cubic.x) + (c - a) * cubic.y * (1.0 - cubic.x) + (d - b) * cubic.x * cubic.y;
}

float fbm(vec2 coords)
{
    float value = 0.0;
    float scale = 0.5;

    for (int i = 0; i < 10; i++)
    {
       value += noise(coords) * scale;
       coords *= 4.0;
       scale *= 0.24;
    }

    return value;
}


void mainImage(in vec2 fragCoord )
{
    vec2 uv = fragCoord.xy / iResolution.y * 2.0;

    float final = 0.0;

    for (int i =1; i < 10; i++)
    {
        vec2 motion = vec2(fbm(uv + iTime * 0.3 + vec2(i)));

        final += fbm(uv + motion);

    }

    final /= 4.0;
    fragColor = vec4(mix(vec3(-0.3), vec3(0.45, 0.4, 0.6) + vec3(0.6), final), 1);
}

void main(void) {
    if (texture(tex, texCoord).a <= 0.0)
        discard;

    mainImage(gl_FragCoord.xy);
}
