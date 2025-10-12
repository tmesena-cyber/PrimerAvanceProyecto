package cr.ac.ucenfotec.ui;

import java.util.Scanner;
import cr.ac.ucenfotec.bl.PalabraTecnica;
import cr.ac.ucenfotec.bl.Usuario;

public class Main {

    // Esperar a que el usuario presione ENTER
    private static void presionaEnter(Scanner sc) {
        System.out.println("\n(Pulsa ENTER para continuar)");
        sc.nextLine();
    }

    public static void main(String[] args) {
        Usuario usuarios = new Usuario();
        PalabraTecnica diccionario = new PalabraTecnica();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    MENÚ PRINCIPAL (Consola)
                    1) Registrar Usuario
                    2) Listar Usuarios
                    3) Registrar Palabra Técnica
                    4) Listar Palabras Técnicas
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
                        System.out.print("Palabra técnica: ");
                        String palabra = sc.nextLine();
                        System.out.print("Categoría (REDES/IMPRESORAS/CUENTAS/HARDWARE/SOFTWARE/OTROS): ");
                        String categoria = sc.nextLine();

                        var p = diccionario.registrar(palabra, categoria);
                        System.out.println("Palabra técnica creada:");
                        p.imprimirInfo();

                        presionaEnter(sc);
                        break;
                    }
                    case "4": {
                        System.out.println("Diccionario Técnico");
                        diccionario.listar().forEach(p -> {
                            p.imprimirInfo();
                            System.out.println("-----------------------------");
                        });

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
