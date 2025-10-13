package cr.ac.ucenfotec.ui;

import java.util.Scanner;
import cr.ac.ucenfotec.bl.PalabraTecnica;
import cr.ac.ucenfotec.bl.Usuario;
import cr.ac.ucenfotec.bl.Departamento;
import cr.ac.ucenfotec.bl.PalabraEmocional;

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
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    MENÚ PRINCIPAL (Consola)
                    1) Registrar Usuario
                    2) Listar Usuarios
                    3) Registrar Departamento
                    4) Listar Departamentos
                    5) Registrar Palabra Técnica
                    6) Listar Palabras Técnicas
                    7) Registrar Palabras Emocionales
                    8) Listar Palabras Emocionales
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
                        System.out.print("Palabra Emocional: ");
                        String palabra = sc.nextLine();
                        System.out.print("Emoción: ");
                        String emocion = sc.nextLine();

                        var p = diccionarioEmocional.registrar(palabra, emocion);
                        System.out.println("\n Palabra emocional creada: \n");

                        p.imprimirInfo();

                        presionaEnter(sc);
                        break;
                    }
                    case "8": {
                        System.out.println("Diccionario Emocional");
                        diccionarioEmocional.listar().forEach(p -> {
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
