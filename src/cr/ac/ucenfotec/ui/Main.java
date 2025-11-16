package cr.ac.ucenfotec.ui;

public class Main {
    public static void main(String[] args) {
        try {
            Menu.menuPrincipal();
        } catch (Exception e) {
            System.out.println("Error general en la aplicación: " + e.getMessage());
        }
    }
}
