package ru.melonity.proxy;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public record MelonityProxy(ProxyType type, String raw, String string, int port, long ping) {
}
