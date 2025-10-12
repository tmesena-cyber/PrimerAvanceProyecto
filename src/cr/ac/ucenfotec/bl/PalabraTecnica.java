package modelo;

public class PalabraTecnica {
    private String palabra;          // única en la lógica
    private String categoriaTecnica; // REDES/IMPRESORAS/CUENTAS/HARDWARE/SOFTWARE/OTROS

    public PalabraTecnica(String palabra, String categoriaTecnica) {
        this.palabra = palabra;
        this.categoriaTecnica = categoriaTecnica;
    }

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
}
