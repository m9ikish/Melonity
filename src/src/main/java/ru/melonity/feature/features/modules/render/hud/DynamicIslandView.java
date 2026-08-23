package ru.melonity.feature.features.modules.render.hud;

import com.google.common.collect.Maps;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1799;
import net.minecraft.class_2535;
import net.minecraft.class_310;
import net.minecraft.class_337;
import net.minecraft.class_4587;
import net.minecraft.class_634;
import net.minecraft.class_640;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.mixin.BossBarHudAccessor;
import ru.melonity.feature.Feature;
import ru.melonity.feature.impl.FeatureManager;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.View;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.language.LanguageService;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScaleUtils;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000²\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u001a\u0018\u0000 ©\u00012\u00020\u0001:\u0004ª\u0001©\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0017\u0010\u0007\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0007\u0010\bJ\r\u0010\n\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\f\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\f\u0010\bJ\u000f\u0010\r\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\r\u0010\u0003J\u0019\u0010\u000e\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0002¢\u0006\u0004\b\u000e\u0010\bJ\u0019\u0010\u000f\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0002¢\u0006\u0004\b\u000f\u0010\bJ\u0019\u0010\u0013\u001a\u00020\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u0002¢\u0006\u0004\b\u0013\u0010\u0014J\u0019\u0010\u0015\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0002¢\u0006\u0004\b\u0015\u0010\bJ\u0017\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u0010H\u0002¢\u0006\u0004\b\u0017\u0010\u0018J\u001f\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\u001aH\u0002¢\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u001aH\u0002¢\u0006\u0004\b\u001e\u0010\u001fJ\u000f\u0010 \u001a\u00020\u0006H\u0002¢\u0006\u0004\b \u0010\u0003J\u001d\u0010#\u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\u0012¢\u0006\u0004\b#\u0010$J\u0015\u0010'\u001a\u00020\u00062\u0006\u0010&\u001a\u00020%¢\u0006\u0004\b'\u0010(J\u0017\u0010+\u001a\u00020\u00062\u0006\u0010*\u001a\u00020)H\u0002¢\u0006\u0004\b+\u0010,J\u0017\u0010-\u001a\u00020\u00062\u0006\u0010*\u001a\u00020)H\u0002¢\u0006\u0004\b-\u0010,J'\u00101\u001a\u00020\u00122\u0006\u0010.\u001a\u00020\u00162\b\u0010/\u001a\u0004\u0018\u00010\u00162\u0006\u00100\u001a\u00020\u0012¢\u0006\u0004\b1\u00102J?\u0010<\u001a\u00020\u00062\u0006\u00103\u001a\u00020\t2\u0006\u00104\u001a\u00020\t2\u0006\u00106\u001a\u0002052\u0006\u00107\u001a\u0002052\u0006\u00109\u001a\u0002082\u0006\u0010;\u001a\u00020:H\u0017¢\u0006\u0004\b<\u0010=JO\u0010C\u001a\u00020\u00062\u0006\u00103\u001a\u00020\t2\u0006\u00104\u001a\u00020\t2\u0006\u0010>\u001a\u00020\t2\u0006\u0010?\u001a\u00020\t2\u0006\u0010@\u001a\u00020\t2\u0006\u0010B\u001a\u00020A2\u0006\u00109\u001a\u0002082\u0006\u0010;\u001a\u00020:H\u0002¢\u0006\u0004\bC\u0010DJO\u0010H\u001a\u00020\u00062\u0006\u00103\u001a\u00020\t2\u0006\u00104\u001a\u00020\t2\u0006\u0010>\u001a\u00020\t2\u0006\u0010?\u001a\u00020\t2\u0006\u0010E\u001a\u00020\u00162\u0006\u0010G\u001a\u00020F2\u0006\u00109\u001a\u0002082\u0006\u0010;\u001a\u00020:H\u0002¢\u0006\u0004\bH\u0010IJ\u0017\u0010J\u001a\u00020\t2\u0006\u0010G\u001a\u00020FH\u0002¢\u0006\u0004\bJ\u0010KJ\u000f\u0010L\u001a\u00020\u0016H\u0002¢\u0006\u0004\bL\u0010MJ\u0017\u0010N\u001a\u00020\t2\u0006\u0010G\u001a\u00020FH\u0002¢\u0006\u0004\bN\u0010KJ\u000f\u0010O\u001a\u00020\u0016H\u0002¢\u0006\u0004\bO\u0010MJ\u0017\u0010P\u001a\u00020\t2\u0006\u0010G\u001a\u00020FH\u0002¢\u0006\u0004\bP\u0010KJ\u0017\u0010R\u001a\u00020%2\u0006\u0010Q\u001a\u00020%H\u0002¢\u0006\u0004\bR\u0010SJG\u0010T\u001a\u00020\u00062\u0006\u00103\u001a\u00020\t2\u0006\u00104\u001a\u00020\t2\u0006\u0010>\u001a\u00020\t2\u0006\u0010?\u001a\u00020\t2\u0006\u0010B\u001a\u00020A2\u0006\u00109\u001a\u0002082\u0006\u0010;\u001a\u00020:H\u0002¢\u0006\u0004\bT\u0010UJ_\u0010Y\u001a\u00020\u00062\u0006\u0010W\u001a\u00020V2\u0006\u00103\u001a\u00020\t2\u0006\u00104\u001a\u00020\t2\u0006\u0010>\u001a\u00020\t2\u0006\u0010?\u001a\u00020\t2\u0006\u0010X\u001a\u00020\t2\u0006\u0010B\u001a\u00020A2\u0006\u0010G\u001a\u00020F2\u0006\u00109\u001a\u0002082\u0006\u0010;\u001a\u00020:H\u0002¢\u0006\u0004\bY\u0010ZJ\u000f\u0010[\u001a\u00020VH\u0002¢\u0006\u0004\b[\u0010\\J#\u0010_\u001a\u00020\u00122\b\u0010]\u001a\u0004\u0018\u00010V2\b\u0010^\u001a\u0004\u0018\u00010VH\u0002¢\u0006\u0004\b_\u0010`J\u000f\u0010a\u001a\u00020\u0016H\u0002¢\u0006\u0004\ba\u0010MJ\u000f\u0010b\u001a\u00020\u0016H\u0002¢\u0006\u0004\bb\u0010MJ\u0017\u0010d\u001a\u00020\u00162\u0006\u0010c\u001a\u00020%H\u0002¢\u0006\u0004\bd\u0010eJ3\u0010k\u001a\u00020\u00162\u0006\u0010f\u001a\u00020\u00162\u0006\u0010g\u001a\u00020\u00162\u0012\u0010j\u001a\n\u0012\u0006\b\u0001\u0012\u00020i0h\"\u00020iH\u0002¢\u0006\u0004\bk\u0010lJ\u001f\u0010o\u001a\u00020m2\u0006\u0010n\u001a\u00020m2\u0006\u0010X\u001a\u00020\tH\u0002¢\u0006\u0004\bo\u0010pJ\u0017\u0010q\u001a\u00020\t2\u0006\u0010Q\u001a\u00020\tH\u0002¢\u0006\u0004\bq\u0010rJ\u0017\u0010t\u001a\u00020\t2\u0006\u0010s\u001a\u00020\tH\u0002¢\u0006\u0004\bt\u0010rJ7\u0010v\u001a\u00020\u00062\u0006\u00103\u001a\u00020\t2\u0006\u00104\u001a\u00020\t2\u0006\u00106\u001a\u0002052\u0006\u00107\u001a\u0002052\u0006\u0010u\u001a\u00020%H\u0016¢\u0006\u0004\bv\u0010wJ'\u0010f\u001a\u00020\u00122\u0006\u0010x\u001a\u00020%2\u0006\u0010y\u001a\u00020%2\u0006\u0010z\u001a\u00020%H\u0016¢\u0006\u0004\bf\u0010{J\u000f\u0010|\u001a\u00020\u0006H\u0016¢\u0006\u0004\b|\u0010\u0003R\u0014\u0010~\u001a\u00020}8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b~\u0010\u007fR\u001e\u0010\u0081\u0001\u001a\t\u0012\u0004\u0012\u00020)0\u0080\u00018\u0002X\u0082\u0004¢\u0006\b\n\u0006\b\u0081\u0001\u0010\u0082\u0001R$\u0010\u0084\u0001\u001a\u000f\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020\u00120\u0083\u00018\u0002X\u0082\u0004¢\u0006\b\n\u0006\b\u0084\u0001\u0010\u0085\u0001R\u0019\u0010\u0086\u0001\u001a\u00020\u00128\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u0086\u0001\u0010\u0087\u0001R\u0019\u0010\u0088\u0001\u001a\u00020\u00128\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u0088\u0001\u0010\u0087\u0001R\u001b\u0010\u0089\u0001\u001a\u0004\u0018\u00010\u00168\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u0089\u0001\u0010\u008a\u0001R\u001b\u0010\u008b\u0001\u001a\u0004\u0018\u00010\u00168\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u008b\u0001\u0010\u008a\u0001R\u001b\u0010\u008c\u0001\u001a\u0004\u0018\u00010)8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u008c\u0001\u0010\u008d\u0001R\u001b\u0010\u008e\u0001\u001a\u0004\u0018\u00010V8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u008e\u0001\u0010\u008f\u0001R\u001b\u0010\u0090\u0001\u001a\u0004\u0018\u00010V8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u0090\u0001\u0010\u008f\u0001R\u0018\u0010\u0092\u0001\u001a\u00030\u0091\u00018\u0002X\u0082\u0004¢\u0006\b\n\u0006\b\u0092\u0001\u0010\u0093\u0001R\u0018\u0010\u0094\u0001\u001a\u00030\u0091\u00018\u0002X\u0082\u0004¢\u0006\b\n\u0006\b\u0094\u0001\u0010\u0093\u0001R\u0018\u0010\u0095\u0001\u001a\u00030\u0091\u00018\u0002X\u0082\u0004¢\u0006\b\n\u0006\b\u0095\u0001\u0010\u0093\u0001R\u0018\u0010\u0096\u0001\u001a\u00030\u0091\u00018\u0002X\u0082\u0004¢\u0006\b\n\u0006\b\u0096\u0001\u0010\u0093\u0001R\u0019\u0010\u0097\u0001\u001a\u00020\t8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u0097\u0001\u0010\u0098\u0001R\u0019\u0010\u0099\u0001\u001a\u00020\t8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u0099\u0001\u0010\u0098\u0001R\u0019\u0010\u009a\u0001\u001a\u00020\u001a8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u009a\u0001\u0010\u009b\u0001R\u0019\u0010\u009c\u0001\u001a\u00020\u00128\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u009c\u0001\u0010\u0087\u0001R\u0019\u0010\u009d\u0001\u001a\u00020\t8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u009d\u0001\u0010\u0098\u0001R\u0019\u0010\u009e\u0001\u001a\u00020\t8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u009e\u0001\u0010\u0098\u0001R\u0019\u0010\u009f\u0001\u001a\u00020\u001a8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u009f\u0001\u0010\u009b\u0001R\u001e\u0010 \u0001\u001a\t\u0012\u0004\u0012\u0002050\u0080\u00018\u0002X\u0082\u0004¢\u0006\b\n\u0006\b \u0001\u0010\u0082\u0001R\u0019\u0010¡\u0001\u001a\u0002058\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b¡\u0001\u0010¢\u0001R\u0019\u0010£\u0001\u001a\u0002058\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b£\u0001\u0010¢\u0001R\u0019\u0010¤\u0001\u001a\u00020\t8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b¤\u0001\u0010\u0098\u0001R\u0019\u0010¥\u0001\u001a\u00020%8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b¥\u0001\u0010¦\u0001R\u0019\u0010§\u0001\u001a\u00020%8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b§\u0001\u0010¦\u0001R\u0019\u0010¨\u0001\u001a\u00020%8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b¨\u0001\u0010¦\u0001¨\u0006«\u0001"},
   d2 = {"Lru/melonity/feature/features/modules/render/hud/DynamicIslandView;", "Lru/melonity/ui/components/view/View;", "<init>", "()V", "Lnet/minecraft/class_1657;", "player", "", "update", "(Lnet/minecraft/class_1657;)V", "", "estimateWidth", "()F", "handleUpdateTick", "updatePing", "updateBps", "updateArmorAlerts", "Lnet/minecraft/class_1799;", "stack", "", "isLowDurability", "(Lnet/minecraft/class_1799;)Z", "updateHandDurability", "", "formatDurability", "(Lnet/minecraft/class_1799;)Ljava/lang/String;", "deltaTime", "", "now", "updateAnimations", "(FJ)V", "updateNotificationState", "(J)V", "updateSnapshot", "featureName", "enabled", "notifyFeatureToggle", "(Ljava/lang/String;Z)V", "", "slot", "notifyArmorLow", "(I)V", "Lru/melonity/feature/features/modules/render/hud/DynamicIslandView$IslandNotification;", "notification", "enqueueNotification", "(Lru/melonity/feature/features/modules/render/hud/DynamicIslandView$IslandNotification;)V", "replaceNotification", "title", "detail", "highlightDetail", "pushNotification", "(Ljava/lang/String;Ljava/lang/String;Z)Z", "x", "y", "", "mouseX", "mouseY", "Lru/melonity/util/render/RenderSystem;", "renderSystem", "Lnet/minecraft/class_4587;", "poseStack", "insert", "(FFDDLru/melonity/util/render/RenderSystem;Lnet/minecraft/class_4587;)V", "w", "h", "radius", "Lru/melonity/ui/menu/clickui/style/MenuStyle;", "style", "drawStyledBlock", "(FFFFFLru/melonity/ui/menu/clickui/style/MenuStyle;Lru/melonity/util/render/RenderSystem;Lnet/minecraft/class_4587;)V", "text", "Lru/melonity/util/font/GlyphData;", "font", "drawCenteredText", "(FFFFLjava/lang/String;Lru/melonity/util/font/GlyphData;Lru/melonity/util/render/RenderSystem;Lnet/minecraft/class_4587;)V", "calculateSideBlockWidth", "(Lru/melonity/util/font/GlyphData;)F", "getPingText", "()Ljava/lang/String;", "getPingTemplateWidth", "getBpsText", "getBpsTemplateWidth", "value", "getDigitCount", "(I)I", "drawContent", "(FFFFLru/melonity/ui/menu/clickui/style/MenuStyle;Lru/melonity/util/render/RenderSystem;Lnet/minecraft/class_4587;)V", "Lru/melonity/feature/features/modules/render/hud/ContentSnapshot;", "snapshot", "alpha", "drawSnapshotContent", "(Lru/melonity/feature/features/modules/render/hud/ContentSnapshot;FFFFFLru/melonity/ui/menu/clickui/style/MenuStyle;Lru/melonity/util/font/GlyphData;Lru/melonity/util/render/RenderSystem;Lnet/minecraft/class_4587;)V", "buildSnapshot", "()Lru/melonity/feature/features/modules/render/hud/ContentSnapshot;", "oldS", "newS", "hasContentChanged", "(Lru/melonity/feature/features/modules/render/hud/ContentSnapshot;Lru/melonity/feature/features/modules/render/hud/ContentSnapshot;)Z", "idleTitle", "idleDetail", "totalSeconds", "formatPlaytime", "(I)Ljava/lang/String;", "key", "fallback", "", "", "args", "translate", "(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "Ljava/awt/Color;", "color", "withAlpha", "(Ljava/awt/Color;F)Ljava/awt/Color;", "alignToPixel", "(F)F", "t", "easeOutQuart", "mouseButton", "click", "(FFDDI)V", "keyCode", "scanCode", "modifiers", "(III)Z", "release", "Lnet/minecraft/class_310;", "mc", "Lnet/minecraft/class_310;", "Ljava/util/Deque;", "notifications", "Ljava/util/Deque;", "", "armorAlerts", "Ljava/util/Map;", "mainHandLow", "Z", "offHandLow", "mainHandKey", "Ljava/lang/String;", "offHandKey", "activeNotification", "Lru/melonity/feature/features/modules/render/hud/DynamicIslandView$IslandNotification;", "currentSnapshot", "Lru/melonity/feature/features/modules/render/hud/ContentSnapshot;", "previousSnapshot", "Lru/melonity/feature/features/modules/render/hud/SpringValue;", "animatedWidth", "Lru/melonity/feature/features/modules/render/hud/SpringValue;", "animatedHeight", "animatedSideWidth", "animatedBossOffset", "contentReveal", "F", "previousContentFade", "contentTransitionStart", "J", "isContentTransitioning", "breathePhase", "scaleMultiplier", "lastUpdateTime", "bpsSamples", "lastPlayerX", "D", "lastPlayerZ", "currentBps", "currentPing", "I", "lastPingDigits", "lastBpsDigits", "Companion", "IslandNotification", "melonity_client"}
)
@Environment(EnvType.CLIENT)
@SourceDebugExtension({"SMAP\nDynamicIslandView.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DynamicIslandView.kt\nru/melonity/feature/features/modules/render/hud/DynamicIslandView\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,617:1\n1#2:618\n1788#3,4:619\n*S KotlinDebug\n*F\n+ 1 DynamicIslandView.kt\nru/melonity/feature/features/modules/render/hud/DynamicIslandView\n*L\n522#1:619,4\n*E\n"})
public final class DynamicIslandView extends View {
   @NotNull
   public static final DynamicIslandView.Companion Companion = new DynamicIslandView.Companion(null);
   @NotNull
   private final class_310 mc;
   @NotNull
   private final Deque<DynamicIslandView.IslandNotification> notifications;
   @NotNull
   private final Map<Integer, Boolean> armorAlerts;
   private boolean mainHandLow;
   private boolean offHandLow;
   @Nullable
   private String mainHandKey;
   @Nullable
   private String offHandKey;
   @Nullable
   private DynamicIslandView.IslandNotification activeNotification;
   @Nullable
   private ContentSnapshot currentSnapshot;
   @Nullable
   private ContentSnapshot previousSnapshot;
   @NotNull
   private final SpringValue animatedWidth;
   @NotNull
   private final SpringValue animatedHeight;
   @NotNull
   private final SpringValue animatedSideWidth;
   @NotNull
   private final SpringValue animatedBossOffset;
   private float contentReveal;
   private float previousContentFade;
   private long contentTransitionStart;
   private boolean isContentTransitioning;
   private float breathePhase;
   private float scaleMultiplier;
   private long lastUpdateTime;
   @NotNull
   private final Deque<Double> bpsSamples;
   private double lastPlayerX;
   private double lastPlayerZ;
   private float currentBps;
   private int currentPing;
   private int lastPingDigits;
   private int lastBpsDigits;
   private static final long DISPLAY_DURATION_MS = 2500L;
   private static final float PADDING_X = 12.0F;
   private static final float PADDING_Y = 6.0F;
   private static final float BASE_HEIGHT = 18.0F;
   private static final int MAX_QUEUE = 5;
   private static final float SIDE_GAP = 5.0F;
   private static final int BPS_SAMPLE_COUNT = 20;
   private static final float SPRING_TENSION = 320.0F;
   private static final float SPRING_FRICTION = 26.0F;
   private static final long CONTENT_FADE_DURATION_MS = 180L;
   @NotNull
   private static final String KEY_TOGGLE_ENABLED = "dynamic_island.feature_enabled";
   @NotNull
   private static final String KEY_TOGGLE_DISABLED = "dynamic_island.feature_disabled";
   @NotNull
   private static final String KEY_ARMOR_LOW_TITLE = "dynamic_island.armor_low_title";
   @NotNull
   private static final String KEY_IDLE_TITLE = "dynamic_island.idle_title";
   @NotNull
   private static final String KEY_IDLE_DETAIL = "dynamic_island.idle_detail";
   @NotNull
   private static final String KEY_HAND_LOW_TITLE = "dynamic_island.hand_low_title";
   @NotNull
   private static final String PING_TEMPLATE_1 = "0ms";
   @NotNull
   private static final String PING_TEMPLATE_2 = "00ms";
   @NotNull
   private static final String PING_TEMPLATE_3 = "000ms";
   @NotNull
   private static final String PING_TEMPLATE_4 = "0000ms";
   @NotNull
   private static final String BPS_TEMPLATE_1 = "BPS 0.0";
   @NotNull
   private static final String BPS_TEMPLATE_2 = "BPS 00.0";
   @NotNull
   private static final String BPS_TEMPLATE_3 = "BPS 000.0";

