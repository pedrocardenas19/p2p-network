package p2p.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class NodeConsole {
    private Node node;
    private Scanner scanner;
    private static final String NODES_FILE = "active_nodes.txt"; // Archivo para almacenar los IDs de nodos

    public NodeConsole(String nodeId) throws IOException {
        if (nodeExists(nodeId)) {
            System.out.println("Error: Ya existe un nodo con el ID " + nodeId);
            System.exit(1);
        }
        
        this.node = new Node(nodeId);
        this.node.start();
        this.scanner = new Scanner(System.in);
        addNodeToRegistry(nodeId);
    }

    public void startConsole() {
        System.out.println("Nodo " + node.getId() + " iniciado en el puerto " + node.getRpcServer().getPort());
        String command;
        while (true) {
            System.out.println("\nComandos disponibles:");
            System.out.println("1. put <key> <value> - Almacenar un valor en la DHT");
            System.out.println("2. get <key> - Recuperar un valor de la DHT");
            System.out.println("3. exit - Salir de la consola del nodo");
    
            System.out.print("\nIngresa un comando: ");
            command = scanner.nextLine().trim();
    
            if (command.startsWith("put")) {
                String[] parts = command.split(" ", 3);
                if (parts.length == 3) {
                    putValue(parts[1], parts[2]);
                } else {
                    System.out.println("Uso incorrecto del comando put. Uso correcto: put <key> <value>");
                }
            } else if (command.startsWith("get")) {
                String[] parts = command.split(" ", 2);
                if (parts.length == 2) {
                    getValue(parts[1]);
                } else {
                    System.out.println("Uso incorrecto del comando get. Uso correcto: get <key>");
                }
            } else if (command.equals("exit")) {
                System.out.println("Cerrando la consola del nodo " + node.getId());
                node.stop();
                try {
                    removeNodeFromRegistry(node.getId());  // Envuelve la llamada en un try-catch
                } catch (IOException e) {
                    System.out.println("Error al eliminar el nodo del registro: " + e.getMessage());
                }
                break;
            } else {
                System.out.println("Comando no reconocido. Intenta nuevamente.");
            }
        }
    }
    

    private void putValue(String key, String value) {
        String response = node.handlePut(key, value);
        System.out.println("Respuesta del nodo: " + response);
    }

    private void getValue(String key) {
        String response = node.handleGet(key);
        System.out.println("Respuesta del nodo: " + response);
    }

    // Verifica si el nodo con el ID dado ya existe
    private boolean nodeExists(String nodeId) throws IOException {
        File file = new File(NODES_FILE);
        if (!file.exists()) {
            return false;
        }

        Scanner fileScanner = new Scanner(file);
        while (fileScanner.hasNextLine()) {
            String existingNodeId = fileScanner.nextLine();
            if (existingNodeId.equals(nodeId)) {
                fileScanner.close();
                return true;
            }
        }
        fileScanner.close();
        return false;
    }

    // Agrega el nodo al archivo de nodos activos
    private void addNodeToRegistry(String nodeId) throws IOException {
        FileWriter writer = new FileWriter(NODES_FILE, true);
        writer.write(nodeId + "\n");
        writer.close();
    }

    // Elimina el nodo del archivo de nodos activos
    private void removeNodeFromRegistry(String nodeId) throws IOException {
        File inputFile = new File(NODES_FILE);
        File tempFile = new File("temp_nodes.txt");

        Scanner reader = new Scanner(inputFile);
        FileWriter writer = new FileWriter(tempFile);

        while (reader.hasNextLine()) {
            String currentLine = reader.nextLine();
            if (!currentLine.trim().equals(nodeId)) {
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        }
        writer.close();
        reader.close();

        // Reemplazar el archivo original con el archivo temporal
        if (!inputFile.delete()) {
            System.out.println("Error al eliminar el archivo original.");
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Error al renombrar el archivo temporal.");
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java p2p.core.NodeConsole <NodeID>");
            System.exit(1);
        }

        try {
            NodeConsole nodeConsole = new NodeConsole(args[0]);
            nodeConsole.startConsole();
        } catch (IOException e) {
            System.out.println("Error al inicializar el nodo: " + e.getMessage());
        }
    }
}
