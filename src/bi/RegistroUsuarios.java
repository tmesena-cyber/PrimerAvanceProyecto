package bi;

import modelo.Usuario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegistroUsuarios {
    private final ArrayList<Usuario> usuarios = new ArrayList<>();

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
