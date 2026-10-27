package es.goya.dwes.codificacion;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CodificacionController {

    private static final String HTML = "text/html;charset=UTF-8";

    @RequestMapping(value = "/recibir", method = { RequestMethod.GET, RequestMethod.POST }, produces = HTML)
    public String recibir(@RequestParam(name = "texto", defaultValue = "") String texto, HttpServletRequest request) {

        String codificado = URLEncoder.encode(texto, StandardCharsets.UTF_8);

        String codigos = texto.codePoints()
                .mapToObj(cp -> String.format("%s=U+%04X", HtmlUtils.htmlEscape(Character.toString(cp)), cp))
                .collect(Collectors.joining("  "));

        return """
            <!DOCTYPE html>
            <html lang="es"><head><meta charset="UTF-8"><title>Recibido</title></head><body>
            <h1>Datos recibidos</h1>
            <ul>
              <li><b>Método:</b> %s</li>
              <li><b>Content-Type de la petición:</b> %s</li>
              <li><b>Charset con el que Spring lee la petición:</b> %s</li>
              <li><b>Query string tal como llegó:</b> <code>%s</code></li>
            </ul>
            <h2>Texto recibido</h2>
            <p><b>Escapado con HtmlUtils (se ve tal cual):</b> %s</p>
            <p><b>Sin escapar (el navegador interpreta las etiquetas):</b> %s</p>
            <p><b>Longitud:</b> %d caracteres</p>
            <p><b>Caracteres:</b> <code>%s</code></p>
            <h2>Enlaces con el texto en la query string</h2>
            <p><b>URLEncoder.encode:</b> <code>%s</code></p>
            <ul>
              <li><a href="/mostrar?texto=%s">/mostrar con el texto CODIFICADO</a></li>
              <li><a href="/mostrar?texto=%s">/mostrar con el texto SIN codificar</a></li>
              <li><a href="/">Volver a los formularios</a></li>
            </ul>
            </body></html>
            """.formatted(
                request.getMethod(),
                request.getContentType(),
                request.getCharacterEncoding(),
                HtmlUtils.htmlEscape(String.valueOf(request.getQueryString())),
                HtmlUtils.htmlEscape(texto),
                texto,
                texto.length(),
                codigos,
                codificado,
                codificado,
                texto);
    }

    @GetMapping(value = "/mostrar", produces = HTML)
    public String mostrar(@RequestParam(name = "texto", defaultValue = "") String texto) {
        return """
            <!DOCTYPE html>
            <html lang="es"><head><meta charset="UTF-8"><title>Mostrar</title></head><body>
            <h1>/mostrar ha recibido</h1>
            <p>%s</p>
            <p>(%d caracteres)</p>
            <p><a href="/">Volver</a></p>
            </body></html>
            """.formatted(HtmlUtils.htmlEscape(texto), texto.length());
    }
}