package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.PalabraTecnica;
import java.util.ArrayList;
import java.util.List;

public class PalabraTecnicaDAO_InMemoria implements IGenericDAO<PalabraTecnica> {

    // ALMACENAMIENTO (Data Layer)
    private final ArrayList<PalabraTecnica> palabras = new ArrayList<>();

    // Constructor: Carga el diccionario hardcodeado al inicializarse
    public PalabraTecnicaDAO_InMemoria() {
        cargarDiccionarioTecnicoCompleto();
    }

    // --- Implementación de CRUD ---

    @Override
    public void save(PalabraTecnica entity) {
        if (!existePalabra(entity.getPalabra())) {
            palabras.add(entity);
        }
    }

    /**
     * CORRECCIÓN: Se usa String id para cumplir con IGenericDAO<T>
     */
    @Override
    public PalabraTecnica findById(String id) {
        String buscada = id.toLowerCase().trim();
        for (PalabraTecnica p : palabras) {
            if (p.getPalabra() != null && p.getPalabra().equals(buscada)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<PalabraTecnica> findAll() {
        return new ArrayList<>(palabras); // Devuelve una copia
    }

    @Override
    public void update(PalabraTecnica entidad) {
        // En In-Memoria, el objeto se pasa por referencia y ya fue modificado.
        // Se deja como marcador para el futuro.
    }

    /**
     * CORRECCIÓN: Implementación OBLIGATORIA para la interfaz.
     */
    @Override
    public void delete(String id) {
        // Lógica de eliminación si aplica
    }

    // Lógica auxiliar de validación (utilizada por la BL)
    public boolean existePalabra(String palabra) {
        String w = palabra.trim().toLowerCase();
        for (PalabraTecnica p : palabras) {
            if (p.getPalabra() != null && p.getPalabra().equals(w)) return true;
        }
        return false;
    }

    // ===================================
    //  Contenido Hardcodeado (sin cambios)
    // ===================================

    private void cargarDiccionarioTecnicoCompleto() {
        // REDES
        registrarInterno("no hay internet", "REDES");
        registrarInterno("red caida", "REDES");
        registrarInterno("conexion lenta", "REDES");
        registrarInterno("sin señal wifi", "REDES");
        registrarInterno("problemas de ip", "REDES");

        // IMPRESORAS
        registrarInterno("impresora sin tinta", "IMPRESORAS");
        registrarInterno("papel atascado", "IMPRESORAS");
        registrarInterno("no imprime", "IMPRESORAS");
        registrarInterno("toner bajo", "IMPRESORAS");

        // CUENTAS
        registrarInterno("clave bloqueada", "CUENTAS");
        registrarInterno("cambiar clave", "CUENTAS");
        registrarInterno("usuario no existe", "CUENTAS");
        registrarInterno("acceso denegado", "CUENTAS");

        // HARDWARE
        registrarInterno("computadora lenta", "HARDWARE");
        registrarInterno("pantalla negra", "HARDWARE");
        registrarInterno("mouse no funciona", "HARDWARE");
        registrarInterno("teclado desconfigurado", "HARDWARE");
        registrarInterno("disco duro lleno", "HARDWARE");

        // SOFTWARE
        registrarInterno("error excel", "SOFTWARE");
        registrarInterno("no abre aplicacion", "SOFTWARE");
        registrarInterno("actualizacion fallida", "SOFTWARE");
        registrarInterno("licencia caducada", "SOFTWARE");

        // SISTEMAS
        registrarInterno("servidor caido", "SISTEMAS");
        registrarInterno("reiniciar servicio", "SISTEMAS");
        registrarInterno("cpu al 100", "SISTEMAS");
        registrarInterno("memoria al maximo", "SISTEMAS");
        registrarInterno("tiempo de respuesta alto", "SISTEMAS");

        // SEGURIDAD
        registrarInterno("virus detectado", "SEGURIDAD");
        registrarInterno("malware", "SEGURIDAD");
        registrarInterno("phishing", "SEGURIDAD");
        registrarInterno("correo sospechoso", "SEGURIDAD");
        registrarInterno("acceso no autorizado", "SEGURIDAD");
        registrarInterno("alerta de seguridad", "SEGURIDAD");

        // BASES DE DATOS
        registrarInterno("conexion a bd fallida", "BASES DE DATOS");
        registrarInterno("query lenta", "BASES DE DATOS");
        registrarInterno("tabla corrupta", "BASES DE DATOS");
        registrarInterno("timeout en base de datos", "BASES DE DATOS");

        // OTROS GENERALES
        registrarInterno("no funciona", "OTROS");
        registrarInterno("no responde", "OTROS");
        registrarInterno("fallando", "OTROS");
        registrarInterno("soporte requerido", "OTROS");
        registrarInterno("problema tecnico", "OTROS");
    }

    // Método auxiliar para crear la entidad y añadirla a la lista interna.
    private void registrarInterno(String palabra, String categoria) {
        PalabraTecnica p = new PalabraTecnica(palabra.toLowerCase().trim(), categoria.toUpperCase());
        palabras.add(p);
    }
}