package cr.ac.ucenfotec.bl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class BoWAnalyzer {

    private final PalabraEmocional emocionalManager;
    private final PalabraTecnica tecnicaManager;

    public BoWAnalyzer(PalabraEmocional emocionalManager, PalabraTecnica tecnicaManager) {
        this.emocionalManager = emocionalManager;
        this.tecnicaManager = tecnicaManager;
    }

    // ESTE ES EL MÉTODO QUE FALTA O ESTÁ MAL ESCRITO EN TU ARCHIVO
    public Map<String, Object> analizarTicket(Ticket ticket) {

        String descripcion = ticket.getDescripcion();

        List<String> tokensLimpios = PreProcesamientoTexto.preprocesar(descripcion);

        Map<String, Integer> vectorTF = generarVectorTF(tokensLimpios);

        Map<String, Object> analisisEmocional = detectarEstadoDeAnimo(vectorTF);

        Map<String, Object> analisisTecnico = clasificarTecnicamente(vectorTF);

        Map<String, Object> resultados = new HashMap<>();
        resultados.put("VectorTF", vectorTF);
        resultados.putAll(analisisEmocional);
        resultados.putAll(analisisTecnico);

        return resultados;
    }

    private Map<String, Integer> generarVectorTF(List<String> tokens) {
        Map<String, Integer> vector = new HashMap<>();
        for (String token : tokens) {
            String cleanToken = token.trim();
            if (!cleanToken.isEmpty()) {
                vector.put(cleanToken, vector.getOrDefault(cleanToken, 0) + 1);
            }
        }
        return vector;
    }

    /* private Map<String, Object> detectarEstadoDeAnimo(Map<String, Integer> vectorTF) {
        Map<String, Integer> scoreEmociones = new HashMap<>();
        String dominante = "NEUTRO";
        int maxScore = 0;

        List<PalabraEmocional> diccionario = emocionalManager.listar();

        for (Map.Entry<String, Integer> entry : vectorTF.entrySet()) {
            String token = entry.getKey();
            int frecuencia = entry.getValue();

            for (PalabraEmocional palabraEmocional : diccionario) {
                if (palabraEmocional.getPalabra().equals(token)) {
                    String emocion = palabraEmocional.getEmocion();
                    int nuevoScore = scoreEmociones.getOrDefault(emocion, 0) + frecuencia;
                    scoreEmociones.put(emocion, nuevoScore);

                    if (nuevoScore > maxScore) {
                        maxScore = nuevoScore;
                        dominante = emocion;
                    }
                }
            }
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("EstadoDeAnimo", dominante);
        resultado.put("ScoreEmocional", maxScore);
        resultado.put("DetallesEmocionales", scoreEmociones);

        return resultado;
    } */

    private Map<String, Object> detectarEstadoDeAnimo(Map<String, Integer> vectorTF) {
        Map<String, Integer> scoreEmociones = new HashMap<>();
        String dominante = "NEUTRO";
        int maxScore = 0;

        // Iteramos sobre el diccionario emocional
        Map<String, List<String>> diccionario = emocionalManager.getDiccionarioEmocional();

        for (Map.Entry<String, List<String>> entry : diccionario.entrySet()) {
            String emocion = entry.getKey();
            List<String> palabras = entry.getValue();

            int score = 0;
            for (String palabra : palabras) {
                score += vectorTF.getOrDefault(palabra, 0);
            }

            if (score > 0) {
                scoreEmociones.put(emocion, score);
            }

            if (score > maxScore) {
                maxScore = score;
                dominante = emocion;
            }
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("EstadoDeAnimo", dominante);
        resultado.put("ScoreEmocional", maxScore);
        resultado.put("DetallesEmocionales", scoreEmociones);

        return resultado;
    }

    private Map<String, Object> clasificarTecnicamente(Map<String, Integer> vectorTF) {
        Map<String, Integer> scoreCategorias = new HashMap<>();
        String dominante = "OTROS";
        int maxScore = 0;

        List<PalabraTecnica> diccionario = tecnicaManager.listar();

        for (Map.Entry<String, Integer> entry : vectorTF.entrySet()) {
            String token = entry.getKey();
            int frecuencia = entry.getValue();

            for (PalabraTecnica palabraTecnica : diccionario) {
                if (palabraTecnica.getPalabra().equals(token)) {
                    String categoria = palabraTecnica.getCategoriaTecnica();
                    int nuevoScore = scoreCategorias.getOrDefault(categoria, 0) + frecuencia;
                    scoreCategorias.put(categoria, nuevoScore);

                    if (nuevoScore > maxScore) {
                        maxScore = nuevoScore;
                        dominante = categoria;
                    }
                }
            }
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("ClasificacionTecnica", dominante);
        resultado.put("ScoreTecnico", maxScore);
        resultado.put("DetallesTecnicos", scoreCategorias);

        return resultado;
    }
}