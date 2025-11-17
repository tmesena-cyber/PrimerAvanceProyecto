package cr.ac.ucenfotec.bl;

import java.util.*;

public class BoWAnalyzer {

    private PalabraEmocional dicEmocional;
    private PalabraTecnica dicTecnica;

    public BoWAnalyzer(PalabraEmocional emo, PalabraTecnica tec) {
        this.dicEmocional = emo;
        this.dicTecnica = tec;
    }

    public Map<String, Object> analizarTicket(Ticket ticket) {

        Map<String, Object> resultado = new HashMap<>();

        if (ticket == null || ticket.getDescripcion() == null) {
            resultado.put("ClasificacionTecnica", "N/A");
            resultado.put("ScoreTecnico", 0);
            resultado.put("DetallesTecnicos", Collections.emptyMap());

            resultado.put("EstadoDeAnimo", "N/A");
            resultado.put("ScoreEmocional", 0);
            resultado.put("DetallesEmocionales", Collections.emptyMap());

            return resultado;
        }

        // =======================================================
        // PREPROCESAMIENTO
        // =======================================================
        String texto = ticket.getDescripcion().toLowerCase();
        texto = texto.replaceAll("[^a-záéíóúñ0-9 ]", "");
        String[] tokens = texto.split("\\s+");

        Map<String, Integer> conteoTecnico = new HashMap<>();
        Map<String, Integer> conteoEmocional = new HashMap<>();

        int scoreTecnico = 0;
        int scoreEmocional = 0;

        // =======================================================
        // ANÁLISIS DE PALABRAS
        // =======================================================
        for (String palabra : tokens) {

            // ------- TÉCNICAS -------
            PalabraTecnica encontradaTec = dicTecnica.buscarPorPalabra(palabra);
            if (encontradaTec != null) {
                conteoTecnico.put(
                        palabra,
                        conteoTecnico.getOrDefault(palabra, 0) + 1
                );
                scoreTecnico++;
            }

            // ------- EMOCIONALES (ADAPTADO A TU PALABRAEMOCIONAL) -------
            String emocionEncontrada = buscarEmocion(palabra);
            if (emocionEncontrada != null) {
                conteoEmocional.put(
                        emocionEncontrada,
                        conteoEmocional.getOrDefault(emocionEncontrada, 0) + 1
                );
                scoreEmocional++;
            }
        }

        // =======================================================
        // CLASIFICAR TÉCNICO
        // =======================================================
        String clasificacionTecnica = (scoreTecnico > 0)
                ? "Problema Técnico"
                : "Sin coincidencias técnicas";

        // =======================================================
        // CLASIFICAR EMOCIONAL
        // =======================================================
        String estadoEmocional = (scoreEmocional > 0)
                ? "Emocional Detectado"
                : "Neutral";

        // =======================================================
        // RESULTADO FINAL
        // =======================================================
        resultado.put("ClasificacionTecnica", clasificacionTecnica);
        resultado.put("ScoreTecnico", scoreTecnico);
        resultado.put("DetallesTecnicos", conteoTecnico);

        resultado.put("EstadoDeAnimo", estadoEmocional);
        resultado.put("ScoreEmocional", scoreEmocional);
        resultado.put("DetallesEmocionales", conteoEmocional);

        return resultado;
    }

    // =======================================================
    //  MÉTODO PARA BUSCAR EMOCIONES EN TU MAP
    // =======================================================
    private String buscarEmocion(String palabra) {

        Map<String, List<String>> mapa = dicEmocional.getDiccionarioEmocional();

        for (String emocion : mapa.keySet()) {
            List<String> listaPalabras = mapa.get(emocion);

            for (String p : listaPalabras) {
                if (p.equalsIgnoreCase(palabra)) {
                    return emocion;
                }
            }
        }

        return null;
    }
}
