#version 150

uniform sampler2D tex;
uniform vec2 texelSize;
uniform vec3 color;
uniform float toggleAlpha;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    vec4 center = texture(tex, texCoord);

    if (center.a != 0) discard;

    float alpha = 0;
    vec4 endCol = vec4(0);
    for (float x = -1; x <= 1; x++) {
        for (float y = -1; y <= 1; y++) {
            vec4 curColor = texture(tex, texCoord + vec2(texelSize.x * x, texelSize.y * y));
            if (curColor.a != 0) {
                alpha += max(0, (2 - sqrt(x * x + y * y)));
            }

            curColor.rgb *= curColor.a;
            endCol += curColor;
        }
    }

    fragColor = vec4(color, min(alpha, toggleAlpha));
}
