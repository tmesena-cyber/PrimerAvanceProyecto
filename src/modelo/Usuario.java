package modelo;

public class Usuario {
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String rol; // Admin / Estudiante / Funcionario

    public Usuario(String nombreCompleto, String correo, String telefono, String rol) {
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
    }

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
}
