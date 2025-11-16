package cr.ac.ucenfotec.bl;

public class PalabraTecnica {

    private String palabra;
    private String categoria;

    public PalabraTecnica(String palabra, String categoria) {
        this.palabra = palabra.toLowerCase();
        this.categoria = categoria.toUpperCase();
    }

    public String getPalabra() {
        return palabra;
    }

    public String getCategoria() {
        return categoria;
    }

    public void imprimirInfo() {
        System.out.println("Palabra técnica: " + palabra);
        System.out.println("Categoría: " + categoria);
    }

    @Override
    public String toString() {
        return palabra + " (" + categoria + ")";
    }
}
