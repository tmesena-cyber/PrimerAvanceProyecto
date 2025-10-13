package cr.ac.ucenfotec.bl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Departamento {
    private String nombre;
    private String funciones;
    private String correo;
    private int extension; //La variable es int para facilidad con los constructores
    private final ArrayList<Departamento> departamentos = new ArrayList<>();

    //Constructores
    public Departamento(String nombre, String funciones, String correo, int extension) {
        this.nombre = nombre;
        this.funciones = funciones;
        this.correo = correo;
        this.extension = extension;
    }
    public Departamento(String nombre, String funciones) {
        this.nombre = nombre;
        this.funciones = funciones;
    }
    public Departamento(String nombre, String funciones, String correo) {
        this.nombre = nombre;
        this.funciones = funciones;
        this.correo = correo;
    }
    public Departamento(String nombre, String funciones, int extension) {
        this.nombre = nombre;
        this.funciones = funciones;
        this.extension = extension;
    }
    public Departamento(){};


    //Getters
    public String getNombre() {
        return nombre;
    }
    public String getFunciones() {
        return funciones;
    }
    public String getCorreo() {
        return correo;
    }
    public int getExtension() {
        return extension;
    }

    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setFunciones(String funciones) {
        this.funciones = funciones;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public void setExtension(int extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "nombre='" + nombre + '\'' +
                ", funciones='" + funciones + '\'' +
                ", correo='" + correo + '\'' +
                ", extension=" + extension +
                '}';
    }

    //Para imprimir en el menu
    public void imprimirInfo(){
        System.out.println("Nombre de departamento: " + nombre);
        System.out.println("Funciones: " + funciones);
        System.out.println("Correo: " + (correo == null || correo.isBlank() ? "(sin registrar)" : correo) );
        System.out.println("Extensión telefónica: " + (extension <= 0 ? "(sin registrar)" : extension) );
    }

    // Registrar = crear y almacenar en memoria
    public Departamento registrar(String nombre, String funciones, String correo, int extension) {
        validar(nombre, funciones);
        if (existeCorreo(correo)) throw new IllegalStateException("El correo ya existe.");
        Departamento d = new Departamento(nombre, funciones, correo, extension);
        departamentos.add(d);
        return d;
    }

    // Listar = devolver todas las instancias
    public List<Departamento> listar() {
        return Collections.unmodifiableList(departamentos);
    }

    private boolean existeCorreo(String correo) {
        String c = correo.trim().toLowerCase();
        for (Departamento d : departamentos) {
            if (d.getCorreo() != null && d.getCorreo().trim().toLowerCase().equals(c)) return true;
        }
        return false;
    }

    private void validar(String nombre, String funciones) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre requerido.");
        if (funciones == null || funciones.isBlank()) throw new IllegalArgumentException("Funciones requeridas.");
    }
}
