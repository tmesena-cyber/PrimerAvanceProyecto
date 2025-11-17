package cr.ac.ucenfotec.bl;

import java.util.*;

public class PalabraEmocional {

        private final Map<String, List<String>> diccionarioEmocional = new LinkedHashMap<>();

    //Constructores

    public PalabraEmocional() {
            diccionarioEmocional.put("Frustración", new ArrayList<>(Arrays.asList(
                    "error","falla","fallando","problema","bloqueado","bloqueada","incorrecto","incorrecta",
                    "bug","mal","frustado","atascado","frustada","atascada","reiniciar","reiniciado","pegado",
                    "pegada","problemas","congelado","crash","imposible"
            )));

            diccionarioEmocional.put("Urgencia", new ArrayList<>(Arrays.asList(
                    "estres","urgente","inmediatamente","inmediato","rapido","rapidamente","critico","critica",
                    "prioridad","apurado","apurada","emergencia","asap","antes"
            )));

            diccionarioEmocional.put("Enojo", new ArrayList<>(Arrays.asList(
                    "innaceptable","pesimo","pesima","horrible","terrible","indignante","molesto","enojado",
                    "furioso,","mal","malo","harto","cansado","insoportable","queja","abuso","estafa","decepcionado",
                    "molesta","enojada","furiosa,","mala","harta","cansada","decepcionada"
            )));

            diccionarioEmocional.put("Satisfacción", new ArrayList<>(Arrays.asList(
                    "gracias","excelente","perfecto","genial","maravilloso","agradecido","impecable"
            )));

            diccionarioEmocional.put("Desesperación", new ArrayList<>(Arrays.asList(
                    "desesperado","urge","aguanto","ayudenme","imploro","desesperadamente"
            )));

            diccionarioEmocional.put("Optimismo", new ArrayList<>(Arrays.asList(
                    "espero","ojala","favor","confio","amable","cordial"
            )));

            diccionarioEmocional.put("Confusión", new ArrayList<>(Arrays.asList(
                    "entiendo","significa","ayuda","instrucciones","guia","explicacion","confuso","confusa",
                    "confusion","aclaracion","aclaren","duda","dudas","perdido","perdida"
            )));

            diccionarioEmocional.put("Preocupación", new ArrayList<>(Arrays.asList(
                    "preocupado","temo","temor","riesgo","inseguro","insegura","miedo","preocupa","alarma",
                    "alarmas","alerta","peligro","peligroso","afecta","perder"
            )));

            diccionarioEmocional.put("Decepción", new ArrayList<>(Arrays.asList(
                    "triste","decepcionado","decepcionada","desanimado","desanimada","lastima","esperaba",
                    "entristece","insatisfecho","insatisfecha","decepcionante"
            )));

            diccionarioEmocional.put("Alivio", new ArrayList<>(Arrays.asList(
                    "mejoro","mejora","parcialmente","avance","aliviado","aliviada"
            )));

            diccionarioEmocional.put("Sorpresa", new ArrayList<>(Arrays.asList(
                    "sorpresa","sorprendente","extraño","raro","nunca","increible","curioso","inesperado"
            )));

        }

    //Getters
    public Map<String, List<String>> getDiccionarioEmocional() {
        return diccionarioEmocional;
    }

    //ToString
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Diccionario Emocional:\n");
        for (String emocion : diccionarioEmocional.keySet()) {
            sb.append("\n").append(emocion).append(": ");
            sb.append(diccionarioEmocional.get(emocion));
        }
        return sb.toString();
    }
}
