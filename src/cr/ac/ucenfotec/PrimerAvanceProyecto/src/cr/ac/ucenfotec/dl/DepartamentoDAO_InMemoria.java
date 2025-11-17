package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.Departamento;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DepartamentoDAO_InMemoria implements IGenericDAO<Departamento> {

    private final ArrayList<Departamento> departamentos = new ArrayList<>();

    @Override
    public void save(Departamento d) { departamentos.add(d); }

    @Override
    public List<Departamento> findAll() {
        return Collections.unmodifiableList(departamentos);
    }

    /**
     * Lógica de negocio de persistencia: verifica si el correo ya existe.
     */
    public boolean existsByCorreo(String correo) {
        String c = correo.trim().toLowerCase();
        for (Departamento d : departamentos) {
            if (d.getCorreo() != null && d.getCorreo().trim().toLowerCase().equals(c)) return true;
        }
        return false;
    }

    // Métodos para cumplir la interfaz
    @Override public Departamento findById(String id) { return null; }
    @Override public void update(Departamento entidad) { /* No es necesario */ }
    @Override public void delete(String id) { /* Lógica de eliminación */ }

}