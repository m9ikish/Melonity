package ru.melonity.util.render.wavefront;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import lombok.Generated;
import lombok.SneakyThrows;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

@Environment(EnvType.CLIENT)
public class Wavefront {
   private static final int FLOAT_SIZE_IN_BYTES = 4;
   private static final int TRIANGLE_EDGES_COUNT = 3;
   private static final int QUAD_EDGES_COUNT = 4;
   Map<String, Wavefront.PartData> partNameToPartData;
   int vao;
   int maxIndex;

   public Wavefront(String content) {
      this.init(content);
   }

   public void init(String content) {
      List<Wavefront.Part> local2_0 = this.parseParts(Arrays.asList(content.split("\n")));
      this.partNameToPartData = this.allocateBuffersAndGetPartNameToPartDataMappings(local2_0);
      this.maxIndex = this.partNameToPartData.values().stream().mapToInt(Wavefront.PartData::endIndex).max().orElseThrow();
   }

   public void drawAllParts() {
      GL33.glBindVertexArray(this.vao);
      GL33.glDrawElements(4, this.maxIndex, 5125, 0L);
   }

   public void drawPart(String part) {
      Wavefront.PartData local2_0 = this.partNameToPartData.get(part);
      GL33.glBindVertexArray(this.vao);
      GL33.glDrawElements(4, local2_0.indices(), 5125, (long)local2_0.offsetInBytes());
   }

