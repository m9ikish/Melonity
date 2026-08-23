package ru.melonity.util.java;

import lombok.SneakyThrows;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class IOUtil {
   @SneakyThrows
   public static String readString(InputStream inputStream) {
      BufferedReader local1_0 = createReader(inputStream);
      StringBuilder local2_1 = new StringBuilder();

      String local3_2;
      while ((local3_2 = local1_0.readLine()) != null) {
         local2_1.append(local3_2).append("\n");
      }

      return local2_1.toString();
   }

   public static BufferedReader createReader(InputStream inputStream) {
      return new BufferedReader(new InputStreamReader(inputStream));
   }

   @SneakyThrows
   public static void writeString(OutputStream outputStream, String string) {
      createWriter(outputStream).write(string);
   }

   public static BufferedWriter createWriter(OutputStream outputStream) {
      return new BufferedWriter(new OutputStreamWriter(outputStream));
   }

   @Generated
   private IOUtil() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
