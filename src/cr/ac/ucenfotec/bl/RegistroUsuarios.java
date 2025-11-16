package cr.ac.ucenfotec.bl;

import java.util.ArrayList;

public class RegistroUsuarios {

    private ArrayList<Usuario> usuarios = new ArrayList<>();

    public Usuario registrar(String nombre, String correo, String telefono, String rol) {
        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }

        if (buscarPorCorreo(correo) != null) {
            throw new IllegalStateException("Ya existe un usuario con ese correo.");
        }

        Usuario u = new Usuario(nombre, correo, telefono, rol);
        usuarios.add(u);
        return u;
    }

    public Usuario buscarPorCorreo(String correo) {
        if (correo == null) {
            return null;
        }

        String c = correo.trim().toLowerCase();

        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(c)) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<Usuario> listar() {
        return usuarios;
    }
}
