package ru.melonity.util.altmanager.impl.microsoft;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_320.class_321;
import ru.melonity.util.altmanager.Profile;
import ru.melonity.util.altmanager.ProfileProvider;
import ru.melonity.util.altmanager.ProfileType;
import ru.melonity.util.altmanager.impl.SimpleProfile;
import javax.swing.JOptionPane;

@Environment(EnvType.CLIENT)
public class RedirectMicrosoftProfileProvider implements ProfileProvider {
   public static String CURRENT_AUTH_CODE = null;

   @Override
   public Profile createProfile(String username, String password) {
      try {
         return this.createProfileWithDeviceFlow();
      } catch (Exception var4) {
         var4.printStackTrace();
         return null;
      }
   }

   private Profile createProfileWithDeviceFlow() throws Exception {
      CompletableFuture<DeviceAuth> local1_6 = MSAuth.requestDac();
      DeviceAuth local2_7 = local1_6.get(30L, TimeUnit.SECONDS);
      CURRENT_AUTH_CODE = local2_7.user();

      try {
         Desktop local3_2 = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
         if (local3_2 != null && local3_2.isSupported(Action.BROWSE)) {
            local3_2.browse(local2_7.uri());
         } else {
            Runtime local4_0 = Runtime.getRuntime();
            String local5_1 = System.getProperty("os.name").toLowerCase();
            if (local5_1.contains("win")) {
               local4_0.exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", local2_7.uri().toString()});
            } else if (local5_1.contains("mac")) {
               local4_0.exec(new String[]{"open", local2_7.uri().toString()});
            } else if (local5_1.contains("nix") || local5_1.contains("nux")) {
               local4_0.exec(new String[]{"xdg-open", local2_7.uri().toString()});
            }
         }
      } catch (Exception var11) {
         JOptionPane.showMessageDialog(null, "Не удалось открыть браузер автоматически\nОткройте ссылку вручную:\n" + local2_7.uri(), "AltManager", JOptionPane.INFORMATION_MESSAGE);
      }

      MSTokens local3_8 = null;
      long local4_9 = System.currentTimeMillis();
      long local6_10 = local4_9 + local2_7.expire().toMillis();

      while (System.currentTimeMillis() < local6_10) {
         try {
            local3_8 = MSAuth.dacToMsaMsr(local2_7.device());
            break;
         } catch (RuntimeException var10) {
            Throwable local9_4 = var10.getCause();
            if (local9_4 instanceof MSAuth.DevicePendingException) {
               Thread.sleep(local2_7.interval().toMillis());
            } else {
               if (var10.getMessage() == null || !var10.getMessage().contains("authorization_pending")) {
                  throw var10;
               }

               Thread.sleep(local2_7.interval().toMillis());
            }
         }
      }

      if (local3_8 == null) {
         throw new RuntimeException("Device flow expired");
      } else {
         return this.completeAuthentication(local3_8.access(), local3_8.refresh());
      }
   }

   private Profile completeAuthentication(String msaToken, String msrToken) throws Exception {
      try {
         CompletableFuture<XHashedToken> local3_0 = MSAuth.msaToXbl(msaToken);
         XHashedToken local4_1 = local3_0.get(30L, TimeUnit.SECONDS);
         CompletableFuture<XHashedToken> local5_2 = MSAuth.xblToXsts(local4_1.token(), local4_1.hash());
         XHashedToken local6_3 = local5_2.get(30L, TimeUnit.SECONDS);
         CompletableFuture<String> local7_4 = MSAuth.xstsToMca(local6_3.token(), local6_3.hash());
         String local8_5 = local7_4.get(30L, TimeUnit.SECONDS);
         CompletableFuture<MCProfile> local9_6 = MSAuth.mcaToMcp(local8_5);
         MCProfile local10_7 = local9_6.get(30L, TimeUnit.SECONDS);
         return new SimpleProfile(local10_7.name(), local10_7.uuid(), local8_5, class_321.field_1988, ProfileType.MICROSOFT);
      } catch (Exception var11) {
         String local4_8 = var11.getMessage();
         Throwable local5_9 = var11.getCause();
         if (local5_9 != null && local5_9.getMessage() != null) {
            local4_8 = local5_9.getMessage();
         }

         if (local4_8 != null && local4_8.contains("404") && local4_8.contains("NOT_FOUND")) {
            JOptionPane.showMessageDialog(null, "На аккаунте нету лицензии Minecraft", "AltManager", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException("У этого Microsoft аккаунта нет лицензии Minecraft", var11);
         } else if (local4_8 != null && local4_8.contains("No Xbox linked")) {
            JOptionPane.showMessageDialog(null, "К аккаунту не привязан Xbox", "AltManager", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException("К этому Microsoft аккаунту не привязан Xbox аккаунт", var11);
         } else if (local4_8 != null && local4_8.contains("Xbox not available")) {
            JOptionPane.showMessageDialog(null, "Xbox недоступен в вашем регионе", "AltManager", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException("Xbox недоступен в вашем регионе", var11);
         } else if (local4_8 != null && local4_8.contains("Non-adult")) {
            JOptionPane.showMessageDialog(null, "Требуется взрослый Xbox аккаунт (18+)", "AltManager", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException("Требуется взрослый Xbox аккаунт (18+)", var11);
         } else {
            return null;
         }
      }
   }
}
