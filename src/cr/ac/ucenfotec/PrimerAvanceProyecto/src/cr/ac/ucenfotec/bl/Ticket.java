package cr.ac.ucenfotec.bl;

import cr.ac.ucenfotec.dl.IGenericDAO;
import cr.ac.ucenfotec.dl.TicketDAO_InMemoria;
import java.util.Arrays; // Agregado para validación de estados
import java.util.List;
import java.util.UUID;

public class Ticket {

    private String id;
    private String asunto;
    private String descripcion;
    private String estado; // NUEVO, ASIGNADO, EN_PROCESO, CERRADO
    private Usuario usuario;
    private Departamento departamento;

    private final IGenericDAO<Ticket> dao;

    // Constructores para la Entidad (Ticket individual)
    public Ticket(String asunto, String descripcion, Usuario usuario, Departamento departamento) {
        this.id = UUID.randomUUID().toString();
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.estado = "NUEVO"; // Estado inicial
        this.usuario = usuario;
        this.departamento = departamento;
        this.dao = null;
    }

    // Constructor para el Manager
    public Ticket() {
        this.dao = new TicketDAO_InMemoria();
    }

    // Getters y Setters
    public String getDescripcion() { return descripcion; }
    public String getId() { return id; }
    public String getAsunto() { return asunto; }
    public String getEstado() { return estado; }
    public Usuario getUsuario() { return usuario; }
    public Departamento getDepartamento() { return departamento; }

    // Setter de estado para uso interno y del manager (privado para forzar uso de actualizarEstado)
    private void setEstado(String estado) { this.estado = estado; }

    // Métodos del Manager
    public Ticket registrar(String asunto, String descripcion, Usuario usuario, Departamento departamento) {
        Ticket t = new Ticket(asunto.trim(), descripcion.trim(), usuario, departamento);
        dao.save(t);
        return t;
    }

    public List<Ticket> listar() {
        return dao.findAll();
    }

    // GESTIÓN DE ESTADO (Lógica de Negocio - BL)
    public void actualizarEstado(Ticket ticket, String nuevoEstado) {
        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            throw new IllegalArgumentException("El estado no puede ser vacío.");
        }

        String estadoNormalizado = nuevoEstado.toUpperCase().trim();
        // Validación de estados permitidos
        if (!Arrays.asList("NUEVO", "ASIGNADO", "EN_PROCESO", "CERRADO").contains(estadoNormalizado)) {
            throw new IllegalArgumentException("Estado inválido. Use: NUEVO, ASIGNADO, EN_PROCESO, CERRADO.");
        }

        ticket.setEstado(estadoNormalizado);
        dao.update(ticket); // Persiste el cambio (se llama al método update del DAO)
    }

    @Override
    public String toString() {
        String departamentoNombre = (departamento != null) ? departamento.getNombre() : "N/A";
        return String.format("ID: %s | Asunto: %s | Estado: %s | Usuario: %s | Departamento: %s",
                id.substring(0, 8),
                asunto,
                estado,
                (usuario != null ? usuario.getCorreo() : "N/A"),
                departamentoNombre);
    }
}