   public DynamicIslandView() {
      super(18.0F, 18.0F);
      class_310 var10001 = class_310.method_1551();
      Intrinsics.checkNotNullExpressionValue(var10001, "getInstance(...)");
      this.mc = var10001;
      this.notifications = new ArrayDeque<>();
      HashMap var1 = Maps.newHashMap();
      Intrinsics.checkNotNullExpressionValue(var1, "newHashMap(...)");
      this.armorAlerts = var1;
      this.animatedWidth = new SpringValue(18.0F, 320.0F, 26.0F);
      this.animatedHeight = new SpringValue(18.0F, 320.0F, 26.0F);
      this.animatedSideWidth = new SpringValue(18.0F, 320.0F, 26.0F);
      this.animatedBossOffset = new SpringValue(0.0F, 320.0F, 26.0F);
      this.contentReveal = 1.0F;
      this.scaleMultiplier = 1.0F;
      this.lastUpdateTime = System.currentTimeMillis();
      this.bpsSamples = new ArrayDeque<>(20);
      this.lastPlayerX = Double.NaN;
      this.lastPlayerZ = Double.NaN;
      this.lastPingDigits = 1;
      this.lastBpsDigits = 1;
   }

   public final void update(@Nullable class_1657 player) {
      long local2_0 = System.currentTimeMillis();
      float local4_1 = Math.min((float)(local2_0 - this.lastUpdateTime) / 1000.0F, 0.05F);
      this.lastUpdateTime = local2_0;
      this.updatePing();
      this.updateArmorAlerts(player);
      this.updateHandDurability(player);
      this.updateAnimations(local4_1, local2_0);
      this.updateNotificationState(local2_0);
      this.updateSnapshot();
   }

