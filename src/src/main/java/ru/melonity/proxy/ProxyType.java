package ru.melonity.proxy;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public enum ProxyType {
   SOCKS5,
   SOCKS4;
}
