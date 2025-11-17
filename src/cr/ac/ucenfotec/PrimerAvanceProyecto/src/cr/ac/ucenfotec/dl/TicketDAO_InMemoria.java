package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.Ticket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketDAO_InMemoria implements IGenericDAO<Ticket> {

    // ALMACENAMIENTO
    private final ArrayList<Ticket> tickets = new ArrayList<>();

    @Override
    public void save(Ticket t) {
        tickets.add(t);
    }

    @Override
    public List<Ticket> findAll() {
        return Collections.unmodifiableList(tickets);
    }

    /**
     * El metodo update es importante aquí, ya que se usa para persistir
     * el cambio de estado del ticket.
     * NOTA: En In-Memoria, el objeto Ticket en el ArrayList es el mismo
     * que se modifica en el BL (por referencia).
     * Este metodo solo registra el cambio para mantener el estándar de la interfaz.
     */
    @Override
    public void update(Ticket ticketActualizado) {
        // En una implementación real (ej. JDBC/JPA) aquí iría el UPDATE a la BD.
        // En memoria, solo imprimimos la nota de que se realizó la operación.
        System.out.println(" [NOTA DL] Ticket: " + ticketActualizado.getId().substring(0,8) + " actualizado en memoria.");
    }

    // Métodos para cumplir la interfaz (no se usarán en este avance)
    @Override public Ticket findById(String id) { return null; }
    @Override public void delete(String id) { /* Lógica de eliminación */ }
}