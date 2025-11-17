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

    // Esperar a que el usuario presione ENTER
    private static void presionaEnter(Scanner sc) {
        System.out.println("\n(Pulsa ENTER para continuar)");
        sc.nextLine();
    }

    public static void main(String[] args) {
        Usuario usuarios = new Usuario();
        PalabraTecnica diccionarioTecnico = new PalabraTecnica();
        Departamento departamentos = new Departamento();
        PalabraEmocional diccionarioEmocional = new PalabraEmocional();
        Ticket ticketsManager = new Ticket();
        BoWAnalyzer boWAnalyzer = new BoWAnalyzer(diccionarioEmocional, diccionarioTecnico);
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
            String op = sc.nextLine().trim();

            try {
                switch (op) {

                    case "1": {
                        System.out.print("Nombre completo: ");
                        String nombre = sc.nextLine();
                        System.out.print("Correo: ");
                        String correo = sc.nextLine();
                        System.out.print("Teléfono (opcional): ");
                        String tel = sc.nextLine();
                        System.out.print("Rol (ADMIN/ESTUDIANTE/FUNCIONARIO): ");
                        String rol = sc.nextLine();

                        var u = usuarios.registrar(nombre, correo, tel, rol);
                        System.out.println("\n Usuario creado :) = \n");

                        u.imprimirInfo();

                        presionaEnter(sc);
                        break;
                    }
                    case "2": {
                        System.out.println("Usuarios");
                        usuarios.listar().forEach(u -> {
                            u.imprimirInfo();
                            System.out.println("-----------------------------");
                        });

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

                        int extension = 0; // valor por defecto si no escribe nada
                        if (!extInput.isBlank()) {  // solo intentamos convertir si escribió algo
                            try {
                                extension = Integer.parseInt(extInput);
                            } catch (NumberFormatException e) {
                                System.out.println("Error: El número de extensión debe ser un entero.");
                                presionaEnter(sc);
                                return; // o manejar como quieras
                            }
                        }

                        var d = departamentos.registrar(nombre, funciones, correo, extension);
                        System.out.println("\n Departamento creado :) = \n");

                        d.imprimirInfo();

                        presionaEnter(sc);
                        break;
                    }
                    case "4": {
                        System.out.println("Departamentos");
                        departamentos.listar().forEach(d -> {
                            d.imprimirInfo();
                            System.out.println("-----------------------------");
                        });

                        presionaEnter(sc);
                        break;
                    }
                    case "5": {
                        System.out.print("Palabra técnica: ");
                        String palabra = sc.nextLine();
                        System.out.print("Categoría (REDES/IMPRESORAS/CUENTAS/HARDWARE/SOFTWARE/OTROS): ");
                        String categoria = sc.nextLine();

                        var p = diccionarioTecnico.registrar(palabra, categoria);
                        System.out.println("Palabra técnica creada:");
                        p.imprimirInfo();

                        presionaEnter(sc);
                        break;
                    }
                    case "6": {
                        System.out.println("Diccionario Técnico");
                        diccionarioTecnico.listar().forEach(p -> {
                            p.imprimirInfo();
                            System.out.println("-----------------------------");
                        });

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

                        presionaEnter(sc);
                        break;
                    }

                    case "10": {
                        List<Ticket> listaTickets = ticketsManager.listar();

                        if (listaTickets.isEmpty()) {
                            System.out.println("(!) No hay tickets registrados para analizar.");
                            presionaEnter(sc);
                            break;
                        }

                        System.out.println("\n--- LISTA Y ANÁLISIS DE TICKETS ---");
                        for (int i = 0; i < listaTickets.size(); i++) {
                            Ticket t = listaTickets.get(i);
                            System.out.println((i + 1) + ") " + t.getAsunto() + " - de " + t.getUsuario().getNombreCompleto());
                        }

                        System.out.print("Seleccione el número de ticket para analizar (0 para cancelar): ");
                        int ticketIndex = Integer.parseInt(sc.nextLine()) - 1;

                        if (ticketIndex >= 0 && ticketIndex < listaTickets.size()) {
                            Ticket ticketSeleccionado = listaTickets.get(ticketIndex);

                            // EJECUTAR EL ANÁLISIS BoW
                            Map<String, Object> resultados = boWAnalyzer.analizarTicket(ticketSeleccionado);

                            System.out.println("\n=======================================================");
                            System.out.println("RESULTADOS DEL ANÁLISIS BAG OF WORDS (BoW)");
                            System.out.println("=======================================================");
                            System.out.println("Ticket ID: " + ticketSeleccionado.getId());
                            System.out.println("Asunto: " + ticketSeleccionado.getAsunto());
                            System.out.println("Descripción Original: " + ticketSeleccionado.getDescripcion());
                            System.out.println("-------------------------------------------------------");
                            System.out.println(">> CLASIFICACIÓN TÉCNICA: " + resultados.get("ClasificacionTecnica"));
                            System.out.println("Puntuación Técnica: " + resultados.get("ScoreTecnico"));
                            System.out.println("-------------------------------------------------------");
                            System.out.println(">> ESTADO DE ÁNIMO: " + resultados.get("EstadoDeAnimo"));
                            System.out.println("Puntuación Emocional: " + resultados.get("ScoreEmocional"));
                            System.out.println("=======================================================");
                            System.out.println("Detalles Técnicos (Palabras contadas): " + resultados.get("DetallesTecnicos"));
                            System.out.println("Detalles Emocionales (Palabras contadas): " + resultados.get("DetallesEmocionales"));

                        } else if (ticketIndex != -1) {
                            System.out.println("Número de ticket inválido o cancelado.");
                        }

                        presionaEnter(sc);
                        break;
                    }
                    case "0": {
                        System.out.println("Adiós");
                        return;
                    }
                    default: {
                        System.out.println("Opción inválida.");
                        presionaEnter(sc);
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(" XXXXXXXXXX Error: " + e.getMessage());
                presionaEnter(sc);
            }
        }
    }
}