package ru.melonity.fabric.client.model;

import kotlin.Metadata;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u000f\u0010\u0003\u001a\u00020\u0002H&¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005À\u0006\u0003"},
   d2 = {"Lru/melonity/fabric/client/model/IBossBarOffset;", "", "", "getBossBarHeight", "()I", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public interface IBossBarOffset {
   int getBossBarHeight();
}
