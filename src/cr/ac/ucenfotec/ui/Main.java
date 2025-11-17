package cr.ac.ucenfotec.ui;

import java.util.Scanner;
import java.util.Map;
import java.util.List;

import cr.ac.ucenfotec.bl.PalabraTecnica;
import cr.ac.ucenfotec.bl.Usuario;
import cr.ac.ucenfotec.bl.Departamento;
import cr.ac.ucenfotec.bl.PalabraEmocional;
import cr.ac.ucenfotec.bl.Ticket;
import cr.ac.ucenfotec.bl.BoWAnalyzer;

public class Main {

    private static void presionaEnter(Scanner sc) {
        System.out.println();
        System.out.println("(Pulsa ENTER para continuar)");
        sc.nextLine();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        MenuEmociones menuEmociones = new MenuEmociones(diccionarioEmocional, sc);

        while (true) {
            System.out.println("""
                    MENÚ PRINCIPAL (Consola)
                    1) Registrar Usuario
                    2) Listar Usuarios
                    3) Registrar Departamento
                    4) Listar Departamentos
                    5) Registrar Palabra Técnica
                    6) Listar Palabras Técnicas
                    7) Gestionar Palabras Emocionales
                    8) Listar Palabras Emocionales
                    9) Registrar Ticket
                    10) Listar Tickets y Analizar BoW
                    0) Salir
                    """);
            System.out.print("Opción: ");
            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1": {
                    System.out.print("Nombre completo: ");
                    String nombre = sc.nextLine();
                    System.out.print("Correo: ");
                    String correo = sc.nextLine();
                    System.out.print("Teléfono (opcional): ");
                    String tel = sc.nextLine();
                    System.out.print("Rol (ADMIN/ESTUDIANTE/FUNCIONARIO): ");
                    String rol = sc.nextLine();

                    try {
                        Usuario u = usuarioManager.registrar(nombre, correo, tel, rol);
                        System.out.println();
                        System.out.println("Usuario creado:");
                        u.imprimirInfo();
                    } catch (Exception e) {
                        System.out.println("Error al registrar usuario: " + e.getMessage());
                    }
                    presionaEnter(sc);
                    break;
                }
                case "2": {
                    System.out.println("=== Usuarios registrados ===");
                    List<Usuario> lista = usuarioManager.listar();
                    if (lista.isEmpty()) {
                        System.out.println("No hay usuarios registrados.");
                    } else {
                        for (Usuario u : lista) {
                            u.imprimirInfo();
                            System.out.println("-----------------------------");
                        }
                    }
                    presionaEnter(sc);
                    break;
                }
                case "3": {
                    System.out.print("Nombre de Departamento: ");
                    String nombre = sc.nextLine();
                    System.out.print("Funciones: ");
                    String funciones = sc.nextLine();
                    System.out.print("Correo (opcional): ");
                    String correo = sc.nextLine();
                    System.out.print("Número de extensión (opcional): ");
                    String extInput = sc.nextLine();

                    int extension = 0;
                    if (!extInput.isBlank()) {
                        try {
                            extension = Integer.parseInt(extInput);
                        } catch (NumberFormatException e) {
                            System.out.println("Error: el número de extensión debe ser un entero.");
                            presionaEnter(sc);
                            break;
                        }
                    }

                    try {
                        Departamento d = departamentoManager.registrar(nombre, funciones, correo, extension);
                        System.out.println();
                        System.out.println("Departamento creado:");
                        d.imprimirInfo();
                    } catch (Exception e) {
                        System.out.println("Error al registrar departamento: " + e.getMessage());
                    }
                    presionaEnter(sc);
                    break;
                }
                case "4": {
                    System.out.println("=== Departamentos registrados ===");
                    List<Departamento> lista = departamentoManager.listar();
                    if (lista.isEmpty()) {
                        System.out.println("No hay departamentos registrados.");
                    } else {
                        for (Departamento d : lista) {
                            d.imprimirInfo();
                            System.out.println("-----------------------------");
                        }
                    }
                    presionaEnter(sc);
                    break;
                }
                case "6": {
                    System.out.println("=== Diccionario Técnico ===");
                    List<PalabraTecnica> lista = tecnicaManager.listar();
                    if (lista.isEmpty()) {
                        System.out.println("No hay palabras técnicas registradas.");
                    } else {
                        for (PalabraTecnica p : lista) {
                            p.imprimirInfo();
                            System.out.println("-----------------------------");
                        }
                    }
                    presionaEnter(sc);
                    break;
                }
                case "9": {
                    List<Usuario> usuarios = usuarioManager.listar();
                    List<Departamento> departamentos = departamentoManager.listar();

                    if (usuarios.isEmpty() || departamentos.isEmpty()) {
                        System.out.println("Debe existir al menos un usuario y un departamento antes de crear tickets.");
                        presionaEnter(sc);
                        break;
                    }

                    System.out.println();
                    System.out.println("--- REGISTRO DE TICKET ---");
                    System.out.print("Asunto del Ticket: ");
                    String asunto = sc.nextLine();
                    System.out.print("Descripción del problema: ");
                    String descripcion = sc.nextLine();

                    System.out.println();
                    System.out.println("Seleccione Usuario:");
                    for (int i = 0; i < usuarios.size(); i++) {
                        System.out.println((i + 1) + ") " + usuarios.get(i).getNombreCompleto());
                    }
                    System.out.print("Número de usuario: ");
                    int idxUsuario;
                    try {
                        idxUsuario = Integer.parseInt(sc.nextLine()) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Opción inválida.");
                        presionaEnter(sc);
                        break;
                    }
                    if (idxUsuario < 0 || idxUsuario >= usuarios.size()) {
                        System.out.println("Usuario inválido.");
                        presionaEnter(sc);
                        break;
                    }
                    case "7": {
                        menuEmociones.mostrarMenu();
                        presionaEnter(sc);
                        break;
                    }
                    case "8": {


                        presionaEnter(sc);
                        break;
                    }
                    case "9": {
                        // 1. Verificar si existen dependencias
                        List<Usuario> listaUsuarios = usuarios.listar();
                        List<Departamento> listaDeptos = departamentos.listar();

                        if (listaUsuarios.isEmpty() || listaDeptos.isEmpty()) {
                            System.out.println("(!) Error: Necesitas registrar al menos un Usuario y un Departamento.");
                            presionaEnter(sc);
                            break;
                        }

                        System.out.println("\n--- REGISTRO DE TICKET ---");
                        System.out.print("Asunto del Ticket: ");
                        String asunto = sc.nextLine();

                        System.out.print("Descripción del problema (el texto para BoW): ");
                        String descripcion = sc.nextLine();

                        // Selección de Usuario
                        System.out.println("\n--- Seleccione Usuario ---");
                        for (int i = 0; i < listaUsuarios.size(); i++) {
                            System.out.println((i + 1) + ") " + listaUsuarios.get(i).getNombreCompleto());
                        }
                        System.out.print("Número de Usuario: ");
                        int userIndex = Integer.parseInt(sc.nextLine()) - 1;
                        Usuario usuarioSeleccionado = listaUsuarios.get(userIndex);

                        // Selección de Departamento
                        System.out.println("\n--- Seleccione Departamento ---");
                        for (int i = 0; i < listaDeptos.size(); i++) {
                            System.out.println((i + 1) + ") " + listaDeptos.get(i).getNombre());
                        }
                        System.out.print("Número de Departamento: ");
                        int deptoIndex = Integer.parseInt(sc.nextLine()) - 1;
                        Departamento deptoSeleccionado = listaDeptos.get(deptoIndex);

                        // Registrar Ticket
                        Ticket t = ticketsManager.registrar(asunto, descripcion, usuarioSeleccionado, deptoSeleccionado);
                        System.out.println("\n [OK] Ticket Creado: " + t.getId() + "\n");
                    Usuario uSel = usuarios.get(idxUsuario);

                    System.out.println();
                    System.out.println("Seleccione Departamento:");
                    for (int i = 0; i < departamentos.size(); i++) {
                        System.out.println((i + 1) + ") " + departamentos.get(i).getNombre());
                    }
                    System.out.print("Número de departamento: ");
                    int idxDepto;
                    try {
                        idxDepto = Integer.parseInt(sc.nextLine()) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Opción inválida.");
                        presionaEnter(sc);
                        break;
                    }
                    if (idxDepto < 0 || idxDepto >= departamentos.size()) {
                        System.out.println("Departamento inválido.");
                        presionaEnter(sc);
                        break;
                    }
                    Departamento dSel = departamentos.get(idxDepto);

                    Ticket t = ticketManager.registrar(asunto, descripcion, uSel, dSel);
                    System.out.println();
                    System.out.println("[OK] Ticket creado con ID: " + t.getId());
                    presionaEnter(sc);
                    break;
                }
                case "10": {
                    List<Ticket> tickets = ticketManager.listar();
                    if (tickets.isEmpty()) {
                        System.out.println("No hay tickets registrados.");
                        presionaEnter(sc);
                        break;
                    }

                    System.out.println();
                    System.out.println("=== LISTA DE TICKETS ===");
                    for (int i = 0; i < tickets.size(); i++) {
                        Ticket t = tickets.get(i);
                        System.out.println((i + 1) + ") " + t.getAsunto()
                                + " - " + t.getUsuario().getNombreCompleto()
                                + " (Estado: " + t.getEstado() + ")");
                    }
                    System.out.print("Seleccione un ticket para analizar (0 = cancelar): ");
                    int idx;
                    try {
                        idx = Integer.parseInt(sc.nextLine()) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Opción inválida.");
                        presionaEnter(sc);
                        break;
                    }
                    if (idx == -1) {
                        break; // cancelado
                    }
                    if (idx < 0 || idx >= tickets.size()) {
                        System.out.println("Ticket inválido.");
                        presionaEnter(sc);
                        break;
                    }

                    Ticket tSel = tickets.get(idx);
                    Map<String, Object> resultados = bowAnalyzer.analizarTicket(tSel);

                    System.out.println();
                    System.out.println("===== RESULTADO BoW =====");
                    System.out.println("Ticket ID: " + tSel.getId());
                    System.out.println("Asunto: " + tSel.getAsunto());
                    System.out.println("Descripción: " + tSel.getDescripcion());
                    System.out.println("------------------------------------");
                    System.out.println("Clasificación técnica: " + resultados.get("ClasificacionTecnica"));
                    System.out.println("Score técnico: " + resultados.get("ScoreTecnico"));
                    System.out.println("Detalles técnicos: " + resultados.get("DetallesTecnicos"));
                    System.out.println("------------------------------------");
                    System.out.println("Estado de ánimo: " + resultados.get("EstadoDeAnimo"));
                    System.out.println("Score emocional: " + resultados.get("ScoreEmocional"));
                    System.out.println("Detalles emocionales: " + resultados.get("DetallesEmocionales"));

                    presionaEnter(sc);
                    break;
                }
                case "11": {
                    List<Ticket> tickets = ticketManager.listar();
                    if (tickets.isEmpty()) {
                        System.out.println("No hay tickets registrados.");
                        presionaEnter(sc);
                        break;
                    }

                    System.out.println();
                    System.out.println("=== CAMBIAR ESTADO DE TICKET ===");
                    for (int i = 0; i < tickets.size(); i++) {
                        Ticket t = tickets.get(i);
                        System.out.println((i + 1) + ") " + t.getAsunto()
                                + " (Estado actual: " + t.getEstado() + ")");
                    }
                    System.out.print("Seleccione un ticket (0 = cancelar): ");
                    int idx;
                    try {
                        idx = Integer.parseInt(sc.nextLine()) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Opción inválida.");
                        presionaEnter(sc);
                        break;
                    }
                    if (idx == -1) {
                        break; // cancelado
                    }
                    if (idx < 0 || idx >= tickets.size()) {
                        System.out.println("Ticket inválido.");
                        presionaEnter(sc);
                        break;
                    }

                    Ticket tSel = tickets.get(idx);
                    System.out.println("Estado actual: " + tSel.getEstado());
                    System.out.print("Nuevo estado (ej: nuevo / en proceso / resuelto): ");
                    String nuevoEstado = sc.nextLine();
                    try {
                        tSel.setEstado(nuevoEstado);
                        System.out.println("[OK] Estado actualizado.");
                    } catch (Exception e) {
                        System.out.println("Error al cambiar estado: " + e.getMessage());
                    }
                    presionaEnter(sc);
                    break;
                }
                case "0": {
                    System.out.println("Saliendo...");
                    salir = true;
                    break;
                }
                default: {
                    System.out.println("Opción inválida.");
                    presionaEnter(sc);
                    break;
                }
            }
        }

        sc.close();
    }
}