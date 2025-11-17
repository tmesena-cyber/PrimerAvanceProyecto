package cr.ac.ucenfotec.dl;

import java.util.List;

/**
 * Interfaz genérica para la Capa de Acceso a Datos (Data Access Object).
 * Define las operaciones CRUD básicas.
 */
public interface IGenericDAO<T> {
    void save(T entidad);
    List<T> findAll();
    T findById(String id);
    void update(T entidad);
    void delete(String id);
}