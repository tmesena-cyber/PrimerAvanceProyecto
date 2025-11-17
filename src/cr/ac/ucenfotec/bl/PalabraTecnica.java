package cr.ac.ucenfotec.bl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PalabraTecnica {

    private String palabra;
    private String categoria;

    private final ArrayList<PalabraTecnica> palabras = new ArrayList<>();

    // ===============================
    //   CONSTRUCTOR GESTOR
    //   (CARGA TODO EL DICCIONARIO)
    // ===============================
    public PalabraTecnica() {
        cargarDiccionarioTecnicoCompleto();
    }

    // ===============================
    //   CONSTRUCTOR ENTIDAD
    // ===============================
    public PalabraTecnica(String palabra, String categoria) {
        this.palabra = palabra;
        this.categoria = categoria;
    }

    // ===============================
    //   GETTERS / SETTERS
    // ===============================
    public String getPalabra() { return palabra; }
    public String getCategoria() { return categoria; }

    public void setPalabra(String palabra) { this.palabra = palabra; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    // ===============================
    //     CRUD COMPLETO
    // ===============================

    public PalabraTecnica registrar(String palabra, String categoria) {
        palabra = palabra.toLowerCase().trim();

        if (existe(palabra))
            throw new IllegalStateException("La palabra técnica ya existe.");

        PalabraTecnica nueva = new PalabraTecnica(palabra, categoria.toUpperCase());
        palabras.add(nueva);
        return nueva;
    }

    public boolean eliminar(String palabraAEliminar) {
        if (palabraAEliminar == null || palabraAEliminar.isBlank())
            return false;

        String buscada = palabraAEliminar.toLowerCase().trim();

        return palabras.removeIf(p -> p.getPalabra().equals(buscada));
    }

    public PalabraTecnica editar(String original, String nuevaPalabra, String nuevaCategoria) {
        original = original.toLowerCase().trim();
        nuevaPalabra = nuevaPalabra.toLowerCase().trim();

        for (PalabraTecnica p : palabras) {
            if (p.getPalabra().equals(original)) {

                if (!nuevaPalabra.equals(original) && existe(nuevaPalabra))
                    throw new IllegalStateException("Ya existe una palabra con ese nombre.");

                p.setPalabra(nuevaPalabra);
                p.setCategoria(nuevaCategoria.toUpperCase());
                return p;
            }
        }
        throw new IllegalStateException("La palabra original no existe.");
    }

    public List<PalabraTecnica> listar() {
        ArrayList<PalabraTecnica> copia = new ArrayList<>(palabras);
        copia.sort(Comparator.comparing(PalabraTecnica::getPalabra));
        return copia;
    }

    // ===============================
    //   BUSCAR PARA BoWAnalyzer
    // ===============================
    public PalabraTecnica buscarPorPalabra(String palabra) {
        if (palabra == null) return null;
        String buscada = palabra.toLowerCase().trim();

        for (PalabraTecnica p : palabras) {
            if (p.getPalabra().equals(buscada)) {
                return p;
            }
        }
        return null;
    }

    public boolean existe(String palabra) {
        palabra = palabra.toLowerCase().trim();
        for (PalabraTecnica p : palabras) {
            if (p.getPalabra().equals(palabra))
                return true;
        }
        return false;
    }

    // ===============================
    //   DICCIONARIO PROFESIONAL
    // ===============================
    private void cargarDiccionarioTecnicoCompleto() {

        // SOFTWARE
        registrar("aplicacion", "SOFTWARE");
        registrar("aplicacion lenta", "SOFTWARE");
        registrar("no abre", "SOFTWARE");
        registrar("no carga", "SOFTWARE");
        registrar("crash", "SOFTWARE");
        registrar("congelado", "SOFTWARE");
        registrar("reinicio forzado", "SOFTWARE");
        registrar("actualizacion fallida", "SOFTWARE");
        registrar("driver", "SOFTWARE");
        registrar("controlador", "SOFTWARE");
        registrar("error 404", "SOFTWARE");
        registrar("error 500", "SOFTWARE");

        // HARDWARE
        registrar("servidor", "HARDWARE");
        registrar("servidor caido", "HARDWARE");
        registrar("disco lleno", "HARDWARE");
        registrar("disco duro", "HARDWARE");
        registrar("memoria llena", "HARDWARE");
        registrar("ventilador fallando", "HARDWARE");
        registrar("equipo quemado", "HARDWARE");
        registrar("pantalla negra", "HARDWARE");

        // REDES
        registrar("conexion", "REDES");
        registrar("conexion fallida", "REDES");
        registrar("sin internet", "REDES");
        registrar("latencia", "REDES");
        registrar("dns", "REDES");
        registrar("ip duplicada", "REDES");
        registrar("vpn no conecta", "REDES");
        registrar("wifi caido", "REDES");
        registrar("ethernet desconectado", "REDES");
        registrar("paquetes perdidos", "REDES");

        // IMPRESORAS
        registrar("impresora", "IMPRESORAS");
        registrar("impresora no imprime", "IMPRESORAS");
        registrar("atasco de papel", "IMPRESORAS");
        registrar("cola de impresion", "IMPRESORAS");
        registrar("tinta baja", "IMPRESORAS");
        registrar("cartucho incompatible", "IMPRESORAS");
        registrar("no detecta impresora", "IMPRESORAS");

        // CUENTAS Y ACCESOS
        registrar("cuenta bloqueada", "CUENTAS");
        registrar("restablecer contraseña", "CUENTAS");
        registrar("login fallido", "CUENTAS");
        registrar("no puedo ingresar", "CUENTAS");
        registrar("sin acceso", "CUENTAS");
        registrar("usuario inactivo", "CUENTAS");

        // SERVIDORES / SISTEMAS
        registrar("servicio detenido", "SISTEMAS");
        registrar("servicio caido", "SISTEMAS");
        registrar("base de datos caida", "SISTEMAS");
        registrar("reiniciar servicio", "SISTEMAS");
        registrar("cpu al 100", "SISTEMAS");
        registrar("memoria al maximo", "SISTEMAS");
        registrar("tiempo de respuesta alto", "SISTEMAS");

        // SEGURIDAD
        registrar("virus detectado", "SEGURIDAD");
        registrar("malware", "SEGURIDAD");
        registrar("phishing", "SEGURIDAD");
        registrar("correo sospechoso", "SEGURIDAD");
        registrar("acceso no autorizado", "SEGURIDAD");
        registrar("alerta de seguridad", "SEGURIDAD");

        // BASES DE DATOS
        registrar("conexion a bd fallida", "BASES DE DATOS");
        registrar("query lenta", "BASES DE DATOS");
        registrar("tabla corrupta", "BASES DE DATOS");
        registrar("timeout en base de datos", "BASES DE DATOS");

        // OTROS GENERALES
        registrar("no funciona", "OTROS");
        registrar("no responde", "OTROS");
        registrar("fallando", "OTROS");
        registrar("soporte requerido", "OTROS");
        registrar("problema tecnico", "OTROS");
    }

    // Para imprimir bonito si lo necesitas
    public void imprimirInfo() {
        System.out.println("Palabra: " + palabra + " | Categoría: " + categoria);
    }

    @Override
    public String toString() {
        return palabra + " (" + categoria + ")";
    }
}
