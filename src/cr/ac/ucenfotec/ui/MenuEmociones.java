package cr.ac.ucenfotec.ui;

import java.util.*;
import cr.ac.ucenfotec.bl.PalabraEmocional;
import java.util.Scanner;

public class MenuEmociones {

    private final PalabraEmocional emocionalManager;
    private final Scanner sc;

    public MenuEmociones(PalabraEmocional emocionalManager, Scanner sc) {
        this.emocionalManager = emocionalManager;
        this.sc = sc;
    }

    public void mostrarMenu() {
        int opcion = -1;

        while (opcion != 9) {
            System.out.println("\n===== MENÚ PALABRA EMOCIONAL =====");
            System.out.println("1. Listar emociones");
            System.out.println("2. Listar palabras de una emoción");
            System.out.println("3. Agregar palabra a emoción");
            System.out.println("4. Agregar nueva emoción");
            System.out.println("5. Actualizar nombre de emoción");
            System.out.println("6. Actualizar palabras de emoción");
            System.out.println("7. Eliminar palabra de emoción");
            System.out.println("8. Eliminar emoción completa");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida.");
                continue;
            }

            switch (opcion) {
                case 1 -> listarEmociones();
                case 2 -> listarPalabras();
                case 3 -> agregarPalabra();
                case 4 -> agregarEmocion();
                case 5 -> actualizarNombreEmocion();
                case 6 -> actualizarPalabras();
                case 7 -> eliminarPalabra();
                case 8 -> eliminarEmocion();
                case 9 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    public void listarEmociones() {
        List<String> emociones = emocionalManager.listarEmocionesOrdenadas();
        System.out.println("\nEmociones disponibles:");
        for (String e : emociones) System.out.println("- " + e);
    }

    public void listarPalabras() {
        System.out.print("Ingrese emoción: ");
        String emocion = sc.nextLine().trim();
        List<String> palabras = emocionalManager.obtenerPalabras(emocion);
        if (palabras.isEmpty()) {
            System.out.println("No existe esa emoción o no tiene palabras.");
        } else {
            System.out.println("Palabras de '" + emocion + "': " + palabras);
        }
    }

    public void agregarPalabra() {
        System.out.print("Ingrese emoción: ");
        String emocion = sc.nextLine().trim();
        if (emocion.isEmpty()) {
            System.out.println("Emoción no puede estar vacía.");
            return;
        }

        System.out.print("Ingrese palabra a agregar: ");
        String palabra = sc.nextLine().trim();
        if (palabra.isEmpty()) {
            System.out.println("Palabra no puede estar vacía.");
            return;
        }

        if (emocionalManager.obtenerPalabras(emocion).contains(palabra)) {
            System.out.println("La palabra ya existe en esta emoción.");
        } else {
            emocionalManager.agregarPalabra(emocion, palabra);
            System.out.println("Palabra agregada exitosamente.");
        }
    }

    public void agregarEmocion() {
        System.out.print("Ingrese nueva emoción: ");
        String emocion = sc.nextLine().trim();
        if (emocion.isEmpty()) {
            System.out.println("Emoción no puede estar vacía.");
            return;
        }

        if (emocionalManager.existeEmocion(emocion)) {
            System.out.println("La emoción ya existe.");
            return;
        }

        System.out.print("Ingrese palabras separadas por coma: ");
        String[] palabrasArray = sc.nextLine().split(",");
        List<String> palabras = new ArrayList<>();
        for (String p : palabrasArray) {
            if (!p.trim().isEmpty()) palabras.add(p.trim());
        }

        emocionalManager.agregarEmocion(emocion, palabras);
        System.out.println("Emoción agregada exitosamente.");
    }

    public void actualizarNombreEmocion() {
        System.out.print("Ingrese nombre de emoción a actualizar: ");
        String vieja = sc.nextLine().trim();
        if (vieja.isEmpty() || emocionalManager.obtenerPalabras(vieja).isEmpty()) {
            System.out.println("La emoción no existe.");
            return;
        }

        System.out.print("Ingrese nuevo nombre de emoción: ");
        String nueva = sc.nextLine().trim();
        if (nueva.isEmpty()) {
            System.out.println("El nuevo nombre no puede estar vacío.");
            return;
        }

        if (emocionalManager.actualizarNombreEmocion(vieja, nueva)) {
            System.out.println("Nombre de emoción actualizado.");
        } else {
            System.out.println("Error al actualizar emoción.");
        }
    }

    public void actualizarPalabras() {
        System.out.print("Ingrese emoción a actualizar palabras: ");
        String emocion = sc.nextLine().trim();
        if (emocionalManager.existeEmocion(emocion)) {
            System.out.println("Emoción no existe.");
            return;
        }

        System.out.print("Ingrese nuevas palabras separadas por coma: ");
        String[] palabrasArray = sc.nextLine().split(",");
        List<String> palabras = new ArrayList<>();
        for (String p : palabrasArray) {
            if (!p.trim().isEmpty()) palabras.add(p.trim());
        }

        if (emocionalManager.actualizarPalabras(emocion, palabras)) {
            System.out.println("Palabras actualizadas exitosamente.");
        } else {
            System.out.println("Error al actualizar palabras.");
        }
    }

    public void eliminarPalabra() {
        System.out.print("Ingrese emoción: ");
        String emocion = sc.nextLine().trim();
        List<String> palabras = emocionalManager.obtenerPalabras(emocion);
        if (palabras.isEmpty()) {
            System.out.println("Emoción no existe o no tiene palabras.");
            return;
        }

        System.out.print("Ingrese palabra a eliminar: ");
        String palabra = sc.nextLine().trim();

        if (emocionalManager.eliminarPalabra(emocion, palabra)) {
            System.out.println("Palabra eliminada exitosamente.");
        } else {
            System.out.println("Palabra no encontrada.");
        }
    }

    public void eliminarEmocion() {
        System.out.print("Ingrese emoción a eliminar: ");
        String emocion = sc.nextLine().trim();

        if (emocionalManager.eliminarEmocion(emocion)) {
            System.out.println("Emoción eliminada exitosamente.");
        } else {
            System.out.println("Emoción no encontrada.");
        }
    }
}
