package victor.training.sequence;

import net.sourceforge.plantuml.FileFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Renders each diagram to SVG and keeps it twice: as files in {@code target/sequence/}
 * (with an index.html listing them, newest first) and in memory, for the live page at /sequence.
 */
public class DiagramStore {
  public record Diagram(int id, String name, LocalTime at, String puml, String svg) {
  }

  private static final Log log = LogFactory.getLog(DiagramStore.class);
  private final Deque<Diagram> recent = new ConcurrentLinkedDeque<>();
  private final AtomicInteger ids = new AtomicInteger();
  private final Path folder;
  private final int keepInMemory;

  public DiagramStore(Path folder, int keepInMemory) {
    this.folder = folder.toAbsolutePath().normalize();
    this.keepInMemory = keepInMemory;
  }

  public synchronized Diagram save(String name, String puml) {
    Diagram diagram = new Diagram(ids.incrementAndGet(), name, LocalTime.now().truncatedTo(ChronoUnit.SECONDS), puml, render(puml));
    recent.addFirst(diagram);
    while (recent.size() > keepInMemory) recent.removeLast();
    try {
      Files.createDirectories(folder);
      String file = fileName(name);
      Files.writeString(folder.resolve(file + ".puml"), puml);
      Path svg = folder.resolve(file + ".svg");
      Files.writeString(svg, diagram.svg());
      writeIndex();
      log.info("📈 Sequence diagram '" + name + "': " + svg.toUri());
    } catch (IOException e) {
      log.warn("Could not write the sequence diagram '" + name + "' to " + folder + ": " + e);
    }
    return diagram;
  }

  public List<Diagram> recent() {
    return new ArrayList<>(recent);
  }

  public Optional<Diagram> find(int id) {
    return recent.stream().filter(d -> d.id() == id).findFirst();
  }

  private static String render(String puml) {
    try {
      ByteArrayOutputStream svg = new ByteArrayOutputStream();
      new SourceStringReader(puml).outputImage(svg, new FileFormatOption(FileFormat.SVG));
      return svg.toString(StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new IllegalStateException("PlantUML could not render the diagram", e);
    }
  }

  // "GET /lazy" -> "GET-lazy"; the same request or test overwrites its previous diagram
  static String fileName(String name) {
    String slug = name.replaceAll("[^A-Za-z0-9._-]+", "-").replaceAll("^-+|-+$", "");
    return slug.isEmpty() ? "diagram" : slug;
  }

  private void writeIndex() throws IOException {
    List<Path> svgs;
    try (Stream<Path> files = Files.list(folder)) {
      svgs = files.filter(p -> p.toString().endsWith(".svg"))
          .sorted(Comparator.comparing(DiagramStore::modified).reversed())
          .toList();
    }
    StringBuilder html = new StringBuilder("""
        <!doctype html><html><head><meta charset="utf-8"><title>Sequence diagrams</title>
        <style>body{font-family:sans-serif;margin:16px} h2{font-size:15px;margin:28px 0 6px} img{max-width:100%;border:1px solid #ddd}</style>
        </head><body><h1>Sequence diagrams</h1><p>Newest first. Reload after a run.</p>
        """);
    for (Path svg : svgs) {
      String file = svg.getFileName().toString();
      html.append("<h2>").append(file, 0, file.length() - 4).append("</h2><img src=\"").append(file).append("\">\n");
    }
    Files.writeString(folder.resolve("index.html"), html.append("</body></html>\n"));
  }

  private static long modified(Path path) {
    try {
      return Files.getLastModifiedTime(path).toMillis();
    } catch (IOException e) {
      return 0;
    }
  }
}