   public final float estimateWidth() {
      float local1_0 = this.animatedSideWidth.get();
      return local1_0 + 5.0F + this.animatedWidth.get() + 5.0F + local1_0;
   }

   public final void handleUpdateTick(@Nullable class_1657 player) {
      this.updateBps(player);
   }

   private final void updatePing() {
      class_746 local1_0 = this.mc.field_1724;
      if (local1_0 != null && this.mc.method_1562() != null) {
         class_634 var10000 = this.mc.method_1562();
         class_640 local2_1 = var10000 != null ? var10000.method_2871(local1_0.method_5667()) : null;
         this.currentPing = local2_1 != null ? local2_1.method_2959() : 0;
      } else {
         this.currentPing = 0;
      }
   }

   private final void updateBps(class_1657 player) {
      if (player == null) {
         this.currentBps = 0.0F;
         this.lastPlayerX = Double.NaN;
         this.lastPlayerZ = Double.NaN;
         this.bpsSamples.clear();
      } else {
         double local2_0 = player.method_23317();
         double local4_1 = player.method_23321();
         if (!Double.isNaN(this.lastPlayerX) && !Double.isNaN(this.lastPlayerZ)) {
            double local6_2 = local2_0 - this.lastPlayerX;
            double local8_3 = local4_1 - this.lastPlayerZ;
            this.lastPlayerX = local2_0;
            this.lastPlayerZ = local4_1;
            double local10_4 = Math.hypot(local6_2, local8_3) * 20.0;
            if (this.bpsSamples.size() >= 20) {
               this.bpsSamples.removeFirst();
            }

            this.bpsSamples.addLast(local10_4);
            double local12_5 = this.bpsSamples.isEmpty() ? 0.0 : CollectionsKt.sumOfDouble(this.bpsSamples) / (double)this.bpsSamples.size();
            this.currentBps = (float)local12_5;
         } else {
            this.lastPlayerX = local2_0;
            this.lastPlayerZ = local4_1;
         }
      }
   }

