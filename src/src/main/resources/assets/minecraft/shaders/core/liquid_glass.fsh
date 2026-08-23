#version 150

uniform sampler2D Sampler0;
uniform vec2 ScreenSize;
uniform vec4 Rect;
uniform float Radius;

uniform float TintStrength;
uniform vec4 TintTop;
uniform vec4 TintBot;

uniform vec3 LightDirection;
uniform float Thinkness;         // Физическая толщина (линза)
uniform float SpecularThickness; // Толщина линии по краю
uniform float IOR;
uniform float Dispersion;

in vec4 vertexColor;
out vec4 fragColor;

#define BASE_HEIGHT 80.0

// SDF
float sdfRoundedRect(vec2 p, vec2 size, float r) {
    vec2 q = abs(p) - size + r;
    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - r;
}

// Gradient
vec3 getIosGradient(vec2 uv) {
    float t = smoothstep(-0.2, 1.3, uv.y);
    return mix(TintBot.rgb, TintTop.rgb, t);
}

vec3 getBackground(vec2 uv) {
    return texture(Sampler0, uv).rgb;
}

void main() {
    float mcFragY = ScreenSize.y - gl_FragCoord.y;
    vec2 fragCoordGeo = vec2(gl_FragCoord.x, mcFragY);

    vec2 size = Rect.zw * 0.5;
    vec2 center = Rect.xy + size;
    vec2 p = fragCoordGeo - center;

    float sd = sdfRoundedRect(p, size, Radius);
    float edgeAlpha = 1.0 - smoothstep(-1.0, 0.5, sd);

    if (edgeAlpha <= 0.0) discard;

    float distFromEdge = -sd;

    float bevelFactor = clamp(distFromEdge / max(Thinkness, 1.0), 0.0, 1.0);
    float bevelCurve = pow(bevelFactor, 0.3);

    // нормали
    vec3 flatNormal = vec3(0.0, 0.0, 1.0);
    vec3 edgeDir = normalize(vec3(p, 0.0));
    vec3 edgeNormal = normalize(vec3(edgeDir.xy, 0.7));
    vec3 normal = normalize(mix(edgeNormal, flatNormal, bevelCurve));

    vec2 screenUV = gl_FragCoord.xy / ScreenSize;
    float glRectTop = ScreenSize.y - Rect.y;
    float glRectBot = ScreenSize.y - (Rect.y + Rect.w);
    vec2 rectUVMin = vec2(Rect.x, glRectBot) / ScreenSize + (1.0/ScreenSize);
    vec2 rectUVMax = vec2(Rect.x + Rect.z, glRectTop) / ScreenSize - (1.0/ScreenSize);

    vec3 viewVec = vec3(0.0, 0.0, -1.0);
    float refDist = BASE_HEIGHT * (1.0 - bevelCurve * 0.9);

    vec3 refR = refract(viewVec, normal, 1.0/(IOR - Dispersion));
    vec3 refG = refract(viewVec, normal, 1.0/IOR);
    vec3 refB = refract(viewVec, normal, 1.0/(IOR + Dispersion));

    vec2 offR = (refR.xy * refDist) / ScreenSize; offR.y = -offR.y;
    vec2 offG = (refG.xy * refDist) / ScreenSize; offG.y = -offG.y;
    vec2 offB = (refB.xy * refDist) / ScreenSize; offB.y = -offB.y;

    vec3 sceneColor;
    sceneColor.r = getBackground(clamp(screenUV + offR, rectUVMin, rectUVMax)).r;
    sceneColor.g = getBackground(clamp(screenUV + offG, rectUVMin, rectUVMax)).g;
    sceneColor.b = getBackground(clamp(screenUV + offB, rectUVMin, rectUVMax)).b;

    vec2 localUV = (p / Rect.zw) + 0.5;
    vec3 gradient = getIosGradient(localUV);
    float mixFactor = TintStrength + (1.0 - bevelCurve) * 0.3;
    vec3 finalColor = mix(sceneColor, gradient, clamp(mixFactor, 0.0, 0.9));

    vec3 lightDir = normalize(vec3(-0.5, 0.5, 1.0));
    if (length(LightDirection) > 0.1) lightDir = normalize(LightDirection);

    // поверхностный блик
    float specSurface = 0.0;

    //
    // SpecularThickness
    float edgeLightAlign = dot(edgeDir.xy, lightDir.xy);

    // маска
    float rimMask = 1.0 - smoothstep(0.0, max(SpecularThickness, 1.0), distFromEdge);

    float rimPrimary = smoothstep(0.0, 1.0, edgeLightAlign) * rimMask;
    rimPrimary = pow(rimPrimary, 4.0) * 0.75;

    float rimSecondary = smoothstep(0.0, 1.0, -edgeLightAlign) * rimMask;
    rimSecondary = pow(rimSecondary, 2.0) * 0.3;

    // складываем только контуры
    vec3 highlight = vec3(1.0) * (specSurface + rimPrimary + rimSecondary);

    finalColor += highlight;

    fragColor = vec4(finalColor, edgeAlpha);
}