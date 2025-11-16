package cr.ac.ucenfotec.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {

    private static BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));

    public static void menuPrincipal() throws Exception {

        byte opcion = -1;

        do {
            System.out.println("\n==================================");
            System.out.println("          SISTEMA DE SOPORTE      ");
            System.out.println("==================================");
            System.out.println("1) Gestión de Usuarios");
            System.out.println("2) Diccionario Técnico");
            System.out.println("0) Salir");
            System.out.println("----------------------------------");

            opcion = leerOpcion();

            Controller.procesarSeleccionPrincipal(opcion);

        } while (opcion != 0);
    }

    public static void menuUsuarios() throws IOException {

        byte opcion = -1;

        do {
            System.out.println("\n----------- GESTIÓN DE USUARIOS -----------");
            System.out.println("1) Registrar usuario");
            System.out.println("2) Listar usuarios");
            System.out.println("0) Volver al menú principal");
            System.out.println("-------------------------------------------");

            opcion = leerOpcion();

            switch (opcion) {
                case 1 -> {
                    System.out.print("Nombre completo: ");
                    String nombre = entrada.readLine();

                    System.out.print("Correo: ");
                    String correo = entrada.readLine();

                    System.out.print("Teléfono: ");
                    String tel = entrada.readLine();

                    System.out.print("Rol (ADMIN/ESTUDIANTE/FUNCIONARIO): ");
                    String rol = entrada.readLine();

                    Controller.registrarUsuario(nombre, correo, tel, rol);
                }
                case 2 -> Controller.listarUsuarios();
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("La opción indicada no es válida.");
            }

        } while (opcion != 0);
    }

    public static void menuDiccionario() throws IOException {

        byte opcion = -1;

        do {
            System.out.println("\n----------- DICCIONARIO TÉCNICO -----------");
            System.out.println("1) Listar palabras técnicas");
            System.out.println("2) Buscar palabra técnica");
            System.out.println("0) Volver al menú principal");
            System.out.println("-------------------------------------------");

            opcion = leerOpcion();

            switch (opcion) {
                case 1 -> Controller.listarPalabrasTecnicas();
                case 2 -> {
                    System.out.print("Ingrese la palabra a buscar: ");
                    String palabra = entrada.readLine();
                    Controller.buscarPalabraTecnica(palabra);
                }
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("La opción indicada no es válida.");
            }

        } while (opcion != 0);
    }

    private static byte leerOpcion() {
        byte opcion = -1;

        try {
            System.out.print("Seleccione opción: ");
            opcion = Byte.parseByte(entrada.readLine());
        } catch (Exception e) {
            System.out.println("Lo sentimos, sucedió un error. Intente de nuevo.");
        }
        return opcion;
    }
}
