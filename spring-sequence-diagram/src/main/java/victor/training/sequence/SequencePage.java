package victor.training.sequence;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Map;

/**
 * http://localhost:8080/sequence — put it on the projector: it follows the newest diagram,
 * so every request you send from another tab shows up here a second later.
 * <p>
 * A RouterFunction, not a @RestController: an application that happens to component-scan this
 * package would otherwise register the same mappings twice.
 */
class SequencePage {
  private final DiagramStore store;

  SequencePage(DiagramStore store) {
    this.store = store;
  }

  RouterFunction<ServerResponse> routes() {
    return RouterFunctions.route()
        .GET("/sequence", request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(PAGE))
        .GET("/sequence/diagrams", request -> ServerResponse.ok().body(store.recent().stream()
            .map(d -> Map.of("id", d.id(), "name", d.name(), "at", d.at().toString()))
            .toList()))
        .GET("/sequence/{file}", this::diagram)
        .build();
  }

  private ServerResponse diagram(ServerRequest request) {
    String file = request.pathVariable("file"); // 12.svg or 12.puml
    int dot = file.lastIndexOf('.');
    if (dot < 1 || !file.substring(0, dot).chars().allMatch(Character::isDigit)) {
      return ServerResponse.notFound().build();
    }
    int id = Integer.parseInt(file.substring(0, dot));
    String extension = file.substring(dot + 1);
    return store.find(id)
        .map(d -> switch (extension) {
          case "svg" -> ServerResponse.ok().contentType(MediaType.valueOf("image/svg+xml")).body(d.svg());
          case "puml" -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(d.puml());
          default -> ServerResponse.notFound().build();
        })
        .orElseGet(() -> ServerResponse.notFound().build());
  }

  private static final String PAGE = """
      <!doctype html><html><head><meta charset="utf-8"><title>Sequence diagrams</title>
      <style>
        body{font-family:sans-serif;margin:0;display:flex;height:100vh}
        nav{width:260px;overflow:auto;border-right:1px solid #ddd;padding:8px;font-size:13px;flex-shrink:0}
        nav a{display:block;padding:4px 6px;color:#222;text-decoration:none;border-radius:4px}
        nav a.on{background:#1565C0;color:#fff} nav small{opacity:.6}
        main{flex:1;overflow:auto;padding:12px} main img{max-width:100%}
      </style></head><body>
      <nav><label><input type="checkbox" id="follow" checked> follow newest</label><div id="list"></div></nav>
      <main><p id="empty">No diagram yet: send a request to the app.</p><img id="svg"></main>
      <script>
        const base = location.pathname.replace(/\\/$/, '');
        let shown = null, last = [];
        function show(id){ shown = id; document.getElementById('svg').src = base + '/' + id + '.svg';
          document.getElementById('empty').style.display = 'none'; render(); }
        function render(){ document.getElementById('list').innerHTML = last.map(d =>
          `<a href="#" class="${d.id===shown?'on':''}" onclick="follow.checked=false;show(${d.id});return false">
           ${d.name.replace(/</g,'&lt;')} <small>${d.at}</small></a>`).join(''); }
        async function poll(){
          try { last = await (await fetch(base + '/diagrams')).json(); } catch(e) { return; }
          if (last.length && (shown === null || follow.checked && last[0].id !== shown)) show(last[0].id); else render();
        }
        poll(); setInterval(poll, 1000);
      </script></body></html>
      """;
}
