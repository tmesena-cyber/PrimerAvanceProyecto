package cr.ac.ucenfotec.ui;

import cr.ac.ucenfotec.bl.*;

import java.util.ArrayList;

public class Controller {

    private static RegistroUsuarios registroUsuarios = new RegistroUsuarios();
    private static DiccionarioTecnico diccionario = new DiccionarioTecnico();

    public static void procesarSeleccionPrincipal(byte opcion) throws Exception {

        switch (opcion) {
            case 1 -> Menu.menuUsuarios();
            case 2 -> Menu.menuDiccionario();
            case 0 -> System.out.println("Saliendo del sistema...");
            default -> System.out.println("La opción seleccionada no es válida.");
        }
    }

    // USUARIOS
    public static void registrarUsuario(String n, String c, String t, String r) {
        try {
            Usuario u = registroUsuarios.registrar(n, c, t, r);
            System.out.println("Usuario registrado correctamente:");
            u.imprimirInfo();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void listarUsuarios() {
        ArrayList<Usuario> lista = registroUsuarios.listar();

        if (lista.isEmpty()) {
            System.out.println("No existen usuarios registrados.");
        } else {
            for (Usuario u : lista) {
                System.out.println("-----------------------------");
                u.imprimirInfo();
            }
        }
    }

    // DICCIONARIO
    public static void listarPalabrasTecnicas() {
        ArrayList<PalabraTecnica> lista = diccionario.listar();

        System.out.println("\nPalabras registradas en el diccionario:");
        for (PalabraTecnica p : lista) {
            System.out.println("-----------------------------");
            p.imprimirInfo();
        }
    }

    public static void buscarPalabraTecnica(String palabra) {
        PalabraTecnica p = diccionario.buscar(palabra);

        if (p == null) {
            System.out.println("La palabra \"" + palabra + "\" no existe en el diccionario.");
        } else {
            System.out.println("Palabra: " + p.getPalabra());
            System.out.println("Categoría: " + p.getCategoria());
        }
    }
}
