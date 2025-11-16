package cr.ac.ucenfotec.bl;

public class Usuario {

    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String rol; // ADMIN, ESTUDIANTE, FUNCIONARIO

    public Usuario(String nombreCompleto, String correo, String telefono, String rol) {
        this.nombreCompleto = nombreCompleto;
        this.correo = correo.toLowerCase();
        this.telefono = telefono;
        this.rol = rol.toUpperCase();
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void imprimirInfo() {
        System.out.println("Usuario: " + nombreCompleto);
        System.out.println("Correo: " + correo);
        System.out.println("Teléfono: " + (telefono == null || telefono.isBlank() ? "(sin teléfono)" : telefono));
        System.out.println("Rol: " + rol);
    }

    @Override
    public String toString() {
        return nombreCompleto + " - " + correo + " (" + rol + ")";
    }
}
