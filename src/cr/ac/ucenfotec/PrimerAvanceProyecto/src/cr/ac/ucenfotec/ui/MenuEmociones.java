package cr.ac.ucenfotec.ui;

import java.util.*;
import cr.ac.ucenfotec.bl.PalabraEmocional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuEmociones {

    private final PalabraEmocional emocionalManager;
    private final Scanner sc;

    public MenuEmociones(PalabraEmocional emocionalManager, Scanner sc) {
        this.emocionalManager = emocionalManager;
        this.sc = sc;
    }

    private void presionaEnter() {
        System.out.println("\n(Pulsa ENTER para continuar)");
        sc.nextLine();
    }

    public void mostrarMenu() {
        int opcion = -1;

        while (opcion != 9) {
            System.out.println("\n===== MENÚ PALABRA EMOCIONAL =====");
            System.out.println("1. Listar emociones");
            System.out.println("2. Listar palabras de una emoción");
            System.out.println("3. Agregar palabra a emoción");
            System.out.println("4. Agregar nueva emoción (con palabra de ejemplo)");
            System.out.println("5. Actualizar nombre de emoción");
            System.out.println("6. Actualizar palabras de emoción (Reemplazar lista)");
            System.out.println("7. Eliminar palabra de emoción");
            System.out.println("8. Eliminar emoción completa");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                String input = sc.nextLine().trim();
                if (input.isEmpty()) continue;
                opcion = Integer.parseInt(input);

                switch (opcion) {
                    case 1 -> listarEmociones();
                    case 2 -> listarPalabrasEmocion();
                    case 3 -> agregarPalabraAEmocion();
                    case 4 -> agregarNuevaEmocion();
                    case 5 -> actualizarNombreEmocion();
                    case 6 -> actualizarPalabrasDeEmocion();
                    case 7 -> eliminarPalabra();
                    case 8 -> eliminarEmocion();
                    case 9 -> System.out.println("Saliendo de la gestión de emociones...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Debe ingresar un número.");
            } catch (Exception e) {
                System.out.println("XXXXXXXXXX Error: " + e.getMessage());
            }
            if (opcion != 9) presionaEnter();
        }
    }

    // ===================================
    // LÓGICA DE MENÚ
    // ===================================

    private void listarEmociones() {
        System.out.println("\n--- LISTA DE EMOCIONES ---");
        List<String> emociones = emocionalManager.listarEmocionesOrdenadas();
        if (emociones.isEmpty()) {
            System.out.println("No hay emociones registradas.");
            return;
        }
        for (String e : emociones) {
            System.out.println("- " + e);
        }
    }

    private void listarPalabrasEmocion() {
        System.out.print("Ingrese emoción a listar: ");
        String emocion = sc.nextLine().trim();

        List<String> palabras = emocionalManager.obtenerPalabras(emocion);
        System.out.println("\n--- PALABRAS DE '" + emocion.toUpperCase() + "' ---");
        if (palabras.isEmpty()) {
            System.out.println("Emoción no encontrada o sin palabras.");
            return;
        }
        for (String p : palabras) {
            System.out.println("- " + p);
        }
    }

    private void agregarPalabraAEmocion() {
        System.out.print("Ingrese la palabra a añadir: ");
        String palabra = sc.nextLine();
        System.out.print("Ingrese la emoción (Ej: FRUSTRACION): ");
        String emocion = sc.nextLine();

        PalabraEmocional p = emocionalManager.registrar(palabra, emocion);
        System.out.println("\n [OK] Palabra '" + p.getPalabra() + "' agregada a emoción '" + p.getEmocion() + "'.");
    }

    private void agregarNuevaEmocion() {
        System.out.print("Ingrese el nombre de la nueva emoción: ");
        String emocion = sc.nextLine();
        System.out.print("Ingrese una palabra de ejemplo para la emoción: ");
        String palabra = sc.nextLine();

        // Se usa el mismo método registrar; si la emoción es nueva, se crea.
        PalabraEmocional p = emocionalManager.registrar(palabra, emocion);
        System.out.println("\n [OK] Nueva emoción '" + p.getEmocion() + "' registrada con palabra: '" + p.getPalabra() + "'.");
    }

    private void actualizarNombreEmocion() {
        System.out.print("Ingrese el nombre de la emoción a actualizar: ");
        String original = sc.nextLine();
        System.out.print("Ingrese el nuevo nombre: ");
        String nuevo = sc.nextLine();

        if (emocionalManager.actualizarNombreEmocion(original, nuevo)) {
            System.out.println("\n [OK] Emoción actualizada de '" + original.toUpperCase() + "' a '" + nuevo.toUpperCase() + "'.");
        } else {
            System.out.println("Error: Emoción '" + original.toUpperCase() + "' no encontrada.");
        }
    }

    private void actualizarPalabrasDeEmocion() {
        System.out.print("Ingrese la emoción a actualizar (Se reemplazarán todas las palabras): ");
        String emocion = sc.nextLine();
        System.out.print("Ingrese las NUEVAS palabras separadas por coma (ej: palabra1,palabra2,palabra3): ");
        String[] palabrasArray = sc.nextLine().split(",");
        List<String> palabras = new ArrayList<>();
        for (String p : palabrasArray) {
            if (!p.trim().isEmpty()) palabras.add(p.trim());
        }

        if (emocionalManager.actualizarPalabras(emocion, palabras)) {
            System.out.println("\n [OK] Palabras de '" + emocion.toUpperCase() + "' actualizadas exitosamente.");
        } else {
            System.out.println("Advertencia: No se actualizaron palabras. La emoción no existe o la lista ingresada estaba vacía.");
        }
    }

    public void eliminarPalabra() {
        System.out.print("Ingrese la emoción de la palabra a eliminar: ");
        String emocion = sc.nextLine().trim();

        List<String> palabrasDisponibles = emocionalManager.obtenerPalabras(emocion);
        if (palabrasDisponibles.isEmpty()) {
            System.out.println("Emoción no existe o no tiene palabras.");
            return;
        }

        System.out.print("Ingrese palabra a eliminar: ");
        String palabra = sc.nextLine().trim();

        if (emocionalManager.eliminarPalabra(emocion, palabra)) {
            System.out.println("\n [OK] Palabra eliminada exitosamente.");
        } else {
            System.out.println("Palabra no encontrada en la emoción '" + emocion.toUpperCase() + "'.");
        }
    }

    public void eliminarEmocion() {
        System.out.print("Ingrese emoción a eliminar: ");
        String emocion = sc.nextLine().trim();

        if (emocionalManager.eliminarEmocion(emocion)) {
            System.out.println("\n [OK] Emoción '" + emocion.toUpperCase() + "' y todas sus palabras eliminadas exitosamente.");
        } else {
            System.out.println("Emoción no encontrada.");
        }
    }
}