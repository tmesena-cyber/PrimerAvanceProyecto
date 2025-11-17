package cr.ac.ucenfotec.ui;

import java.util.*;
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

    /**
     * Metodo genérico para listar objetos con un índice.
     */
    private static <T> void listarConIndices(List<T> lista, String titulo) {
        System.out.println("\n===== LISTA DE " + titulo.toUpperCase() + " =====");
        if (lista.isEmpty()) {
            System.out.println("[Vacío] No hay " + titulo.toLowerCase() + " registrados.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            // Usamos +1 para que el índice de la consola inicie en 1
            System.out.print((i + 1) + ") ");
            // Se imprime la información según el tipo de objeto
            T item = lista.get(i);
            if (item instanceof Departamento) {
                ((Departamento) item).imprimirInfo();
            } else if (item instanceof Usuario) {
                Usuario u = (Usuario) item;
                System.out.println(String.format("Nombre: %s | Correo: %s | Rol: %s", u.getNombreCompleto(), u.getCorreo(), u.getRol()));
            } else if (item instanceof Ticket) {
                Ticket t = (Ticket) item;
                // Muestra solo los primeros 8 caracteres del ID para brevedad
                System.out.println(String.format("ID: %s | Asunto: %s | Estado: %s", t.getId().substring(0, 8), t.getAsunto(), t.getEstado()));
            } else {
                System.out.println(item); // Usa toString() por defecto
            }
        }
        System.out.println("=====================================");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Inicialización de Managers (Capa BL con delegación a la DAL)
        Usuario usuarioManager = new Usuario();
        Departamento departamentoManager = new Departamento();
        PalabraEmocional diccionarioEmocional = new PalabraEmocional();
        PalabraTecnica tecnicaManager = new PalabraTecnica();
        Ticket ticketManager = new Ticket();

        // Inicialización de herramientas de la BL
        BoWAnalyzer bowAnalyzer = new BoWAnalyzer(diccionarioEmocional, tecnicaManager);

        // Inicialización de un sub-menú para gestión de emociones
        MenuEmociones menuEmociones = new MenuEmociones(diccionarioEmocional, sc);

        boolean salir = false;

        while (!salir) {
            // FIX: Reemplazo de Text Block con System.out.println estándar por compatibilidad
            System.out.println("\n=====================================");
            System.out.println("      MENÚ PRINCIPAL (Consola)");
            System.out.println("=====================================");
            System.out.println("1) Registrar Usuario");
            System.out.println("2) Listar Usuarios");
            System.out.println("3) Registrar Departamento");
            System.out.println("4) Listar Departamentos");
            System.out.println("5) Registrar Palabra Técnica");
            System.out.println("6) Listar Palabras Técnicas");
            System.out.println("7) Registrar Palabra Emocional"); // Se mantiene por si se desea registro individual
            System.out.println("8) Gestión de Palabras Emocionales"); // Menú de gestión completo
            System.out.println("9) Registrar Ticket");
            System.out.println("10) Actualizar Estado de Ticket");
            System.out.println("11) Analizar Ticket Existente");
            System.out.println("0) Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = sc.nextLine().trim();

            try {
                switch (opcion) {
                    case "1": { // Registrar Usuario
                        System.out.println("\n--- REGISTRAR USUARIO ---");
                        System.out.print("Nombre Completo: ");
                        String nombre = sc.nextLine();
                        System.out.print("Correo: ");
                        String correo = sc.nextLine();
                        System.out.print("Teléfono (opcional): ");
                        String telefono = sc.nextLine();
                        System.out.print("Rol (CLIENTE o SOPORTE): ");
                        String rol = sc.nextLine();

                        Usuario u = usuarioManager.registrar(nombre, correo, telefono, rol);
                        System.out.println("\n [OK] Usuario registrado: " + u.getNombreCompleto());
                        presionaEnter(sc);
                        break;
                    }

                    case "2": { // Listar Usuarios
                        listarConIndices(usuarioManager.listar(), "Usuarios");
                        presionaEnter(sc);
                        break;
                    }

                    case "3": { // Registrar Departamento
                        System.out.println("\n--- REGISTRAR DEPARTAMENTO ---");
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        System.out.print("Funciones: ");
                        String funciones = sc.nextLine();
                        System.out.print("Correo (opcional): ");
                        String correo = sc.nextLine();
                        System.out.print("Extensión telefónica (0 para omitir): ");

                        // Uso de Integer.parseInt dentro del try-catch general
                        int extension = Integer.parseInt(sc.nextLine());

                        Departamento d = departamentoManager.registrar(nombre, funciones, correo, extension);
                        System.out.println("\n [OK] Departamento registrado: " + d.getNombre());
                        presionaEnter(sc);
                        break;
                    }

                    case "4": { // Listar Departamentos
                        List<Departamento> departamentos = departamentoManager.listar();
                        listarConIndices(departamentos, "Departamentos");
                        presionaEnter(sc);
                        break;
                    }

                    case "5": { // Registrar Palabra Técnica
                        System.out.println("\n--- REGISTRAR PALABRA TÉCNICA ---");
                        System.out.print("Palabra o Frase Clave: ");
                        String palabra = sc.nextLine();
                        System.out.print("Categoría Técnica (ej: REDES, SISTEMAS, SEGURIDAD): ");
                        String categoria = sc.nextLine();

                        tecnicaManager.registrar(palabra, categoria);
                        System.out.println("\n [OK] Palabra técnica registrada: " + palabra);
                        presionaEnter(sc);
                        break;
                    }

                    case "6": { // Listar Palabras Técnicas
                        System.out.println("\n--- DICCIONARIO TÉCNICO ---");
                        // Asumiendo que PalabraTecnica tiene un método listar() que retorna List<PalabraTecnica>
                        tecnicaManager.listar().forEach(p -> System.out.println(p.toString()));
                        presionaEnter(sc);
                        break;
                    }

                    case "7": { // Registrar Palabra Emocional
                        System.out.println("\n--- REGISTRAR PALABRA EMOCIONAL ---");
                        System.out.print("Palabra: ");
                        String palabra = sc.nextLine();
                        System.out.print("Emoción (ej: FRUSTRACIÓN, URGENCIA, ENOJO): ");
                        String emocion = sc.nextLine();

                        diccionarioEmocional.registrar(palabra, emocion);
                        System.out.println("\n [OK] Palabra emocional registrada: " + palabra);
                        presionaEnter(sc);
                        break;
                    }

                    case "8": { // Gestión de Palabras Emocionales
                        menuEmociones.mostrarMenu(); // Llama al menú de gestión
                        presionaEnter(sc);
                        break;
                    }

                    case "9": { // Registrar Ticket
                        List<Usuario> usuarios = usuarioManager.listar();
                        List<Departamento> departamentos = departamentoManager.listar();

                        if (usuarios.isEmpty() || departamentos.isEmpty()) {
                            System.out.println("\n [ADVERTENCIA] Debe haber al menos un usuario y un departamento registrados para crear un ticket.");
                            presionaEnter(sc);
                            break;
                        }

                        // --- Selección de Usuario ---
                        Usuario usuarioSeleccionado;
                        int userIdx = -1;
                        listarConIndices(usuarios, "Usuarios (CLIENTE)");
                        System.out.print("Ingrese el NÚMERO del usuario que reporta (1-" + usuarios.size() + ", 0 para cancelar): ");

                        // FIX: Se envuelve la lectura en un try-catch anidado para dar un feedback más específico.
                        try {
                            userIdx = Integer.parseInt(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Opción inválida. Debe ingresar un número.");
                            presionaEnter(sc);
                            break;
                        }

                        if (userIdx == 0) break; // Cancelar
                        userIdx--; // Conversión a índice 0-based

                        if (userIdx < 0 || userIdx >= usuarios.size()) {
                            System.out.println("Número de usuario inválido.");
                            presionaEnter(sc);
                            break;
                        }
                        usuarioSeleccionado = usuarios.get(userIdx);
                        System.out.println("Usuario seleccionado: " + usuarioSeleccionado.getNombreCompleto());

                        // --- Selección de Departamento ---
                        Departamento departamentoSeleccionado;
                        int deptIdx = -1;
                        listarConIndices(departamentos, "Departamentos de ASIGNACIÓN");
                        System.out.print("Ingrese el NÚMERO del departamento de destino (1-" + departamentos.size() + ", 0 para cancelar): ");

                        try {
                            deptIdx = Integer.parseInt(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Opción inválida. Debe ingresar un número.");
                            presionaEnter(sc);
                            break;
                        }

                        if (deptIdx == 0) break; // Cancelar
                        deptIdx--; // Conversión a índice 0-based

                        if (deptIdx < 0 || deptIdx >= departamentos.size()) {
                            System.out.println("Número de departamento inválido.");
                            presionaEnter(sc);
                            break;
                        }
                        departamentoSeleccionado = departamentos.get(deptIdx);
                        System.out.println("Departamento seleccionado: " + departamentoSeleccionado.getNombre());


                        // --- Registro de Ticket ---
                        System.out.println("\n--- DATOS DEL TICKET ---");
                        System.out.print("Asunto del Ticket: ");
                        String asunto = sc.nextLine();
                        System.out.print("Descripción Detallada: ");
                        String descripcion = sc.nextLine();

                        Ticket t = ticketManager.registrar(asunto, descripcion, usuarioSeleccionado, departamentoSeleccionado);
                        System.out.println("\n [OK] Ticket ID " + t.getId().substring(0, 8) + " registrado con estado: " + t.getEstado());

                        // --- Análisis BoW ---
                        System.out.println("\n--- ANÁLISIS AUTOMÁTICO (BoW) ---");
                        Map<String, Object> resultados = bowAnalyzer.analizarTicket(t);

                        System.out.println("Clasificación Técnica: " + resultados.getOrDefault("ClasificacionTecnica", "N/A"));
                        System.out.println("Score Técnico: " + resultados.getOrDefault("ScoreTecnico", 0));
                        System.out.println("Estado de Ánimo Detectado: " + resultados.getOrDefault("EstadoDeAnimo", "Neutral"));
                        System.out.println("Score Emocional: " + resultados.getOrDefault("ScoreEmocional", 0));

                        presionaEnter(sc);
                        break;
                    }

                    case "10": { // Actualizar Estado de Ticket
                        List<Ticket> tickets = ticketManager.listar();
                        if (tickets.isEmpty()) {
                            System.out.println("\n[Vacío] No hay tickets para actualizar.");
                            presionaEnter(sc);
                            break;
                        }

                        listarConIndices(tickets, "Tickets");

                        System.out.print("Ingrese el NÚMERO del ticket a actualizar (0 para cancelar): ");
                        int idx = -1;
                        try {
                            // Intenta leer el número y convertir a 0-based index
                            idx = Integer.parseInt(sc.nextLine()) - 1;
                        } catch (NumberFormatException e) {
                            System.out.println("Opción inválida. Debe ingresar un número.");
                            presionaEnter(sc);
                            break;
                        }

                        if (idx == -1) { // 0 para cancelar
                            break;
                        }

                        if (idx < 0 || idx >= tickets.size()) {
                            System.out.println("Número de ticket inválido.");
                            presionaEnter(sc);
                            break;
                        }

                        Ticket tSel = tickets.get(idx);
                        System.out.println("\nSeleccionado: Ticket ID " + tSel.getId().substring(0, 8) + " - Asunto: " + tSel.getAsunto());
                        System.out.println("Estado actual: " + tSel.getEstado());
                        System.out.print("Nuevo estado (ej: NUEVO / ASIGNADO / EN_PROCESO / CERRADO): ");
                        String nuevoEstado = sc.nextLine();

                        // Se llama al manager para actualizar el estado
                        ticketManager.actualizarEstado(tSel, nuevoEstado);

                        System.out.println("\n [OK] Estado del Ticket ID " + tSel.getId().substring(0, 8) + " actualizado a: " + tSel.getEstado() + "\n");

                        presionaEnter(sc);
                        break;
                    }

                    case "11": { // Analizar Ticket existente
                        List<Ticket> tickets = ticketManager.listar();
                        if (tickets.isEmpty()) {
                            System.out.println("\n[Vacío] No hay tickets para analizar.");
                            presionaEnter(sc);
                            break;
                        }

                        listarConIndices(tickets, "Tickets");

                        System.out.print("Ingrese el NÚMERO del ticket a analizar (0 para cancelar): ");
                        int idx = -1;
                        try {
                            idx = Integer.parseInt(sc.nextLine()) - 1;
                        } catch (NumberFormatException e) {
                            System.out.println("Opción inválida. Debe ingresar un número.");
                            presionaEnter(sc);
                            break;
                        }

                        if (idx == -1) { // 0 para cancelar
                            break;
                        }

                        if (idx < 0 || idx >= tickets.size()) {
                            System.out.println("Número de ticket inválido.");
                            presionaEnter(sc);
                            break;
                        }

                        Ticket tSel = tickets.get(idx);
                        System.out.println("\n--- ANÁLISIS AUTOMÁTICO (BoW) del Ticket ID " + tSel.getId().substring(0, 8) + " ---");
                        Map<String, Object> resultados = bowAnalyzer.analizarTicket(tSel);

                        System.out.println("Clasificación Técnica: " + resultados.getOrDefault("ClasificacionTecnica", "N/A"));
                        System.out.println("Score Técnico: " + resultados.getOrDefault("ScoreTecnico", 0));

                        // Se utiliza el @SuppressWarnings para evitar la advertencia de casting no verificado
                        @SuppressWarnings("unchecked")
                        Map<String, Integer> detallesTecnicos = (Map<String, Integer>) resultados.getOrDefault("DetallesTecnicos", Collections.emptyMap());
                        if (!detallesTecnicos.isEmpty()) {
                            System.out.println("Detalles Técnicos (Palabra: Conteo): " + detallesTecnicos);
                        }

                        System.out.println("\nEstado de Ánimo Detectado: " + resultados.getOrDefault("EstadoDeAnimo", "Neutral"));
                        System.out.println("Score Emocional: " + resultados.getOrDefault("ScoreEmocional", 0));

                        @SuppressWarnings("unchecked")
                        Map<String, Integer> detallesEmocionales = (Map<String, Integer>) resultados.getOrDefault("DetallesEmocionales", Collections.emptyMap());
                        if (!detallesEmocionales.isEmpty()) {
                            System.out.println("Detalles Emocionales (Palabra: Conteo): " + detallesEmocionales);
                        }

                        presionaEnter(sc);
                        break;
                    }

                    case "0": { // Salir
                        System.out.println("Adiós");
                        salir = true;
                        break;
                    }

                    default: { // Opción inválida
                        System.out.println("Opción inválida.");
                        presionaEnter(sc);
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                // Captura si se ingresa texto donde se esperaba un número fuera del switch (ej: en extension de Dept)
                System.out.println(" XXXXXXXXXX Error: Se esperaba un número. Mensaje: " + e.getMessage());
                presionaEnter(sc);
            } catch (Exception e) {
                // Captura excepciones de validación de negocio (ej: IllegalStateException, IllegalArgumentException)
                System.out.println(" XXXXXXXXXX Error de Lógica de Negocio: " + e.getMessage());
                presionaEnter(sc);
            }
        }

        sc.close();
    }
}