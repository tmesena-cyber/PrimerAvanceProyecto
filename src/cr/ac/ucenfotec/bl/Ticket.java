package cr.ac.ucenfotec.bl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Ticket {

    private String id;
    private String asunto;
    private String descripcion;
    private String estado;
    private Usuario usuario;
    private Departamento departamento;
    private final ArrayList<Ticket> tickets = new ArrayList<>();

    public Ticket(String asunto, String descripcion, Usuario usuario, Departamento departamento) {
        this.id = UUID.randomUUID().toString();
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.estado = "nuevo"; // estado inicial
        this.usuario = usuario;
        this.departamento = departamento;
    }

    public Ticket(){}

    public String getDescripcion() { return descripcion; }
    public String getId() { return id; }
    public String getAsunto() { return asunto; }
    public String getEstado() { return estado; }
    public Usuario getUsuario() { return usuario; }
    public Departamento getDepartamento() { return departamento; }

    // NUEVO: permitir cambiar el estado del ticket
    public void setEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("El estado no puede ser vacío.");
        }
        this.estado = estado.trim();
    }

    // Registrar = crear y almacenar en memoria
    public Ticket registrar(String asunto, String descripcion, Usuario usuario, Departamento departamento) {
        Ticket t = new Ticket(asunto.trim(), descripcion.trim(), usuario, departamento);
        tickets.add(t);
        return t;
    }

    // Listar todos los tickets almacenados
    public List<Ticket> listar() {
        return List.copyOf(tickets);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", asunto='" + asunto + '\'' +
                ", estado='" + estado + '\'' +
                ", usuario=" + (usuario != null ? usuario.getNombreCompleto() : "N/A") +
                ", departamento=" + (departamento != null ? departamento.getNombre() : "N/A") +
                '}';
    }
}
