package cr.ac.ucenfotec.bl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PalabraEmocional {
    private String palabra;
    private String emocion;
    private final ArrayList<PalabraEmocional> palabras = new ArrayList<>();

    //Constructores
    public PalabraEmocional(String palabra, String emocion){
        this.palabra = palabra;
        this.emocion = emocion;
    }
    public PalabraEmocional(){};

    //Getters
    public String getPalabra() {
        return palabra;
    }
    public String getEmocion() {
        return emocion;
    }

    //Setters
    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }
    public void setEmocion(String emocion) {
        this.emocion = emocion;
    }

    //ToString
    @Override
    public String toString() {
        return "PalabraEmocional{" +
                "emocion='" + emocion + '\'' +
                ", palabra='" + palabra + '\'' +
                '}';
    }

    // Para imprimir bonito en el menú
    public void imprimirInfo() {
        System.out.println("Palabra emocional: " + palabra);
        System.out.println("Categoría: " + emocion);
    }

    // Registrar = crear y almacenar en memoria
    public PalabraEmocional registrar(String palabra, String emocion) {
        validar(palabra, emocion);
        if (existePalabra(palabra)) throw new IllegalStateException("La palabra ya existe.");
        PalabraEmocional p = new PalabraEmocional(palabra.trim().toLowerCase(), emocion.toUpperCase());
        palabras.add(p); // Composición: el diccionario "posee" sus palabras
        return p;
    }

    // Listar = copia ordenada por palabra
    public List<PalabraEmocional> listar() {
        ArrayList<PalabraEmocional> copia = new ArrayList<>(palabras);
        copia.sort(Comparator.comparing(PalabraEmocional::getPalabra));
        return copia;
    }


    private boolean existePalabra(String palabra) {
        String w = palabra.trim().toLowerCase();
        for (PalabraEmocional p : palabras) {
            if (p.getPalabra() != null && p.getPalabra().equals(w)) return true;
        }
        return false;
    }

    private void validar(String palabra, String emocion) {
        if (palabra == null || palabra.isBlank()) throw new IllegalArgumentException("Palabra requerida.");
        if (emocion == null || emocion.isBlank()) throw new IllegalArgumentException("Emoción requerida.");
        if (palabra.length() > 80) throw new IllegalArgumentException("Palabra muy larga (máx 80).");
    }
}