   private final void updateArmorAlerts(class_1657 player) {
      if (player != null && player.method_31548() != null) {
         class_1661 local2_6 = player.method_31548();
         int local3_5 = 0;

         for (int var4 = local2_6.field_7548.size(); local3_5 < var4; local3_5++) {
            class_1799 local5_2 = local2_6.method_7372(local3_5);
            boolean local6_3 = this.isLowDurability(local5_2);
            Boolean var10000 = this.armorAlerts.get(local3_5);
            boolean local7_4 = var10000 != null ? var10000 : false;
            if (local6_3 && !local7_4) {
               String local8_0 = local5_2.method_7964().getString();
               String local9_1 = this.translate("dynamic_island.armor_low_title", "Armor critical");
               this.enqueueNotification(new DynamicIslandView.IslandNotification(local9_1, local8_0, true));
            }

            this.armorAlerts.put(local3_5, local6_3);
         }
      } else {
         this.armorAlerts.clear();
      }
   }

   private final boolean isLowDurability(class_1799 stack) {
      if (stack != null && !stack.method_7960() && stack.method_7963()) {
         float local2_0 = (float)(stack.method_7936() - stack.method_7919()) / (float)stack.method_7936();
         return local2_0 <= 0.25F;
      } else {
         return false;
      }
   }

   private final void updateHandDurability(class_1657 player) {
      if (player == null) {
         this.mainHandLow = false;
         this.offHandLow = false;
         this.mainHandKey = null;
         this.offHandKey = null;
      } else {
         class_1799 local2_2 = player.method_6047();
         class_1799 local3_3 = player.method_6079();
         String local4_4 = local2_2.method_7909().method_7876();
         String local5_5 = local3_3.method_7909().method_7876();
         if (!Intrinsics.areEqual(this.mainHandKey, local4_4)) {
            this.mainHandLow = false;
            this.mainHandKey = local4_4;
         }

         if (!Intrinsics.areEqual(this.offHandKey, local5_5)) {
            this.offHandLow = false;
            this.offHandKey = local5_5;
         }

         boolean local6_6 = this.isLowDurability(local2_2);
         boolean local7_7 = this.isLowDurability(local3_3);
         if (local6_6 && !this.mainHandLow) {
            String local8_0 = this.translate("dynamic_island.hand_low_title", "Durability Low");
            Intrinsics.checkNotNull(local2_2);
            this.enqueueNotification(new DynamicIslandView.IslandNotification(local8_0, "Main: " + this.formatDurability(local2_2), true));
         }

         if (local7_7 && !this.offHandLow) {
            String local8_1 = this.translate("dynamic_island.hand_low_title", "Durability Low");
            Intrinsics.checkNotNull(local3_3);
            this.enqueueNotification(new DynamicIslandView.IslandNotification(local8_1, "Off: " + this.formatDurability(local3_3), true));
         }

         this.mainHandLow = local6_6;
         this.offHandLow = local7_7;
      }
   }

   private final String formatDurability(class_1799 stack) {
      if (!stack.method_7963()) {
         String var7 = stack.method_7964().getString();
         Intrinsics.checkNotNullExpressionValue(var7, "getString(...)");
         return var7;
      } else {
         int local2_0 = stack.method_7936();
         int local3_1 = RangesKt.coerceAtLeast(local2_0 - stack.method_7919(), 0);
         float local4_2 = local2_0 > 0 ? (float)local3_1 * 100.0F / (float)local2_0 : 0.0F;
         String var5 = "%.0f";
         Object[] var6 = new Object[]{local4_2};
         String var10000 = String.format(var5, Arrays.copyOf(var6, var6.length));
         Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
         return var10000 + "%";
      }
   }

