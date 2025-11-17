package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class UsuarioDAO_InMemoria implements IGenericDAO<Usuario> {

    private final ArrayList<Usuario> usuarios = new ArrayList<>();

    @Override
    public void save(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public List<Usuario> findAll() {
        return Collections.unmodifiableList(usuarios);
    }

    // Metodo auxiliar necesario para la validación de BL
    public boolean existsByCorreo(String correo) {
        String c = correo.trim().toLowerCase();
        for (Usuario u : usuarios) {
            if (u.getCorreo() != null && u.getCorreo().trim().toLowerCase().equals(c)) return true;
        }
        return false;
    }

    // Métodos para cumplir la interfaz (no se usarán en este avance)
    @Override
    public Usuario findById(String id) { return null; }
    @Override
    public void update(Usuario entidad) { /* La actualización del objeto por referencia es suficiente en memoria */ }
    @Override
    public void delete(String id) { /* Lógica de eliminación si aplica */ }
}