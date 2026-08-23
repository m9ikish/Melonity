package ru.melonity.proxy;

import com.google.common.collect.Lists;
import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.Proxy.Type;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.java.IOUtil;

@Environment(EnvType.CLIENT)
public class ProxyManager {
   private final List<MelonityProxy> proxy;
   private final List<MelonityProxy> myProxy;
   private MelonityProxy selectedProxy = null;

   private static List<String> parseProxies(String urlString) throws Throwable {
      URL local1_0 = new URL(urlString);
      HttpURLConnection local2_1 = (HttpURLConnection)local1_0.openConnection();
      BufferedInputStream local3_2 = new BufferedInputStream(local2_1.getInputStream());
      List<String> local4_3 = Arrays.stream(new String(local3_2.readAllBytes(), StandardCharsets.UTF_8).split("\n"))
         .map(str -> str.replace("socks5://", ""))
         .toList();
      local3_2.close();
      local2_1.disconnect();
      List<String> local5_4 = new LinkedList<>(local4_3);
      return Collections.synchronizedList(local5_4);
   }

   private static long ensocketize(String host, int port) {
      Proxy local2_5 = new Proxy(Type.SOCKS, new InetSocketAddress(host, port));

      try {
         long local3_0 = System.currentTimeMillis();
         HttpURLConnection local5_1 = (HttpURLConnection)new URL("http://example.org").openConnection(local2_5);
         local5_1.setConnectTimeout(2000);
         local5_1.setRequestMethod("GET");
         local5_1.setReadTimeout(2000);
         String local6_2 = IOUtil.readString(local5_1.getInputStream());
         long local7_3 = System.currentTimeMillis();
         return local6_2.contains("Example Domain") ? local7_3 - local3_0 : -1L;
      } catch (Exception var9) {
         return -1L;
      }
   }

   public ProxyManager() {
      this.proxy = Lists.newCopyOnWriteArrayList();
      this.myProxy = Lists.newCopyOnWriteArrayList();
   }

   public void addProxy(String string, boolean custom) {
      ProxyType local3_0;
      if (string.startsWith("socks4://")) {
         local3_0 = ProxyType.SOCKS4;
      } else if (string.startsWith("socks5://")) {
         local3_0 = ProxyType.SOCKS5;
      } else {
         if (string.startsWith("http")) {
            throw new RuntimeException("Invalid proxy type, make sure, that Http/s proxys are not allowed");
         }

         string = "socks5://" + string;
         local3_0 = ProxyType.SOCKS5;
      }

      String[] local4_7 = string.substring("socks5://".length()).split(":");
      String local5_8 = local4_7[0];

      int local6_2;
      try {
         local6_2 = Integer.parseInt(local4_7[1]);
      } catch (Exception var11) {
         throw new RuntimeException("Invalid port");
      }

      long local7_4;
      try {
         local7_4 = ensocketize(local5_8, local6_2);
      } catch (Exception var10) {
         local7_4 = -1L;
      }

      if (local7_4 == -1L) {
         throw new RuntimeException("Proxy not working");
      } else {
         MelonityProxy local9_11 = new MelonityProxy(local3_0, string, local5_8, local6_2, local7_4);
         System.out.println("Proxy added");
         if (custom) {
            this.myProxy.add(local9_11);
         } else {
            this.proxy.add(local9_11);
         }
      }
   }

   @Generated
   public List<MelonityProxy> getProxy() {
      return this.proxy;
   }

   @Generated
   public List<MelonityProxy> getMyProxy() {
      return this.myProxy;
   }

   @Generated
   public MelonityProxy getSelectedProxy() {
      return this.selectedProxy;
   }

   @Generated
   public void setSelectedProxy(MelonityProxy selectedProxy) {
      this.selectedProxy = selectedProxy;
   }
}