   private final void updateAnimations(float deltaTime, long now) {
      this.breathePhase += deltaTime;
      float local4_1 = (float)Math.sin((double)(this.breathePhase * 1.5F)) * 0.015F + 1.0F;
      this.scaleMultiplier = local4_1;
      class_337 local5_2 = this.mc.field_1705.method_1740();
      Map local6_3 = (local5_2 instanceof BossBarHudAccessor ? (BossBarHudAccessor)local5_2 : null) != null
         ? (local5_2 instanceof BossBarHudAccessor ? (BossBarHudAccessor)local5_2 : null).getBossBars()
         : null;
      int local7_4 = local6_3 != null && !local6_3.isEmpty() ? 12 + local6_3.size() * 19 : 0;
      float local8_5 = local7_4 > 0 ? (float)local7_4 + 14.0F : 0.0F;
      this.animatedBossOffset.setTarget(local8_5);
      this.animatedBossOffset.update(deltaTime);
      if (this.isContentTransitioning) {
         float local9_0 = (float)(now - this.contentTransitionStart) / 180.0F;
         if (local9_0 >= 1.0F) {
            this.isContentTransitioning = false;
            this.previousContentFade = 0.0F;
            this.contentReveal = 1.0F;
         } else {
            this.previousContentFade = 1.0F - this.easeOutQuart(local9_0);
            this.contentReveal = this.easeOutQuart(Math.max(0.0F, (local9_0 - 0.3F) / 0.7F));
         }
      }

      this.animatedWidth.update(deltaTime);
      this.animatedHeight.update(deltaTime);
      this.animatedSideWidth.update(deltaTime);
   }

   private final void updateNotificationState(long now) {
      if (this.activeNotification != null) {
         DynamicIslandView.IslandNotification var10000 = this.activeNotification;
         Intrinsics.checkNotNull(this.activeNotification);
         if (var10000.isExpired(now)) {
            this.activeNotification = null;
         }
      }

      if (this.activeNotification == null && !this.notifications.isEmpty()) {
         this.activeNotification = this.notifications.removeFirst();
         if (this.activeNotification != null) {
            this.activeNotification.markShown(now);
         }

         this.animatedWidth.impulse(45.0F);
      }

      while (this.notifications.size() > 5) {
         this.notifications.removeFirst();
      }
   }

   private final void updateSnapshot() {
      ContentSnapshot local1_0 = this.buildSnapshot();
      ContentSnapshot local2_1 = this.currentSnapshot;
      if (this.hasContentChanged(local2_1, local1_0)) {
         this.previousSnapshot = local2_1;
         this.currentSnapshot = local1_0;
         this.isContentTransitioning = true;
         this.contentTransitionStart = System.currentTimeMillis();
         this.contentReveal = 0.0F;
         this.previousContentFade = 1.0F;
      } else {
         this.currentSnapshot = local1_0;
      }

      if (this.currentSnapshot != null) {
         ContentSnapshot local3_2 = this.currentSnapshot;
         this.animatedWidth.setTarget(local3_2.width());
         this.animatedHeight.setTarget(local3_2.height());
         this.animatedSideWidth.setTarget(local3_2.height());
      }
   }

   public final void notifyFeatureToggle(@NotNull String featureName, boolean enabled) {
      Intrinsics.checkNotNullParameter(featureName, "featureName");
      String local3_0 = this.translate(enabled ? "dynamic_island.feature_enabled" : "dynamic_island.feature_disabled", enabled ? "Enabled" : "Disabled");
      this.replaceNotification(new DynamicIslandView.IslandNotification(featureName, local3_0, true));
   }

   public final void notifyArmorLow(int slot) {
      String local2_0 = this.translate("dynamic_island.armor_low_title", "Armor Critical");
      this.pushNotification(local2_0, null, false);
   }

   private final void enqueueNotification(DynamicIslandView.IslandNotification notification) {
      if (this.notifications.size() >= 5) {
         this.notifications.removeFirst();
      }

      this.notifications.addLast(notification);
   }

   private final void replaceNotification(DynamicIslandView.IslandNotification notification) {
      Function1<Object, Boolean> var10001 = existing -> replaceNotification$lambda$0(notification, (DynamicIslandView.IslandNotification)existing);
      this.notifications.removeIf(p0 -> replaceNotification$lambda$1(var10001, p0));
      long local2_0 = System.currentTimeMillis();
      if (this.activeNotification != null) {
         DynamicIslandView.IslandNotification var10000 = this.activeNotification;
         Intrinsics.checkNotNull(this.activeNotification);
         if (Intrinsics.areEqual(var10000.getTitle(), notification.getTitle())) {
            this.activeNotification = notification;
            var10000 = this.activeNotification;
            Intrinsics.checkNotNull(this.activeNotification);
            var10000.markShown(local2_0);
            this.updateSnapshot();
            return;
         }
      }

      this.enqueueNotification(notification);
   }

   public final boolean pushNotification(@NotNull String title, @Nullable String detail, boolean highlightDetail) {
      Intrinsics.checkNotNullParameter(title, "title");
      if (title.length() == 0) {
         return false;
      } else {
         this.enqueueNotification(new DynamicIslandView.IslandNotification(title, detail, highlightDetail));
         return true;
      }
   }

   @Override
   public void insert(float x, float y, double mouseX, double mouseY, @NotNull RenderSystem renderSystem, @NotNull class_4587 poseStack) {
      Intrinsics.checkNotNullParameter(renderSystem, "renderSystem");
      Intrinsics.checkNotNullParameter(poseStack, "poseStack");
      MenuStyle style = Melonity.INSTANCE.styleManager.style;
      if (style != null) {
         GlyphData font = Fonts.INTER_REGULAR_12;
         this.animatedSideWidth.setTarget(this.calculateSideBlockWidth(font));
         float sideWidth = this.animatedSideWidth.get();
         float centerWidth = this.animatedWidth.get();
         float blockHeight = this.animatedHeight.get();
         if (!(centerWidth <= 0.0F) && !(blockHeight <= 0.0F)) {
            float totalWidth = sideWidth + 5.0F + centerWidth + 5.0F + sideWidth;
            this.setWidth(totalWidth);
            this.setHeight(blockHeight);
            float top = y + this.animatedBossOffset.get();
            float radius = blockHeight / 2.0F;
            float leftX = this.alignToPixel(x);
            float centerX = this.alignToPixel(leftX + sideWidth + 5.0F);
            float rightX = this.alignToPixel(centerX + centerWidth + 5.0F);
            float pivotX = x + totalWidth / 2.0F;
            float pivotY = top + blockHeight / 2.0F;
            ScaleUtils.rescale(2.0);
            poseStack.method_22903();
            poseStack.method_46416(pivotX, pivotY, 0.0F);
            poseStack.method_22905(this.scaleMultiplier, this.scaleMultiplier, 1.0F);
            poseStack.method_46416(-pivotX, -pivotY, 0.0F);
            this.drawStyledBlock(leftX, top, sideWidth, blockHeight, radius, style, renderSystem, poseStack);
            this.drawStyledBlock(centerX, top, centerWidth, blockHeight, radius, style, renderSystem, poseStack);
            this.drawStyledBlock(rightX, top, sideWidth, blockHeight, radius, style, renderSystem, poseStack);
            this.drawCenteredText(leftX, top, sideWidth, blockHeight, this.getPingText(), font, renderSystem, poseStack);
            this.drawCenteredText(rightX, top, sideWidth, blockHeight, this.getBpsText(), font, renderSystem, poseStack);
            this.drawContent(centerX, top, centerWidth, blockHeight, style, renderSystem, poseStack);
            poseStack.method_22909();
            ScaleUtils.rescaleMC();
         }
      }
   }

