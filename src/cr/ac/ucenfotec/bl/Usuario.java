package cr.ac.ucenfotec.bl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String rol; // Admin / Estudiante / Funcionario
    private final ArrayList<Usuario> usuarios = new ArrayList<>();

    public Usuario(String nombreCompleto, String correo, String telefono, String rol) {
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
    }

    public Usuario(){};

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    // Para imprimir bonito en el menú
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

    // Registrar = crear y almacenar en memoria
    public Usuario registrar(String nombre, String correo, String telefono, String rol) {
        validar(nombre, correo, rol);
        if (existeCorreo(correo)) throw new IllegalStateException("El correo ya existe.");
        Usuario u = new Usuario(nombre, correo, vacioANull(telefono), rol.toUpperCase());
        usuarios.add(u);
        return u;
    }

    // Listar = devolver todas las instancias
    public List<Usuario> listar() {
        return Collections.unmodifiableList(usuarios);
    }

    private boolean existeCorreo(String correo) {
        String c = correo.trim().toLowerCase();
        for (Usuario u : usuarios) {
            if (u.getCorreo() != null && u.getCorreo().trim().toLowerCase().equals(c)) return true;
        }
        return false;
    }

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
