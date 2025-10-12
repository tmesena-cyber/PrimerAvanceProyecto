package cr.ac.ucenfotec.bl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PalabraTecnica {
    private String palabra;          // única en la lógica
    private String categoriaTecnica; // REDES/IMPRESORAS/CUENTAS/HARDWARE/SOFTWARE/OTROS
    private final ArrayList<PalabraTecnica> palabras = new ArrayList<>();


    public PalabraTecnica(String palabra, String categoriaTecnica) {
        this.palabra = palabra;
        this.categoriaTecnica = categoriaTecnica;
    }

    public PalabraTecnica(){};

    public String getPalabra() { return palabra; }
    public void setPalabra(String palabra) { this.palabra = palabra; }
    public String getCategoriaTecnica() { return categoriaTecnica; }
    public void setCategoriaTecnica(String categoriaTecnica) { this.categoriaTecnica = categoriaTecnica; }

    // Para imprimir bonito en el menú
    public void imprimirInfo() {
        System.out.println("🧩 Palabra técnica: " + palabra);
        System.out.println("Categoría: " + categoriaTecnica);
    }

    @Override
    public String toString() {
        return "PalabraTecnica{palabra='" + palabra + "', categoria='" + categoriaTecnica + "'}";
    }

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
