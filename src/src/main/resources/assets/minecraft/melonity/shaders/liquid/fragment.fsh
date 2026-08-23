#version 150

uniform sampler2D u_BackgroundTexture;
uniform vec2 u_ScreenSize;
uniform vec2 u_Center;
uniform vec2 u_Size;
uniform float u_Radius;
uniform float u_Thickness;
uniform float u_IOR;
uniform float u_Dispersion;
uniform float u_TintStrength;

out vec4 fragColor;

float sdfRect(vec2 center, vec2 size, vec2 p, float r) {
    vec2 p_rel = p - center;
    vec2 q = abs(p_rel) - size;
    return length(max(q, 0.0)) + min(max(q.x, q.y), 0.0) - r;
}

float fresnelSchlick(float cosTheta, float f0) {
    return f0 + (1.0 - f0) * pow(1.0 - cosTheta, 5.0);
}

vec3 getIosGradient(vec2 uv, vec3 normal) {
    vec3 colorTop = vec3(0.2, 0.6, 1.0);
    vec3 colorBot = vec3(0.85, 0.3, 0.9);
    float t = uv.y * 0.8 + normal.y * 0.2;
    t = smoothstep(-0.2, 1.2, t);
    return mix(colorBot, colorTop, t);
}

void main() {
    vec2 fragCoord = gl_FragCoord.xy;
    vec2 uv = fragCoord / u_ScreenSize;

    float sd = sdfRect(u_Center, u_Size, fragCoord, u_Radius);
    float aa = smoothstep(-1.0, 1.0, sd);
    float shadow = smoothstep(0.0, 60.0, sd);

    if (sd > 1.0) {
        fragColor = vec4(0.0);
        return;
    }

    float n_cos = max(u_Thickness + sd, 0.0) / u_Thickness;
    float n_sin = sqrt(max(0.0, 1.0 - n_cos * n_cos));

    vec2 eps = vec2(1.0, 0.0);
    float dx = sdfRect(u_Center, u_Size, fragCoord + eps.xy, u_Radius) - sdfRect(u_Center, u_Size, fragCoord - eps.xy, u_Radius);
    float dy = sdfRect(u_Center, u_Size, fragCoord + eps.yx, u_Radius) - sdfRect(u_Center, u_Size, fragCoord - eps.yx, u_Radius);
    vec3 normal = normalize(vec3(normalize(vec2(dx, dy)) * n_cos, n_sin));
    vec3 viewVec = vec3(0.0, 0.0, -1.0);

    vec3 refR = refract(viewVec, normal, 1.0 / (u_IOR - u_Dispersion));
    vec3 refG = refract(viewVec, normal, 1.0 / u_IOR);
    vec3 refB = refract(viewVec, normal, 1.0 / (u_IOR + u_Dispersion));

    float base_height = 150.0;
    float h = sqrt(max(0.0, u_Thickness * u_Thickness - (u_Thickness + sd) * (u_Thickness + sd)));
    float refDist = h + base_height;

    vec3 sceneColor;
    sceneColor.r = texture(u_BackgroundTexture, (fragCoord + refR.xy * refDist) / u_ScreenSize).r;
    sceneColor.g = texture(u_BackgroundTexture, (fragCoord + refG.xy * refDist) / u_ScreenSize).g;
    sceneColor.b = texture(u_BackgroundTexture, (fragCoord + refB.xy * refDist) / u_ScreenSize).b;

    vec3 gradientColor = getIosGradient(uv + normal.xy * 0.1, normal);
    sceneColor = mix(sceneColor, gradientColor, u_TintStrength * 0.6);
    sceneColor += gradientColor * 0.15;

    float f = fresnelSchlick(max(dot(normal, -viewVec), 0.0), 0.05);
    vec3 lightDir = normalize(vec3(-0.5, 0.8, 1.0));
    vec3 halfVec = normalize(lightDir - viewVec);

    float spec = pow(max(dot(normal, halfVec), 0.0), 90.0);
    float rim = pow(1.0 - max(dot(normal, -viewVec), 0.0), 3.0) * 0.5;

    vec3 reflectColor = vec3(0.9, 0.95, 1.0) * (spec + rim);
    vec3 edgeTint = mix(vec3(1.0), gradientColor, 0.5);
    reflectColor += edgeTint * f * 0.8;

    vec3 finalRGB = mix(sceneColor, reflectColor, f);
    finalRGB += vec3(spec);

    fragColor = vec4(finalRGB, 1.0 - aa);
}