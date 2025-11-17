package cr.ac.ucenfotec.bl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

public class BoWAnalyzer {

    private final PalabraEmocional emocionalManager;
    private final PalabraTecnica tecnicaManager;

    public BoWAnalyzer(PalabraEmocional emocionalManager, PalabraTecnica tecnicaManager) {
        this.emocionalManager = emocionalManager;
        this.tecnicaManager = tecnicaManager;
    }

    public Map<String, Object> analizarTicket(Ticket ticket) {
        // Primera comprobación: si el ticket completo es null
        if (ticket == null) {
            return Collections.emptyMap();
        }

        // Comprobación segura de null para asunto y descripción:
        // Si alguno es null, se usa una cadena vacía ("") en su lugar.
        String asunto = (ticket.getAsunto() != null) ? ticket.getAsunto() : "";
        String descripcion = (ticket.getDescripcion() != null) ? ticket.getDescripcion() : "";

        // Combina el texto de forma segura
        String texto = asunto + " " + descripcion;

        // Si el texto combinado queda vacío (ej. ambos eran null o vacíos), no tiene sentido analizar
        if (texto.trim().isEmpty()) {
            return Collections.emptyMap();
        }

        // 1. Preprocesamiento: Tokenizar, normalizar y eliminar stopwords
        List<String> tokens = PreProcesamientoTexto.preprocesar(texto);

        // 2. Generar Vector TF (Term Frequency)
        Map<String, Integer> vectorTF = generarVectorTF(tokens);

        // 3. Análisis Emocional
        Map<String, Object> resultadosEmocional = detectarEstadoDeAnimo(vectorTF);

        // 4. Clasificación Técnica
        Map<String, Object> resultadosTecnico = clasificarTecnicamente(vectorTF);

        // 5. Consolidar Resultados
        Map<String, Object> resultadosFinales = new HashMap<>();
        resultadosFinales.putAll(resultadosEmocional);
        resultadosFinales.putAll(resultadosTecnico);

        return resultadosFinales;
    }

    private Map<String, Integer> generarVectorTF(List<String> tokens) {
        Map<String, Integer> tfMap = new HashMap<>();
        for (String token : tokens) {
            tfMap.put(token, tfMap.getOrDefault(token, 0) + 1);
        }
        return tfMap;
    }

    private Map<String, Object> detectarEstadoDeAnimo(Map<String, Integer> vectorTF) {
        Map<String, Integer> scoresEmocionales = new HashMap<>();
        Map<String, Integer> palabrasEncontradas = new HashMap<>();
        int scoreTotal = 0;
        String estadoDominante = "NEUTRAL";

        List<PalabraEmocional> diccionarioEmocional = emocionalManager.listar();

        for (PalabraEmocional p : diccionarioEmocional) {
            String palabra = p.getPalabra();
            String emocion = p.getEmocion();

            if (vectorTF.containsKey(palabra)) {
                int conteo = vectorTF.get(palabra);
                scoresEmocionales.put(emocion, scoresEmocionales.getOrDefault(emocion, 0) + conteo);
                palabrasEncontradas.put(palabra, conteo);
                scoreTotal += conteo;
            }
        }

        if (!scoresEmocionales.isEmpty()) {
            estadoDominante = scoresEmocionales.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("NEUTRAL");
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("EstadoDeAnimo", estadoDominante);
        resultado.put("ScoreEmocional", scoreTotal);
        resultado.put("DetallesEmocionales", palabrasEncontradas);
        return resultado;
    }

    private Map<String, Object> clasificarTecnicamente(Map<String, Integer> vectorTF) {
        Map<String, Integer> scoresTecnicos = new HashMap<>();
        Map<String, Integer> palabrasEncontradas = new HashMap<>();
        int scoreTotal = 0;
        String clasificacionDominante = "OTROS";

        List<PalabraTecnica> diccionarioTecnico = tecnicaManager.listar();

        for (PalabraTecnica p : diccionarioTecnico) {
            String palabra = p.getPalabra();
            String categoria = p.getCategoria();

            if (vectorTF.containsKey(palabra)) {
                int conteo = vectorTF.get(palabra);
                scoresTecnicos.put(categoria, scoresTecnicos.getOrDefault(categoria, 0) + conteo);
                palabrasEncontradas.put(palabra, conteo);
                scoreTotal += conteo;
            }
        }

        if (!scoresTecnicos.isEmpty()) {
            clasificacionDominante = scoresTecnicos.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("OTROS");
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("ClasificacionTecnica", clasificacionDominante);
        resultado.put("ScoreTecnico", scoreTotal);
        resultado.put("DetallesTecnicos", palabrasEncontradas);
        return resultado;
    }
}