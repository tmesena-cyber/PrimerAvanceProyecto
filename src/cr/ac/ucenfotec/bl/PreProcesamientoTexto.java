package cr.ac.ucenfotec.bl;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

public class PreProcesamientoTexto {

    private static final List<String> STOPWORDS = Arrays.asList(
            "de", "la", "el", "es", "un", "una", "por", "para", "con", "a", "en",
            "y", "o", "no", "si", "del", "al", "los", "las", "me", "se", "pero",
            "mi", "que", "ha", "he", "han", "tenemos", "tengo", "puedo"
    );

    public static List<String> preprocesar(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String textoNormalizado = normalizar(texto);
        List<String> tokens = tokenizar(textoNormalizado);
        return eliminarStopwords(tokens);
    }

    private static String normalizar(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        texto = texto.toLowerCase();

        texto = texto.replaceAll("[^a-z0-9ñ ]", " ");

        return texto;
    }

    private static List<String> tokenizar(String texto) {
        return Arrays.stream(texto.split("\\s+"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private static List<String> eliminarStopwords(List<String> tokens) {
        return tokens.stream()
                .filter(token -> !STOPWORDS.contains(token))
                .collect(Collectors.toList());
    }
}