package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.PalabraEmocional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Arrays;

public class PalabraEmocionalDAO_InMemoria implements IGenericDAO<PalabraEmocional> {

    // Lista donde se almacenarán los objetos PalabraEmocional
    private final ArrayList<PalabraEmocional> palabras = new ArrayList<>();

    // Diccionario temporal para la inicialización (tomado del código de tus compañeras)
    private final Map<String, List<String>> diccionarioInicial = new LinkedHashMap<>();

    // Constructor del DAO: Aquí se inicializa el diccionario hardcodeado.
    public PalabraEmocionalDAO_InMemoria() {
        inicializarDiccionarioHardcodeado();
        cargarPalabrasAlListado();
    }

    private void inicializarDiccionarioHardcodeado() {
        diccionarioInicial.put("Frustración", new ArrayList<>(Arrays.asList(
                "error","falla","fallando","problema","bloqueado","bloqueada","incorrecto","incorrecta",
                "bug","mal","frustado","atascado","frustada","atascada","reiniciar","reiniciado","pegado",
                "pegada","problemas","congelado","crash","imposible"
        )));

        diccionarioInicial.put("Urgencia", new ArrayList<>(Arrays.asList(
                "urgente","ahora","ya","rapido","inmediato","pronto","espera","demora","tardar"
        )));

        diccionarioInicial.put("Enojo", new ArrayList<>(Arrays.asList(
                "molesto","molesta","enojado","enojada","odio","horrible","pesimo","desastre","terrible",
                "malo","ridiculo","absurdo","basta","detengan","cansado","cansada"
        )));
    }

    // Itera sobre el diccionario temporal y llena la lista de objetos PalabraEmocional
    private void cargarPalabrasAlListado() {
        for (Map.Entry<String, List<String>> entry : diccionarioInicial.entrySet()) {
            String emocion = entry.getKey().toUpperCase();
            List<String> palabrasDeEmocion = entry.getValue();

            for (String palabraStr : palabrasDeEmocion) {
                // Se crea un objeto PalabraEmocional por cada par (palabra, emoción)
                PalabraEmocional p = new PalabraEmocional(palabraStr.trim().toLowerCase(), emocion);
                palabras.add(p);
            }
        }
        // Limpiamos el diccionario temporal para ahorrar memoria
        diccionarioInicial.clear();
    }

    // =========================================================================
    //  IMPLEMENTACIÓN IGenericDAO
    // =========================================================================

    @Override public void save(PalabraEmocional p) { palabras.add(p); }

    @Override public List<PalabraEmocional> findAll() { return Collections.unmodifiableList(palabras); }

    // El ID es la palabra
    @Override public PalabraEmocional findById(String id) {
        if (id == null || id.isBlank()) return null;
        String palabraBuscada = id.toLowerCase().trim();
        return palabras.stream()
                .filter(p -> p.getPalabra() != null && p.getPalabra().equals(palabraBuscada))
                .findFirst()
                .orElse(null);
    }

    @Override public void update(PalabraEmocional entidad) {
        // En in-memoria, el objeto ya ha sido modificado por referencia en la BL (e.g., setEmocion),
        // por lo que no es necesario hacer nada. Se deja para el contrato de la interfaz.
    }

    // Implementación de delete(String id) usando la palabra como ID
    @Override
    public void delete(String id) {
        if (id == null || id.isBlank()) return;
        String palabraBuscada = id.toLowerCase().trim();

        // Eliminar la primera coincidencia por palabra.
        palabras.removeIf(p -> p.getPalabra().equals(palabraBuscada));
    }

    // Metodo auxiliar usado por la BL para eliminar una entidad específica
    public void deleteByEntity(PalabraEmocional entidad) {
        if (entidad != null) {
            palabras.remove(entidad);
        }
    }

    // Metodo de validación de unicidad usado por la BL
    public boolean existsByPalabra(String palabra) {
        String w = palabra.trim().toLowerCase();
        for (PalabraEmocional p : palabras) {
            if (p.getPalabra() != null && p.getPalabra().equals(w)) return true;
        }
        return false;
    }
}