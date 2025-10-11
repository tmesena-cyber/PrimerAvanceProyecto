package bi;

import modelo.PalabraTecnica;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DiccionarioTecnico {
    private final ArrayList<PalabraTecnica> palabras = new ArrayList<>();

    // Registrar = crear y almacenar en memoria
    public PalabraTecnica registrar(String palabra, String categoria) {
        validar(palabra, categoria);
        if (existePalabra(palabra)) throw new IllegalStateException("La palabra ya existe.");
        PalabraTecnica p = new PalabraTecnica(palabra.trim().toLowerCase(), categoria.toUpperCase());
        palabras.add(p); // Composición: el diccionario "posee" sus palabras
        return p;
    }

    // Listar = copia ordenada por palabra
    public List<PalabraTecnica> listar() {
        ArrayList<PalabraTecnica> copia = new ArrayList<>(palabras);
        copia.sort(Comparator.comparing(PalabraTecnica::getPalabra));
        return copia;
    }


    private boolean existePalabra(String palabra) {
        String w = palabra.trim().toLowerCase();
        for (PalabraTecnica p : palabras) {
            if (p.getPalabra() != null && p.getPalabra().equals(w)) return true;
        }
        return false;
    }

    private void validar(String palabra, String categoria) {
        if (palabra == null || palabra.isBlank()) throw new IllegalArgumentException("Palabra requerida.");
        if (categoria == null || categoria.isBlank()) throw new IllegalArgumentException("Categoría requerida.");
        if (palabra.length() > 80) throw new IllegalArgumentException("Palabra muy larga (máx 80).");
    }
}