   private final void drawStyledBlock(float x, float y, float w, float h, float radius, MenuStyle style, RenderSystem renderSystem, class_4587 poseStack) {
      renderSystem.addRect(x, y + 2.0F, w, h, radius, new Color(0, 0, 0, 60), poseStack);
      renderSystem.addRect(x, y, w, h, radius, style.background(), poseStack);
   }

   private final void drawCenteredText(float x, float y, float w, float h, String text, GlyphData font, RenderSystem renderSystem, class_4587 poseStack) {
      String local9_0 = DynamicIslandMeasure.stabilizeDigitWidth(text);
      float local10_1 = font.getStringWidth(local9_0);
      float local11_2 = x + (w - local10_1) / 2.0F;
      float local12_3 = y + (h - font.getStringHeight("0")) / 2.0F + 3.0F;
      renderSystem.addText(
         font, StringsKt.replace(text, ",", ".", false), this.alignToPixel(local11_2), this.alignToPixel(local12_3), Color.WHITE, poseStack
      );
   }

   private final float calculateSideBlockWidth(GlyphData font) {
      float local2_0 = this.getPingTemplateWidth(font);
      float local3_1 = this.getBpsTemplateWidth(font);
      float local4_2 = Math.max(local2_0, local3_1);
      return Math.max(18.0F, local4_2 + 24.0F);
   }

   private final String getPingText() {
      return this.currentPing + "ms";
   }

   private final float getPingTemplateWidth(GlyphData font) {
      int local2_0 = this.getDigitCount(this.currentPing);
      if (local2_0 != this.lastPingDigits) {
         this.lastPingDigits = local2_0;
      }
      String local3_1 = switch (this.lastPingDigits) {
         case 1 -> "0ms";
         case 2 -> "00ms";
         case 3 -> "000ms";
         default -> "0000ms";
      };
      return font.getStringWidth(local3_1);
   }

