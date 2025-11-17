package cr.ac.ucenfotec.bl;

import cr.ac.ucenfotec.dl.IGenericDAO;
import cr.ac.ucenfotec.dl.UsuarioDAO_InMemoria;
import java.util.Collections;
import java.util.List;

public class Usuario {
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String rol;

    // 1. Sustitución del ArrayList por el DAO (Data Access Object)
    private final IGenericDAO<Usuario> dao;

    // Constructor para la Entidad (El objeto de datos, no el Manager)
    public Usuario(String nombreCompleto, String correo, String telefono, String rol) {
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
        this.dao = null; // La entidad no es el manager
    }

    // Constructor para el Manager (Inyección de dependencia por defecto)
    public Usuario() {
        // 2. Inicializa el DAO con la implementación In-Memoria
        this.dao = new UsuarioDAO_InMemoria();
    }

    // Getters y Setters...
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    // Imprimir
    public void imprimirInfo() {
        System.out.println("Usuario: " + nombreCompleto);
        System.out.println("Correo: " + correo);
        System.out.println("Teléfono: " + (telefono == null || telefono.isBlank() ? "(sin registrar)" : telefono));
        System.out.println("Rol: " + rol);
    }

    @Override
    public String toString() {
        return "Usuario{nombre='" + nombreCompleto + "', correo='" + correo +
                "', tel='" + telefono + "', rol='" + rol + "'}";
    }

    // Registrar - Lógica de Negocio + DELEGACIÓN al DAO
    public Usuario registrar(String nombre, String correo, String telefono, String rol) {
        validar(nombre, correo, rol);

        // 3. Uso del metodo específico del DAO (es necesario hacer el cast)
        if (((UsuarioDAO_InMemoria) dao).existsByCorreo(correo)) throw new IllegalStateException("El correo ya existe.");

        Usuario u = new Usuario(nombre, correo, vacioANull(telefono), rol.toUpperCase());
        dao.save(u); // 4. Persistencia delegada al DAO
        return u;
    }

    // Listar - DELEGACIÓN al DAO
    public List<Usuario> listar() {
        return dao.findAll(); // 5. Devolver la lista desde el DAO
    }

    // Nueva función para encontrar un usuario por su correo (Necesaria para registrar tickets)
    public Usuario buscarPorCorreo(String correo) {
        if (correo == null || correo.isBlank()) return null;

        // Se busca en la lista completa
        String c = correo.trim().toLowerCase();
        for (Usuario u : listar()) {
            if (u.getCorreo() != null && u.getCorreo().trim().toLowerCase().equals(c)) {
                return u;
            }
        }
        return null;
    }

    // 6. Se elimina el metodo privado 'existeCorreo' de esta clase.

    private void validar(String nombre, String correo, String rol) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre requerido.");
        if (correo == null || correo.isBlank()) throw new IllegalArgumentException("Correo requerido.");
        if (rol == null || rol.isBlank()) throw new IllegalArgumentException("Rol requerido.");
    }

    private String vacioANull(String t) {
        if (t == null) return null;
        String x = t.trim();
        return x.isEmpty() ? null : x;
    }
}