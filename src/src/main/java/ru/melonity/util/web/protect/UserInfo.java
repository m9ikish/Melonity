package ru.melonity.util.web.protect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public record UserInfo(int userId, String name, String sessionUUID) {
}