   private final String getBpsText() {
      String var1 = "BPS %.1f";
      Object[] var2 = new Object[]{this.currentBps};
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private final float getBpsTemplateWidth(GlyphData font) {
      int local2_0 = this.getDigitCount((int)this.currentBps);
      if (local2_0 != this.lastBpsDigits) {
         this.lastBpsDigits = local2_0;
      }
      String local3_1 = switch (this.lastBpsDigits) {
         case 1 -> "BPS 0.0";
         case 2 -> "BPS 00.0";
         default -> "BPS 000.0";
      };
      return font.getStringWidth(local3_1);
   }

   private final int getDigitCount(int value) {
      return value == 0 ? 1 : (int)Math.log10(Math.abs((double)value)) + 1;
   }

   private final void drawContent(float x, float y, float w, float h, MenuStyle style, RenderSystem renderSystem, class_4587 poseStack) {
      GlyphData var10000 = Fonts.INTER_REGULAR_12;
      Intrinsics.checkNotNullExpressionValue(Fonts.INTER_REGULAR_12, "INTER_REGULAR_12");
      GlyphData local8_4 = var10000;
      if (this.previousSnapshot != null) {
         ContentSnapshot local9_1 = this.previousSnapshot;
         if (this.previousContentFade > 0.01F) {
            float local11_0 = (1.0F - this.previousContentFade) * -6.0F;
            this.drawSnapshotContent(local9_1, x, y + local11_0, w, h, this.previousContentFade, style, local8_4, renderSystem, poseStack);
         }
      }

      if (this.currentSnapshot != null) {
         ContentSnapshot local9_3 = this.currentSnapshot;
         if (this.contentReveal > 0.01F) {
            float local11_2 = (1.0F - this.contentReveal) * 6.0F;
            this.drawSnapshotContent(local9_3, x, y + local11_2, w, h, this.contentReveal, style, local8_4, renderSystem, poseStack);
         }
      }
   }

   private final void drawSnapshotContent(
      ContentSnapshot snapshot,
      float x,
      float y,
      float w,
      float h,
      float alpha,
      MenuStyle style,
      GlyphData font,
      RenderSystem renderSystem,
      class_4587 poseStack
   ) {
      float local11_7 = font.getStringWidth(DynamicIslandMeasure.stabilizeDigitWidth(snapshot.title()));
      String var10000 = snapshot.detail();
      float var27;
      if (var10000 != null) {
         String local16_1 = var10000;
         int local17_0 = 0;
         var27 = font.getStringWidth(DynamicIslandMeasure.stabilizeDigitWidth(local16_1));
      } else {
         var27 = 0.0F;
      }

      float local12_8 = var27;
      float local13_9 = local11_7 + (snapshot.detail() != null ? local12_8 + 5.0F : 0.0F);
      float local14_10 = x + (w - local13_9) / 2.0F;
      float local15_11 = y + (h - font.getStringHeight("A")) / 2.0F;
      float local16_12 = this.alignToPixel(local14_10);
      float local17_13 = this.alignToPixel(local15_11) + 3.0F;
      Color var10001 = Color.WHITE;
      Intrinsics.checkNotNullExpressionValue(Color.WHITE, "WHITE");
      Color local18_14 = this.withAlpha(var10001, alpha);
      renderSystem.addText(font, snapshot.title(), local16_12, local17_13, local18_14, poseStack);
      var10000 = snapshot.detail();
      if (var10000 != null) {
         String local19_6 = var10000;
         float local21_5 = Math.max(0.0F, (alpha - 0.2F) / 0.8F);
         if (local21_5 > 0.01F) {
            Color local22_2 = snapshot.highlightDetail() ? style.ascent() : new Color(200, 200, 200);
            float local23_3 = this.alignToPixel(local14_10 + local11_7 + 5.0F);
            float local24_4 = (1.0F - local21_5) * 8.0F;
            GlyphData var29 = Fonts.INTER_MEDIUM_12;
            float var10003 = local23_3 + local24_4;
            Intrinsics.checkNotNull(local22_2);
            renderSystem.addText(var29, local19_6, var10003, local17_13, this.withAlpha(local22_2, local21_5), poseStack);
         }
      }
   }

   private final ContentSnapshot buildSnapshot() {
      DynamicIslandView.IslandNotification local1_0 = this.activeNotification;
      String local2_1 = null;
      String local3_2 = null;
      boolean local4_3 = false;
      if (local1_0 != null) {
         local2_1 = local1_0.getTitle();
         local3_2 = local1_0.getDetail();
         local4_3 = local1_0.getHighlightDetail();
      } else {
         local2_1 = this.idleTitle();
         local3_2 = this.idleDetail();
         local4_3 = false;
      }

      GlyphData var10000 = Fonts.INTER_REGULAR_12;
      Intrinsics.checkNotNullExpressionValue(Fonts.INTER_REGULAR_12, "INTER_REGULAR_12");
      GlyphData local5_4 = var10000;
      return DynamicIslandMeasure.snapshot(local2_1, local3_2, local4_3, local5_4, 18.0F, 12.0F, 6.0F);
   }

   private final boolean hasContentChanged(ContentSnapshot oldS, ContentSnapshot newS) {
      if (oldS != null && newS != null) {
         if (!Intrinsics.areEqual(oldS.title(), newS.title())) {
            return true;
         } else {
            if (this.activeNotification != null) {
               if (oldS.detail() == null != (newS.detail() == null)) {
                  return true;
               }

               if (oldS.detail() != null && !Intrinsics.areEqual(oldS.detail(), newS.detail())) {
                  return true;
               }
            }

            return oldS.highlightDetail() != newS.highlightDetail();
         }
      } else {
         return true;
      }
   }

   private final String idleTitle() {
      FeatureManager local1_4 = Melonity.INSTANCE.featureManager;
      int local2_5 = local1_4 != null && local1_4.features != null ? local1_4.features.size() : 0;
      long var10000;
      if (local1_4 != null && local1_4.features != null) {
         Iterable local7_3 = local1_4.features;
         int var15;
         if (local7_3 instanceof Collection && ((Collection)local7_3).isEmpty()) {
            var15 = 0;
         } else {
            int local9_2 = 0;

            for (Object local11_1 : local7_3) {
               Feature local12_0 = (Feature)local11_1;
               if (local12_0.isEnabled()) {
                  if (++local9_2 < 0) {
                     CollectionsKt.throwCountOverflow();
                  }
               }
            }

            var15 = local9_2;
         }

         var10000 = (long)var15;
      } else {
         var10000 = 0L;
      }

      long local3_6 = var10000;
      if (local2_5 > 0) {
         int var16 = (int)((float)Math.rint((double)((float)local3_6 * 100.0F / (float)local2_5)));
      } else {
         boolean var17 = false;
      }

      String var20;
      label43: {
         class_634 var18 = this.mc.method_1562();
         if (var18 != null) {
            class_2535 var19 = var18.method_48296();
            if (var19 != null) {
               var20 = var19.method_52909(true);
               break label43;
            }
         }

         var20 = null;
      }

      List var22;
      label38: {
         String local6_8 = var20;
         if (local6_8 != null) {
            CharSequence var21 = local6_8;
            String[] var14 = new String[]{"/"};
            var22 = StringsKt.split(var21, var14, false, 0);
            if (var22 != null) {
               break label38;
            }
         }

         var22 = CollectionsKt.listOf("Unknown");
      }

      List local7_9 = var22;
      return (String)local7_9.get(0);
   }

   private final String idleDetail() {
      class_746 local1_0 = this.mc.field_1724;
      int local2_1 = local1_0 != null ? local1_0.field_6012 / 20 : 0;
      String local3_2 = this.formatPlaytime(local2_1);
      Object[] var4 = new Object[]{local3_2};
      return this.translate("dynamic_island.idle_detail", "- %s", var4);
   }

   private final String formatPlaytime(int totalSeconds) {
      int local2_0 = totalSeconds / 3600;
      int local3_1 = totalSeconds % 3600 / 60;
      int local4_2 = totalSeconds % 60;
      String var10000;
      if (local2_0 > 0) {
         String var5 = "%dh %02dm";
         Object[] var6 = new Object[]{local2_0, local3_1};
         var10000 = String.format(var5, Arrays.copyOf(var6, var6.length));
         Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      } else if (local3_1 > 0) {
         String var7 = "%dm %02ds";
         Object[] var9 = new Object[]{local3_1, local4_2};
         var10000 = String.format(var7, Arrays.copyOf(var9, var9.length));
         Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      } else {
         String var8 = "%ds";
         Object[] var10 = new Object[]{local4_2};
         var10000 = String.format(var8, Arrays.copyOf(var10, var10.length));
         Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      }

      return var10000;
   }

   private final String translate(String key, String fallback, Object... args) {
      LanguageService local4_1 = Melonity.INSTANCE.languageService;
      if (local4_1 != null) {
         String local5_0 = local4_1.get(key, Arrays.copyOf(args, args.length));
         if (local5_0 != null && !Intrinsics.areEqual(local5_0, key)) {
            return local5_0;
         }
      }

      String var10000;
      if (args.length != 0) {
         Object[] var6 = Arrays.copyOf(args, args.length);
         var10000 = String.format(fallback, Arrays.copyOf(var6, var6.length));
         Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      } else {
         var10000 = fallback;
      }

      return var10000;
   }

   private final Color withAlpha(Color color, float alpha) {
      int local3_0 = (int)(Math.max(0.0F, Math.min(1.0F, alpha)) * 255.0F);
      return new Color(color.getRed(), color.getGreen(), color.getBlue(), local3_0);
   }

   private final float alignToPixel(float value) {
      float local2_0 = Math.max(1.0F, ScaleUtils.getCurrentScale());
      return (float)Math.rint((double)(value * local2_0)) / local2_0;
   }

   private final float easeOutQuart(float t) {
      return 1.0F - (1.0F - t) * (1.0F - t) * (1.0F - t) * (1.0F - t);
   }

   @Override
   public void click(float x, float y, double mouseX, double mouseY, int mouseButton) {
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   @Override
   public void release() {
   }

   private static final boolean replaceNotification$lambda$0(DynamicIslandView.IslandNotification $notification, DynamicIslandView.IslandNotification existing) {
      return Intrinsics.areEqual(existing.getTitle(), $notification.getTitle());
   }

   private static final boolean replaceNotification$lambda$1(Function1 $tmp0, Object p0) {
      return (Boolean)$tmp0.invoke(p0);
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u000f\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0014\u0010\u0005\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0005\u0010\u0006R\u0014\u0010\b\u001a\u00020\u00078\u0002X\u0082T¢\u0006\u0006\n\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00078\u0002X\u0082T¢\u0006\u0006\n\u0004\b\n\u0010\tR\u0014\u0010\u000b\u001a\u00020\u00078\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u000b\u0010\tR\u0014\u0010\r\u001a\u00020\f8\u0002X\u0082T¢\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00078\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u000f\u0010\tR\u0014\u0010\u0010\u001a\u00020\f8\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0010\u0010\u000eR\u0014\u0010\u0011\u001a\u00020\u00078\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0011\u0010\tR\u0014\u0010\u0012\u001a\u00020\u00078\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0012\u0010\tR\u0014\u0010\u0013\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0013\u0010\u0006R\u0014\u0010\u0015\u001a\u00020\u00148\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00148\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0017\u0010\u0016R\u0014\u0010\u0018\u001a\u00020\u00148\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0018\u0010\u0016R\u0014\u0010\u0019\u001a\u00020\u00148\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0019\u0010\u0016R\u0014\u0010\u001a\u001a\u00020\u00148\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u001a\u0010\u0016R\u0014\u0010\u001b\u001a\u00020\u00148\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u001b\u0010\u0016R\u0014\u0010\u001c\u001a\u00020\u00148\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u001c\u0010\u0016R\u0014\u0010\u001d\u001a\u00020\u00148\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u001d\u0010\u0016R\u0014\u0010\u001e\u001a\u00020\u00148\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u001e\u0010\u0016R\u0014\u0010\u001f\u001a\u00020\u00148\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u001f\u0010\u0016R\u0014\u0010 \u001a\u00020\u00148\u0002X\u0082T¢\u0006\u0006\n\u0004\b \u0010\u0016R\u0014\u0010!\u001a\u00020\u00148\u0002X\u0082T¢\u0006\u0006\n\u0004\b!\u0010\u0016R\u0014\u0010\"\u001a\u00020\u00148\u0002X\u0082T¢\u0006\u0006\n\u0004\b\"\u0010\u0016¨\u0006#"},
      d2 = {"Lru/melonity/feature/features/modules/render/hud/DynamicIslandView$Companion;", "", "<init>", "()V", "", "DISPLAY_DURATION_MS", "J", "", "PADDING_X", "F", "PADDING_Y", "BASE_HEIGHT", "", "MAX_QUEUE", "I", "SIDE_GAP", "BPS_SAMPLE_COUNT", "SPRING_TENSION", "SPRING_FRICTION", "CONTENT_FADE_DURATION_MS", "", "KEY_TOGGLE_ENABLED", "Ljava/lang/String;", "KEY_TOGGLE_DISABLED", "KEY_ARMOR_LOW_TITLE", "KEY_IDLE_TITLE", "KEY_IDLE_DETAIL", "KEY_HAND_LOW_TITLE", "PING_TEMPLATE_1", "PING_TEMPLATE_2", "PING_TEMPLATE_3", "PING_TEMPLATE_4", "BPS_TEMPLATE_1", "BPS_TEMPLATE_2", "BPS_TEMPLATE_3", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   public static final class Companion {
      private Companion() {
      }

      public Companion(kotlin.jvm.internal.DefaultConstructorMarker marker) {
         this();
      }
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\n\b\u0082\b\u0018\u0000 $2\u00020\u0001:\u0001$B!\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\t¢\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\t¢\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\u0002HÆ\u0003¢\u0006\u0004\b\u0011\u0010\u0012J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u0002HÆ\u0003¢\u0006\u0004\b\u0013\u0010\u0012J\u0010\u0010\u0014\u001a\u00020\u0005HÆ\u0003¢\u0006\u0004\b\u0014\u0010\u0015J0\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00022\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00022\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001¢\u0006\u0004\b\u0016\u0010\u0017J\u001a\u0010\u0019\u001a\u00020\u00052\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0019\u0010\u001aJ\u0010\u0010\u001c\u001a\u00020\u001bHÖ\u0001¢\u0006\u0004\b\u001c\u0010\u001dJ\u0010\u0010\u001e\u001a\u00020\u0002HÖ\u0001¢\u0006\u0004\b\u001e\u0010\u0012R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u001f\u001a\u0004\b \u0010\u0012R\u0019\u0010\u0004\u001a\u0004\u0018\u00010\u00028\u0006¢\u0006\f\n\u0004\b\u0004\u0010\u001f\u001a\u0004\b!\u0010\u0012R\u0017\u0010\u0006\u001a\u00020\u00058\u0006¢\u0006\f\n\u0004\b\u0006\u0010\"\u001a\u0004\b#\u0010\u0015¨\u0006%"},
      d2 = {"Lru/melonity/feature/features/modules/render/hud/DynamicIslandView$IslandNotification;", "", "", "title", "detail", "", "highlightDetail", "<init>", "(Ljava/lang/String;Ljava/lang/String;Z)V", "", "time", "", "markShown", "(J)V", "now", "isExpired", "(J)Z", "component1", "()Ljava/lang/String;", "component2", "component3", "()Z", "copy", "(Ljava/lang/String;Ljava/lang/String;Z)Lru/melonity/feature/features/modules/render/hud/DynamicIslandView$IslandNotification;", "other", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "toString", "Ljava/lang/String;", "getTitle", "getDetail", "Z", "getHighlightDetail", "Companion", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   private static final class IslandNotification {
      @NotNull
      public static final DynamicIslandView.IslandNotification.Companion Companion = new DynamicIslandView.IslandNotification.Companion(null);
      @NotNull
      private final String title;
      @Nullable
      private final String detail;
      private final boolean highlightDetail;
      private static long shownAt = -1L;

      public IslandNotification(@NotNull String title, @Nullable String detail, boolean highlightDetail) {
         super();
         Intrinsics.checkNotNullParameter(title, "title");
         this.title = title;
         this.detail = detail;
         this.highlightDetail = highlightDetail;
      }

      @NotNull
      public final String getTitle() {
         return this.title;
      }

      @Nullable
      public final String getDetail() {
         return this.detail;
      }

      public final boolean getHighlightDetail() {
         return this.highlightDetail;
      }

      public final void markShown(long time) {
         shownAt = time;
      }

      public final boolean isExpired(long now) {
         return shownAt > 0L && now - shownAt >= 2500L;
      }

      @NotNull
      public final String component1() {
         return this.title;
      }

      @Nullable
      public final String component2() {
         return this.detail;
      }

      public final boolean component3() {
         return this.highlightDetail;
      }

      @NotNull
      public final DynamicIslandView.IslandNotification copy(@NotNull String title, @Nullable String detail, boolean highlightDetail) {
         Intrinsics.checkNotNullParameter(title, "title");
         return new DynamicIslandView.IslandNotification(title, detail, highlightDetail);
      }

      @NotNull
      @Override
      public String toString() {
         return "IslandNotification(title=" + this.title + ", detail=" + this.detail + ", highlightDetail=" + this.highlightDetail + ")";
      }

      @Override
      public int hashCode() {
         int local1_0 = this.title.hashCode();
         local1_0 = local1_0 * 31 + (this.detail == null ? 0 : this.detail.hashCode());
         return local1_0 * 31 + Boolean.hashCode(this.highlightDetail);
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof DynamicIslandView.IslandNotification var2)) {
            return false;
         } else if (!Intrinsics.areEqual(this.title, var2.title)) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.detail, var2.detail) ? false : this.highlightDetail == var2.highlightDetail;
         }
      }

      @Metadata(
         mv = {2, 2, 0},
         k = 1,
         xi = 48,
         d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0016\u0010\u0005\u001a\u00020\u00048\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
         d2 = {"Lru/melonity/feature/features/modules/render/hud/DynamicIslandView$IslandNotification$Companion;", "", "<init>", "()V", "", "shownAt", "J", "melonity_client"}
      )
      @Environment(EnvType.CLIENT)
      public static final class Companion {
         private Companion() {
         }

         public Companion(kotlin.jvm.internal.DefaultConstructorMarker marker) {
            this();
         }
      }
   }
}
