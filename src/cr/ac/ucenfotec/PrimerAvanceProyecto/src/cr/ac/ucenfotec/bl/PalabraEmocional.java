package cr.ac.ucenfotec.bl;

import cr.ac.ucenfotec.dl.IGenericDAO;
import cr.ac.ucenfotec.dl.PalabraEmocionalDAO_InMemoria;
import java.util.*;
import java.util.stream.Collectors;

public class PalabraEmocional {

    // Propiedades de la ENTIDAD (una sola palabra)
    private String palabra;
    private String emocion; // Frustración, Urgencia, Enojo, etc.

    // Instancia del DAO: usada solo por la instancia que actúa como Manager
    private final IGenericDAO<PalabraEmocional> dao;

    // 1. Constructor para la Entidad (una instancia individual)
    public PalabraEmocional(String palabra, String emocion){
        this.palabra = palabra;
        this.emocion = emocion;
        this.dao = null; // Las entidades no necesitan el DAO
    }

    // 2. Constructor para el Manager (la instancia que maneja la lógica de negocio)
    public PalabraEmocional(){
        // Inicializa el DAO, que a su vez carga el diccionario hardcodeado
        this.dao = new PalabraEmocionalDAO_InMemoria();
    };

    // Getters
    public String getPalabra() {
        return palabra;
    }
    public String getEmocion() {
        return emocion;
    }

