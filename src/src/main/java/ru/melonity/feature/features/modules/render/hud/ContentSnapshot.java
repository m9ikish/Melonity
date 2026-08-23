package ru.melonity.feature.features.modules.render.hud;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007¢\u0006\u0004\b\n\u0010\u000bJ\r\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0003\u0010\fJ\u000f\u0010\u0004\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0004\u0010\fJ\r\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\rJ\r\u0010\b\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\u000eJ\r\u0010\t\u001a\u00020\u0007¢\u0006\u0004\b\t\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u0002HÂ\u0003¢\u0006\u0004\b\u000f\u0010\fJ\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u0002HÂ\u0003¢\u0006\u0004\b\u0010\u0010\fJ\u0010\u0010\u0011\u001a\u00020\u0005HÂ\u0003¢\u0006\u0004\b\u0011\u0010\rJ\u0010\u0010\u0012\u001a\u00020\u0007HÂ\u0003¢\u0006\u0004\b\u0012\u0010\u000eJ\u0010\u0010\u0013\u001a\u00020\u0007HÂ\u0003¢\u0006\u0004\b\u0013\u0010\u000eJD\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00022\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00022\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u0007HÆ\u0001¢\u0006\u0004\b\u0014\u0010\u0015J\u001a\u0010\u0017\u001a\u00020\u00052\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0017\u0010\u0018J\u0010\u0010\u001a\u001a\u00020\u0019HÖ\u0001¢\u0006\u0004\b\u001a\u0010\u001bJ\u0010\u0010\u001c\u001a\u00020\u0002HÖ\u0001¢\u0006\u0004\b\u001c\u0010\fR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010\u001dR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0004\u0010\u001dR\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0006\u0010\u001eR\u0014\u0010\b\u001a\u00020\u00078\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\b\u0010\u001fR\u0014\u0010\t\u001a\u00020\u00078\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\t\u0010\u001f¨\u0006 "},
   d2 = {"Lru/melonity/feature/features/modules/render/hud/ContentSnapshot;", "", "", "title", "detail", "", "highlightDetail", "", "width", "height", "<init>", "(Ljava/lang/String;Ljava/lang/String;ZFF)V", "()Ljava/lang/String;", "()Z", "()F", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/lang/String;Ljava/lang/String;ZFF)Lru/melonity/feature/features/modules/render/hud/ContentSnapshot;", "other", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "toString", "Ljava/lang/String;", "Z", "F", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class ContentSnapshot {
   @NotNull
   private final String title;
   @Nullable
   private final String detail;
   private final boolean highlightDetail;
   private final float width;
   private final float height;

   public ContentSnapshot(@NotNull String title, @Nullable String detail, boolean highlightDetail, float width, float height) {
      Intrinsics.checkNotNullParameter(title, "title");
      this.title = title;
      this.detail = detail;
      this.highlightDetail = highlightDetail;
      this.width = width;
      this.height = height;
   }

   @NotNull
   public final String title() {
      return this.title;
   }

   @Nullable
   public final String detail() {
      return this.detail;
   }

   public final boolean highlightDetail() {
      return this.highlightDetail;
   }

   public final float width() {
      return this.width;
   }

   public final float height() {
      return this.height;
   }

   private final String component1() {
      return this.title;
   }

   private final String component2() {
      return this.detail;
   }

   private final boolean component3() {
      return this.highlightDetail;
   }

   private final float component4() {
      return this.width;
   }

   private final float component5() {
      return this.height;
   }

   @NotNull
   public final ContentSnapshot copy(@NotNull String title, @Nullable String detail, boolean highlightDetail, float width, float height) {
      Intrinsics.checkNotNullParameter(title, "title");
      return new ContentSnapshot(title, detail, highlightDetail, width, height);
   }

   @NotNull
   @Override
   public String toString() {
      return "ContentSnapshot(title="
         + this.title
         + ", detail="
         + this.detail
         + ", highlightDetail="
         + this.highlightDetail
         + ", width="
         + this.width
         + ", height="
         + this.height
         + ")";
   }

   @Override
   public int hashCode() {
      int local1_0 = this.title.hashCode();
      local1_0 = local1_0 * 31 + (this.detail == null ? 0 : this.detail.hashCode());
      local1_0 = local1_0 * 31 + Boolean.hashCode(this.highlightDetail);
      local1_0 = local1_0 * 31 + Float.hashCode(this.width);
      return local1_0 * 31 + Float.hashCode(this.height);
   }

   @Override
   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof ContentSnapshot var2)) {
         return false;
      } else if (!Intrinsics.areEqual(this.title, var2.title)) {
         return false;
      } else if (!Intrinsics.areEqual(this.detail, var2.detail)) {
         return false;
      } else if (this.highlightDetail != var2.highlightDetail) {
         return false;
      } else {
         return Float.compare(this.width, var2.width) != 0 ? false : Float.compare(this.height, var2.height) == 0;
      }
   }
}
