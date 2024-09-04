package p2p.core;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AdminPanel {

    private static final String NODES_FILE = "active_nodes.txt"; // Archivo para almacenar los IDs de nodos

    public void start() {
        System.out.println("Bienvenido al Panel de Administración de la Red P2P (Solo Visualización)");
        String command;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nComandos disponibles:");
            System.out.println("1. list_nodes - Listar todos los nodos");
            System.out.println("2. exit - Salir del panel de administración");

            System.out.print("\nIngresa un comando: ");
            command = scanner.nextLine().trim();

            if (command.equals("list_nodes")) {
                try {
                    listNodes();
                } catch (IOException e) {
                    System.out.println("Error al leer la lista de nodos: " + e.getMessage());
                }
            } else if (command.equals("exit")) {
                System.out.println("Saliendo del panel de administración...");
                break;
            } else {
                System.out.println("Comando no reconocido. Intenta nuevamente.");
            }
        }
        scanner.close();
    }

    // Método para listar los nodos activos desde el archivo
    private void listNodes() throws IOException {
        File file = new File(NODES_FILE);
        if (!file.exists()) {
            System.out.println("No hay nodos activos en la red.");
            return;
        }

        Scanner fileScanner = new Scanner(file);
        System.out.println("Nodos activos en la red:");
        while (fileScanner.hasNextLine()) {
            String nodeId = fileScanner.nextLine();
            System.out.println("Nodo ID: " + nodeId);
        }
        fileScanner.close();
    }

    public static void main(String[] args) {
        AdminPanel adminPanel = new AdminPanel();
        adminPanel.start();
    }
}