    // Setters
    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }
    public void setEmocion(String emocion) {
        this.emocion = emocion;
    }

    // Para imprimir la info de la palabra
    public void imprimirInfo() {
        System.out.println("Palabra: " + palabra + " | Emoción: " + emocion);
    }

    // =========================================================================
    //  MÉTODOS DEL MANAGER (BL)
    // =========================================================================

    // Registrar/Agregar Palabra: Lógica de Negocio + DELEGACIÓN al DAO
    public PalabraEmocional registrar(String palabra, String emocion) {
        validar(palabra, emocion);

        // Se llama al metodo de validación de unicidad que está en el DAO
        PalabraEmocionalDAO_InMemoria emocionDao = (PalabraEmocionalDAO_InMemoria) dao;
        if (emocionDao.existsByPalabra(palabra)) {
            throw new IllegalStateException("La palabra '" + palabra + "' ya existe en el diccionario emocional.");
        }

        // Crea la entidad y la guarda
        PalabraEmocional p = new PalabraEmocional(palabra.trim().toLowerCase(), emocion.toUpperCase());
        dao.save(p);
        return p;
    }

    // Listar = obtener todos los datos del DAO y devolverlos ordenados
    public List<PalabraEmocional> listar() {
        List<PalabraEmocional> todasLasPalabras = dao.findAll();

        // Ordena la lista antes de devolverla, manteniendo la lógica BL
        todasLasPalabras.sort(Comparator.comparing(PalabraEmocional::getEmocion).thenComparing(PalabraEmocional::getPalabra));

        return todasLasPalabras;
    }

    // Listar todas las emociones únicas y ordenadas.
    public List<String> listarEmocionesOrdenadas() {
        // 1. Obtener todas las palabras emocionales (se delega al DAO)
        List<PalabraEmocional> todasLasPalabras = dao.findAll();

        // 2. Usar un Set para asegurar la unicidad y un TreeSet para ordenarlas alfabéticamente
        Set<String> nombresEmociones = new TreeSet<>();

        for (PalabraEmocional p : todasLasPalabras) {
            // Agregamos el nombre de la emoción, el Set se encarga de que no haya duplicados.
            nombresEmociones.add(p.getEmocion());
        }

        // 3. Convertir el Set ordenado a List para retornarlo
        return new ArrayList<>(nombresEmociones);
    }

    // Obtener las palabras de una emoción específica.
    public List<String> obtenerPalabras(String emocion) {
        if (emocion == null || emocion.isBlank()) {
            return Collections.emptyList();
        }
        String emocionNormalizada = emocion.toUpperCase().trim();

        // 1. Obtener todas las palabras (Delegación al DAO)
        List<PalabraEmocional> todasLasPalabras = dao.findAll();

        // 2. Filtrar y extraer solo las palabras que coinciden con la emoción
        return todasLasPalabras.stream()
                .filter(p -> emocionNormalizada.equals(p.getEmocion()))
                .map(PalabraEmocional::getPalabra)
                .collect(Collectors.toList());
    }

    // Actualiza el nombre de una emoción (e.g., de 'ENOJO' a 'IRA')
    public boolean actualizarNombreEmocion(String nombreOriginal, String nuevoNombre) {
        if (nombreOriginal == null || nombreOriginal.isBlank() || nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("Ambos nombres de emoción son requeridos.");
        }

        String original = nombreOriginal.toUpperCase().trim();
        String nuevo = nuevoNombre.toUpperCase().trim();

        // No permitir renombrar si el nuevo nombre ya existe como una emoción diferente
        if (!original.equals(nuevo) && listarEmocionesOrdenadas().contains(nuevo)) {
            throw new IllegalStateException("La emoción '" + nuevoNombre + "' ya existe.");
        }

        // Buscar todas las palabras de la emoción original
        List<PalabraEmocional> todasLasPalabras = dao.findAll();
        boolean actualizado = false;

        for (PalabraEmocional p : todasLasPalabras) {
            if (p.getEmocion().equals(original)) {
                p.setEmocion(nuevo);
                dao.update(p);
                actualizado = true;
            }
        }
        return actualizado;
    }

    // Elimina una palabra específica de la emoción.
    public boolean eliminarPalabra(String emocion, String palabra) {
        if (emocion == null || emocion.isBlank() || palabra == null || palabra.isBlank()) {
            throw new IllegalArgumentException("Emoción y palabra son requeridas.");
        }
        String emocionNormalizada = emocion.toUpperCase().trim();
        String palabraNormalizada = palabra.toLowerCase().trim();

        PalabraEmocional palabraAEliminar = dao.findAll().stream()
                .filter(p -> p.getEmocion().equals(emocionNormalizada) && p.getPalabra().equals(palabraNormalizada))
                .findFirst()
                .orElse(null);

        if (palabraAEliminar != null) {
            // Se usa un metodo auxiliar del DAO que elimina el objeto por referencia/entidad
            ((PalabraEmocionalDAO_InMemoria) dao).deleteByEntity(palabraAEliminar);
            return true;
        }
        return false;
    }

    // Elimina todos los pares (palabra, emoción) asociados a una emoción.
    public boolean eliminarEmocion(String emocion) {
        if (emocion == null || emocion.isBlank()) {
            throw new IllegalArgumentException("Emoción requerida.");
        }
        String emocionNormalizada = emocion.toUpperCase().trim();

        // Buscar y eliminar todas las palabras asociadas a esa emoción
        List<PalabraEmocional> palabrasDeEmocion = dao.findAll().stream()
                .filter(p -> p.getEmocion().equals(emocionNormalizada))
                .collect(Collectors.toList());

        if (palabrasDeEmocion.isEmpty()) {
            return false;
        }

        for (PalabraEmocional p : palabrasDeEmocion) {
            ((PalabraEmocionalDAO_InMemoria) dao).deleteByEntity(p);
        }

        return true;
    }

    // Reemplaza todas las palabras de una emoción con una nueva lista de palabras
    public boolean actualizarPalabras(String emocion, List<String> nuevasPalabras) {
        if (emocion == null || emocion.isBlank()) {
            throw new IllegalArgumentException("Emoción requerida.");
        }
        String emocionNormalizada = emocion.toUpperCase().trim();

        // 1. Eliminar palabras existentes para esa emoción
        // Se llama al metodo que elimina todas las palabras de esa emoción
        eliminarEmocion(emocionNormalizada);

        // 2. Agregar las nuevas palabras
        boolean exito = false;
        if (nuevasPalabras != null && !nuevasPalabras.isEmpty()) {
            for (String nuevaPalabra : nuevasPalabras) {
                // Se usa el metodo registrar para re-insertar
                try {
                    registrar(nuevaPalabra, emocionNormalizada);
                    exito = true;
                } catch (IllegalStateException e) {
                    System.err.println("Advertencia: La palabra '" + nuevaPalabra + "' ya existe en el diccionario. No se agregó a " + emocionNormalizada);
                }
            }
        }
        // Si no había palabras y se agregaron, o si había y se agregaron nuevas (aunque se eliminaran las viejas), es un éxito.
        return exito;
    }

    // Validaciones de negocio
    private void validar(String palabra, String emocion) {
        if (palabra == null || palabra.isBlank()) throw new IllegalArgumentException("Palabra requerida.");
        if (emocion == null || emocion.isBlank()) throw new IllegalArgumentException("Emoción requerida.");
        if (palabra.length() > 80) throw new IllegalArgumentException("Palabra muy larga (máx 80).");
    }
}