   /**
    * Uploads every part of the freshly parsed model into a single interleaved VBO plus a single
    * element buffer, both owned by {@link #vao}, and describes where each part lives inside the
    * element buffer.
    *
    * <p>The layout is dictated by the two draw calls of this class and by the vertex shader the
    * models are rendered with ({@code assets/minecraft/shaders/core/particles.vsh}):
    * <ul>
    *   <li>{@code drawPart} issues {@code glDrawElements(GL_TRIANGLES, data.indices(),
    *       GL_UNSIGNED_INT, data.offsetInBytes())} - so indices are 32 bit, parts are stored
    *       back to back and {@code offsetInBytes} is the byte offset of the part's first index.</li>
    *   <li>{@code drawAllParts} issues {@code glDrawElements(GL_TRIANGLES, maxIndex, ...)} with
    *       {@code maxIndex = max(PartData::endIndex)} - so {@code endIndex} is the element index
    *       one past the part's last index, and the maximum over all parts equals the total
    *       element count.</li>
    *   <li>{@code layout (location = 0) in vec3 inPosition; layout (location = 1) in vec2 inUv;}
    *       - matching {@link Edge#writeInMemory(ByteBuffer)}, which writes the position (3 floats)
    *       followed by the uv (2 floats).</li>
    * </ul>
    *
    * <p>Identical position/uv pairs are shared between faces; that is what the explicit
    * {@link Edge#hashCode()} / {@link Vec2#hashCode()} / {@link Vec3#hashCode()} overrides are for.
    *
    * <p><b>UNVERIFIED RECONSTRUCTION.</b> This is the only method in the class that was
    * {@code ACC_NATIVE} in o.jar (javap: {@code private native java.util.Map<...>
    * allocateBuffersAndGetPartNameToPartDataMappings(java.util.List<...>)} - no Code attribute),
    * so the obfuscator stripped its body. The GL call sequence below, the {@code Math.max(..., 1)}
    * allocation guards and the try/finally {@code memFree} are all inference, not ground truth.
    *
    * <p>Three artefacts of the real body do survive and prove its shape differed slightly:
    * <ul>
    *   <li>BootstrapMethod 5 + {@code proxy_$25} + the surviving (CFF-stripped)
    *       {@code lambda$allocateBuffersAndGetPartNameToPartDataMappings$0} body: a separate
    *       {@code Consumer<Edge>} did the dedup as
    *       {@code e -> { if (!map.containsKey(e)) map.put(e, map.size()); }}. {@link #appendEdge}
    *       fuses that with appending to the element list.</li>
    *   <li>BootstrapMethod 6 + {@code proxy_$26}: a {@code ToIntFunction<Edge>} over
    *       {@link Edge#inMemorySize()}, i.e. the VBO size was
    *       {@code vertices.stream().mapToInt(Edge::inMemorySize).sum()}.</li>
    *   <li>BootstrapMethod 7 + {@code proxy_$27}: a {@code ToIntFunction<Part>} over
    *       {@link Part#indices()}.</li>
    * </ul>
    * The arithmetic written here is unconditionally equal to those forms - {@code Vec3.inMemorySize()}
    * and {@code Vec2.inMemorySize()} are the constants 12 and 8, and {@code Triangle.indices()} is
    * the constant 3, so none of the quantities are data dependent. Restoring the method-reference
    * shape would therefore buy no robustness for future models; it is left as-is deliberately, which
    * is why {@link Edge#inMemorySize()} and {@link Part#indices()} have no remaining callers.
    */
   private Map<String, Wavefront.PartData> allocateBuffersAndGetPartNameToPartDataMappings(List<Wavefront.Part> parts) {
      Map<Wavefront.Edge, Integer> vertexIndexByEdge = new LinkedHashMap<>();
      List<Wavefront.Edge> vertices = new ArrayList<>();
      List<Integer> elements = new ArrayList<>();
      Map<String, Wavefront.PartData> partNameToPartData = new LinkedHashMap<>();

      for (Wavefront.Part part : parts) {
         int startIndex = elements.size();

         for (Wavefront.Triangle triangle : part.triangles()) {
            this.appendEdge(triangle.first(), vertexIndexByEdge, vertices, elements);
            this.appendEdge(triangle.second(), vertexIndexByEdge, vertices, elements);
            this.appendEdge(triangle.third(), vertexIndexByEdge, vertices, elements);
         }

         int endIndex = elements.size();
         partNameToPartData.put(part.name(), new Wavefront.PartData(endIndex - startIndex, endIndex, startIndex * Integer.BYTES));
      }

      int positionAttributeLocation = 0;
      int uvAttributeLocation = 1;
      int positionSizeInBytes = Wavefront.Vec3.ZERO.inMemorySize();
      int vertexSizeInBytes = positionSizeInBytes + Wavefront.Vec2.ZERO.inMemorySize();
      ByteBuffer vertexData = MemoryUtil.memAlloc(Math.max(vertices.size() * vertexSizeInBytes, 1));
      ByteBuffer elementData = MemoryUtil.memAlloc(Math.max(elements.size() * Integer.BYTES, 1));

      try {
         for (Wavefront.Edge vertex : vertices) {
            vertex.writeInMemory(vertexData);
         }

         for (Integer element : elements) {
            elementData.putInt(element);
         }

         vertexData.flip();
         elementData.flip();
         this.vao = GL33.glGenVertexArrays();
         GL33.glBindVertexArray(this.vao);
         GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, GL33.glGenBuffers());
         GL33.glBufferData(GL33.GL_ARRAY_BUFFER, vertexData, GL33.GL_STATIC_DRAW);
         GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, GL33.glGenBuffers());
         GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, elementData, GL33.GL_STATIC_DRAW);
         GL33.glEnableVertexAttribArray(positionAttributeLocation);
         GL33.glVertexAttribPointer(positionAttributeLocation, 3, GL33.GL_FLOAT, false, vertexSizeInBytes, 0L);
         GL33.glEnableVertexAttribArray(uvAttributeLocation);
         GL33.glVertexAttribPointer(uvAttributeLocation, 2, GL33.GL_FLOAT, false, vertexSizeInBytes, (long)positionSizeInBytes);
         GL33.glBindVertexArray(0);
         GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, 0);
      } finally {
         MemoryUtil.memFree(vertexData);
         MemoryUtil.memFree(elementData);
      }

      return partNameToPartData;
   }

   private void appendEdge(Wavefront.Edge edge, Map<Wavefront.Edge, Integer> vertexIndexByEdge, List<Wavefront.Edge> vertices, List<Integer> elements) {
      Integer existingIndex = vertexIndexByEdge.get(edge);
      if (existingIndex == null) {
         existingIndex = vertices.size();
         vertexIndexByEdge.put(edge, existingIndex);
         vertices.add(edge);
      }

      elements.add(existingIndex);
   }

   private List<Wavefront.Part> parseParts(List<String> lines) {
      final List<Wavefront.Part> local2_0 = new ArrayList<>();

      @Environment(EnvType.CLIENT)
      class PartContext {
         Wavefront.Part part;

         public void newPartAndCompleteIfPresent(String name) {
            this.completeIfPresent();
            this.part = new Wavefront.Part(name);
         }

         public void newTriangle(Wavefront.Triangle triangle) {
            if (this.part == null) {
               throw new IllegalStateException("Part absent");
            } else {
               this.part.newTriangle(triangle);
            }
         }

         public void completeIfPresent() {
            if (this.part != null) {
               local2_0.add(this.part);
            }

            this.part = null;
         }
      }

      PartContext local3_1 = new PartContext();
      Wavefront.IndexPlus1List<Wavefront.Vec3> local4_2 = new Wavefront.IndexPlus1List<>();
      Wavefront.IndexPlus1List<Wavefront.Vec2> local5_3 = new Wavefront.IndexPlus1List<>();
      Wavefront.StringConsumer local6_4 = data -> {
         String[] local5_2 = data.split(" ");
         Wavefront.Edge local6_3 = this.parseEdge(local4_2, local5_3, local5_2[0]);
         Wavefront.Edge local7_4 = this.parseEdge(local4_2, local5_3, local5_2[1]);
         Wavefront.Edge local8_5 = this.parseEdge(local4_2, local5_3, local5_2[2]);
         Wavefront.Triangle local9_6 = new Wavefront.Triangle(local6_3, local7_4, local8_5);
         local3_1.newTriangle(local9_6);
         // Faithful to o.jar: only arity 3 and 4 are triangulated (javap lambda$parseParts$1,
         // 645 iconst_3 -> return, 653 iconst_4 -> one fan triangle, anything else falls through
         // to the return at 990). particle_arrow.obj and particle_spear.obj each contain three
         // octagonal end-cap faces, so 5 triangles per face are dropped (arrow 207/222, spear
         // 339/354) and the caps render as a partially filled tiny disc. Upstream bug in the
         // original client, deliberately reproduced here - a general fan would deviate from o.jar.
         if (local5_2.length != TRIANGLE_EDGES_COUNT) {
            if (local5_2.length == QUAD_EDGES_COUNT) {
               Wavefront.Edge local10_0 = this.parseEdge(local4_2, local5_3, local5_2[3]);
               Wavefront.Triangle local11_1 = new Wavefront.Triangle(local6_3, local8_5, local10_0);
               local3_1.newTriangle(local11_1);
            }
         }
      };
      this.parseLines(
         lines, local3_1::newPartAndCompleteIfPresent, local6_4, new Wavefront.StringVec3Adapter(local4_2::add), new Wavefront.StringVec2Adapter(local5_3::add)
      );
      local3_1.completeIfPresent();
      return local2_0;
   }

   private void parseLines(
      List<String> lines,
      Wavefront.StringConsumer partGate,
      Wavefront.StringConsumer faceGate,
      Wavefront.StringConsumer positionGate,
      Wavefront.StringConsumer uvGate
   ) {
      for (String local7_0 : lines) {
         if (!local7_0.isEmpty() && !local7_0.startsWith("#")) {
            if (local7_0.startsWith("f ")) {
               faceGate.accept(local7_0.substring(2));
            } else if (local7_0.startsWith("v ")) {
               positionGate.accept(local7_0.substring(2));
            } else if (local7_0.startsWith("vt ")) {
               uvGate.accept(local7_0.substring(3));
            } else if (local7_0.startsWith("o ")) {
               partGate.accept(local7_0.substring(2));
            }
         }
      }
   }

   private Wavefront.Edge parseEdge(Wavefront.IndexPlus1List<Wavefront.Vec3> positions, Wavefront.IndexPlus1List<Wavefront.Vec2> uvs, String data) {
      String[] local4_0 = data.split("/");
      int local5_1 = Integer.parseInt(local4_0[0]);
      int local6_2 = Integer.parseInt(local4_0[1]);
      return new Wavefront.Edge(positions.get(local5_1), uvs.get(local6_2));
   }

   // Lombok's @SneakyThrows compiles to exactly the catch(Throwable){ throw; } wrapper the
   // decompiler emitted here, and leaves no `throws` clause on the method -- which is why the
   // callers in Model can invoke this from a this(...) delegation.
   @SneakyThrows
   public static Wavefront fromPath(String path) {
      return new Wavefront(Files.readString(Path.of(path)));
   }

   @SneakyThrows
   public static Wavefront fromInputStream(InputStream inputStream) {
      if (inputStream == null) {
         System.out.println("Wavefront#fromInputStream null");
      }

      byte[] local1_1 = IOUtils.toByteArray(inputStream);

      for (int local2_0 = 0; local2_0 < local1_1.length; local2_0++) {
         local1_1[local2_0] = (byte)(local1_1[local2_0] ^ 171);
      }

      return new Wavefront(IOUtils.toString(new ByteArrayInputStream(local1_1), StandardCharsets.UTF_8));
   }

   @Environment(EnvType.CLIENT)
   private static record Edge(Wavefront.Vec3 position, Wavefront.Vec2 uv) {
      public Edge(Wavefront.Vec3 position) {
         this(position, Wavefront.Vec2.ZERO);
      }

      public void writeInMemory(ByteBuffer buffer) {
         this.position.writeInMemory(buffer);
         this.uv.writeInMemory(buffer);
      }

      public int inMemorySize() {
         return this.position.inMemorySize() + this.uv.inMemorySize();
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.position, this.uv);
      }
   }

   @Environment(EnvType.CLIENT)
   private static class IndexPlus1List<T> {
      final List<T> delegate;

      public IndexPlus1List(int capacity) {
         this.delegate = new ArrayList<>(capacity);
      }

      public IndexPlus1List() {
         this.delegate = new ArrayList<>();
      }

      public T get(int index) {
         return this.delegate.get(index - 1);
      }

      public void add(T element) {
         this.delegate.add(element);
      }
   }

   @Environment(EnvType.CLIENT)
   private static class Part {
      final String name;
      final List<Wavefront.Triangle> triangles = new ArrayList<>();

      public void newTriangle(Wavefront.Triangle triangle) {
         this.triangles.add(triangle);
      }

      public int indices() {
         return this.triangles.stream().mapToInt(Wavefront.Triangle::indices).sum();
      }

      @Generated
      public Part(String name) {
         this.name = name;
      }

      @Generated
      public String name() {
         return this.name;
      }

      @Generated
      public List<Wavefront.Triangle> triangles() {
         return this.triangles;
      }
   }

   @Environment(EnvType.CLIENT)
   private static record PartData(int indices, int endIndex, int offsetInBytes) {
   }

   @Environment(EnvType.CLIENT)
   private interface StringConsumer extends Consumer<String> {
   }

   @Environment(EnvType.CLIENT)
   private static class StringVec2Adapter implements Wavefront.StringConsumer {
      final Consumer<Wavefront.Vec2> consumer;

      public void accept(String s) {
         String[] local2_0 = s.split(" ");
         this.consumer.accept(new Wavefront.Vec2(Float.parseFloat(local2_0[0]), Float.parseFloat(local2_0[1])));
      }

      @Generated
      public StringVec2Adapter(Consumer<Wavefront.Vec2> consumer) {
         this.consumer = consumer;
      }
   }

   @Environment(EnvType.CLIENT)
   private static class StringVec3Adapter implements Wavefront.StringConsumer {
      final Consumer<Wavefront.Vec3> consumer;

      public void accept(String s) {
         String[] local2_0 = s.split(" ");
         this.consumer.accept(new Wavefront.Vec3(Float.parseFloat(local2_0[0]), Float.parseFloat(local2_0[1]), Float.parseFloat(local2_0[2])));
      }

      @Generated
      public StringVec3Adapter(Consumer<Wavefront.Vec3> consumer) {
         this.consumer = consumer;
      }
   }

   @Environment(EnvType.CLIENT)
   private static record Triangle(Wavefront.Edge first, Wavefront.Edge second, Wavefront.Edge third) {
      public int indices() {
         return 3;
      }
   }

   @Environment(EnvType.CLIENT)
   private static record Vec2(float x, float y) {
      public static final Wavefront.Vec2 ZERO = new Wavefront.Vec2(0.0F, 0.0F);

      @Override
      public int hashCode() {
         return Objects.hash(this.x, this.y);
      }

      public int inMemorySize() {
         return 8;
      }

      public void writeInMemory(ByteBuffer buffer) {
         buffer.putFloat(this.x).putFloat(this.y);
      }
   }

   @Environment(EnvType.CLIENT)
   private static record Vec3(float x, float y, float z) {
      public static final Wavefront.Vec3 ZERO = new Wavefront.Vec3(0.0F, 0.0F, 0.0F);

      @Override
      public int hashCode() {
         return Objects.hash(this.x, this.y, this.z);
      }

      public int inMemorySize() {
         return 12;
      }

      public void writeInMemory(ByteBuffer buffer) {
         buffer.putFloat(this.x).putFloat(this.y).putFloat(this.z);
      }
   }
}
