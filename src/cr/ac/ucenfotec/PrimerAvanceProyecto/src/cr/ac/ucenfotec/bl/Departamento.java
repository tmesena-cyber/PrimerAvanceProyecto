package cr.ac.ucenfotec.bl;

import cr.ac.ucenfotec.dl.IGenericDAO;
import cr.ac.ucenfotec.dl.DepartamentoDAO_InMemoria;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Departamento {
    private String nombre;
    private String funciones;
    private String correo;
    private int extension;

    private final IGenericDAO<Departamento> dao; // Sustitución del ArrayList

    // Constructores para la Entidad
    public Departamento(String nombre, String funciones, String correo, int extension) {
        this.nombre = nombre;
        this.funciones = funciones;
        this.correo = correo;
        this.extension = extension;
        this.dao = null; // Entidad
    }
    // ... otros constructores de Entidad ...
    public Departamento(String nombre, String funciones) {
        this(nombre, funciones, null, 0);
    }
    public Departamento(String nombre, String funciones, String correo) {
        this(nombre, funciones, correo, 0);
    }
    public Departamento(String nombre, String funciones, int extension) {
        this(nombre, funciones, null, extension);
    }

    // Constructor para el Manager
    public Departamento(){
        this.dao = new DepartamentoDAO_InMemoria(); // Inicializa el DAO
    };

    // Getters
    public String getNombre() { return nombre; }
    public String getFunciones() { return funciones; }
    public String getCorreo() { return correo; }
    public int getExtension() { return extension; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setFunciones(String funciones) { this.funciones = funciones; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setExtension(int extension) { this.extension = extension; }

    // Imprimir
    public void imprimirInfo(){
        System.out.println("Nombre de departamento: " + nombre);
        System.out.println("Funciones: " + funciones);
        System.out.println("Correo: " + (correo == null || correo.isBlank() ? "(sin registrar)" : correo) );
        System.out.println("Extensión telefónica: " + (extension <= 0 ? "(sin registrar)" : extension) );
    }

    // Registrar - Lógica de Negocio + DELEGACIÓN al DAO
    public Departamento registrar(String nombre, String funciones, String correo, int extension) {
        validar(nombre, funciones);

        // Uso del método específico del DAO (cast necesario)
        if (((DepartamentoDAO_InMemoria) dao).existsByCorreo(correo)) throw new IllegalStateException("El correo ya existe.");

        Departamento d = new Departamento(nombre, funciones, correo, extension);
        dao.save(d); // Persistencia delegada
        return d;
    }

    // Listar - DELEGACIÓN al DAO
    public List<Departamento> listar() {
        return dao.findAll(); // Devolver la lista desde el DAO
    }

    // Encontrar un departamento por su nombre (Necesaria para registrar tickets)
    public Departamento buscarPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) return null;

        String n = nombre.trim().toUpperCase();
        for (Departamento d : listar()) {
            if (d.getNombre() != null && d.getNombre().trim().toUpperCase().equals(n)) {
                return d;
            }
        }
        return null;
    }


    // Se elimina el metodo privado 'existeCorreo' de esta clase.

    private void validar(String nombre, String funciones) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre requerido.");
        if (funciones == null || funciones.isBlank()) throw new IllegalArgumentException("Funciones requeridas.");
    }
}