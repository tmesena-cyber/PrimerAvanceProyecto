package cr.ac.ucenfotec.bl;

import cr.ac.ucenfotec.dl.IGenericDAO;
import cr.ac.ucenfotec.dl.PalabraTecnicaDAO_InMemoria;
import java.util.Comparator;
import java.util.List;

public class PalabraTecnica {

    private String palabra;
    private String categoria;

    // Referencia al DAO (La conexión con la capa DAL)
    private final IGenericDAO<PalabraTecnica> dao;

    // ===============================
    //      CONSTRUCTOR GESTOR (MANAGER)
    // ===============================
    public PalabraTecnica() {
        this.dao = new PalabraTecnicaDAO_InMemoria(); // El DAO carga el diccionario
    }

    // ===============================
    //      CONSTRUCTOR ENTIDAD
    // ===============================
    public PalabraTecnica(String palabra, String categoria) {
        this.palabra = palabra;
        this.categoria = categoria;
        this.dao = null;
    }

    // [GETTERS/SETTERS y métodos utilitarios como imprimirInfo() y toString() se mantienen]
    public String getPalabra() { return palabra; }
    public String getCategoria() { return categoria; }
    public void setPalabra(String palabra) { this.palabra = palabra; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void imprimirInfo() { System.out.println("Palabra: " + palabra + " | Categoría: " + categoria); }
    @Override public String toString() { return palabra + " (" + categoria + ")"; }


    // ===============================
    //      MÉTODOS DE NEGOCIO (CRUD DELEGADO)
    // ===============================

    public PalabraTecnica registrar(String palabra, String categoria) {
        validar(palabra, categoria);
        palabra = palabra.toLowerCase().trim();

        PalabraTecnicaDAO_InMemoria tecnicaDao = (PalabraTecnicaDAO_InMemoria) dao;
        if (tecnicaDao.existePalabra(palabra))
            throw new IllegalStateException("La palabra técnica '" + palabra + "' ya existe.");

        PalabraTecnica nueva = new PalabraTecnica(palabra, categoria.toUpperCase());
        dao.save(nueva);
        return nueva;
    }

    public boolean eliminar(String palabraAEliminar) {
        if (palabraAEliminar == null || palabraAEliminar.isBlank()) return false;
        String buscada = palabraAEliminar.toLowerCase().trim();

        PalabraTecnica p = dao.findById(buscada);

        if (p != null) {
            dao.delete(p.getPalabra());
            return true;
        }
        return false;
    }

    public PalabraTecnica editar(String original, String nuevaPalabra, String nuevaCategoria) {
        validar(nuevaPalabra, nuevaCategoria);
        original = original.toLowerCase().trim();
        nuevaPalabra = nuevaPalabra.toLowerCase().trim();

        PalabraTecnica p = dao.findById(original);

        if (p != null) {
            PalabraTecnicaDAO_InMemoria tecnicaDao = (PalabraTecnicaDAO_InMemoria) dao;
            if (!nuevaPalabra.equals(original) && tecnicaDao.existePalabra(nuevaPalabra)) {
                throw new IllegalStateException("Ya existe una palabra con el nuevo nombre.");
            }

            p.setPalabra(nuevaPalabra);
            p.setCategoria(nuevaCategoria.toUpperCase());
            dao.update(p);
            return p;
        }
        throw new IllegalStateException("La palabra original no existe.");
    }

    public List<PalabraTecnica> listar() {
        List<PalabraTecnica> todasLasPalabras = dao.findAll();
        todasLasPalabras.sort(Comparator.comparing(PalabraTecnica::getPalabra)); // Lógica de ordenamiento en BL
        return todasLasPalabras;
    }

    public PalabraTecnica buscarPorPalabra(String palabra) {
        if (palabra == null) return null;
        return dao.findById(palabra);
    }

    // ===============================
    //      VALIDACIONES DE NEGOCIO (BL)
    // ===============================
    private void validar(String palabra, String categoria) {
        if (palabra == null || palabra.isBlank()) throw new IllegalArgumentException("Palabra requerida.");
        if (categoria == null || categoria.isBlank()) throw new IllegalArgumentException("Categoría requerida.");
        if (palabra.length() > 80) throw new IllegalArgumentException("Palabra muy larga (máx 80).");
    }
}