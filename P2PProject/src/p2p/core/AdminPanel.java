package p2p.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import p2p.rpc.RPCClient;

public class AdminPanel {

    private List<Node> nodes;
    private Scanner scanner;

    public AdminPanel() {
        this.nodes = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Bienvenido al Panel de Administración de la Red P2P");
        String command;
        while (true) {
            System.out.println("\nComandos disponibles:");
            System.out.println("1. add_node <ID> - Agregar un nuevo nodo");
            System.out.println("2. list_nodes - Listar todos los nodos");
            System.out.println("3. put <nodeID> <key> <value> - Almacenar un valor en un nodo");
            System.out.println("4. get <nodeID> <key> - Recuperar un valor de un nodo");
            System.out.println("5. exit - Salir del panel de administración");

            System.out.print("\nIngresa un comando: ");
            command = scanner.nextLine().trim();

            if (command.startsWith("add_node")) {
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    addNode(parts[1]);
                } else {
                    System.out.println("Uso incorrecto del comando add_node. Uso correcto: add_node <ID>");
                }
            } else if (command.equals("list_nodes")) {
                listNodes();
            } else if (command.startsWith("put")) {
                String[] parts = command.split(" ");
                if (parts.length == 4) {
                    putValue(parts[1], parts[2], parts[3]);
                } else {
                    System.out.println("Uso incorrecto del comando put. Uso correcto: put <nodeID> <key> <value>");
                }
            } else if (command.startsWith("get")) {
                String[] parts = command.split(" ");
                if (parts.length == 3) {
                    getValue(parts[1], parts[2]);
                } else {
                    System.out.println("Uso incorrecto del comando get. Uso correcto: get <nodeID> <key>");
                }
            } else if (command.equals("exit")) {
                System.out.println("Saliendo del panel de administración...");
                break;
            } else {
                System.out.println("Comando no reconocido. Intenta nuevamente.");
            }
        }
    }

    private void addNode(String nodeId) {

        if (getNodeById(nodeId) != null) {
            System.out.println("El nodo " + nodeId + " ya existe.");
            return;
        }
        
        Node node = new Node(nodeId);
        node.start();
        nodes.add(node);
        System.out.println("Nodo " + nodeId + " agregado y escuchando en el puerto " + node.getRpcServer().getPort());
    }

    private void listNodes() {
        if (nodes.isEmpty()) {
            System.out.println("No hay nodos en la red.");
        } else {
            System.out.println("Nodos en la red:");
            for (Node node : nodes) {
                System.out.println("ID: " + node.getId() + ", Puerto: " + node.getRpcServer().getPort());
            }
        }
    }

    private void putValue(String nodeId, String key, String value) {
        Node node = getNodeById(nodeId);
        if (node != null) {
            RPCClient rpcClient = new RPCClient();
            String response = rpcClient.sendRequest("localhost", node.getRpcServer().getPort(), "PUT " + key + " " + value);
            System.out.println("Respuesta del nodo: " + response);
        } else {
            System.out.println("Nodo " + nodeId + " no encontrado.");
        }
    }

    private void getValue(String nodeId, String key) {
        Node node = getNodeById(nodeId);
        if (node != null) {
            RPCClient rpcClient = new RPCClient();
            String response = rpcClient.sendRequest("localhost", node.getRpcServer().getPort(), "GET " + key);
            System.out.println("Respuesta del nodo: " + response);
        } else {
            System.out.println("Nodo " + nodeId + " no encontrado.");
        }
    }

    private Node getNodeById(String nodeId) {
        for (Node node : nodes) {
            if (node.getId().equals(nodeId)) {
                return node;
            }
        }
        return null;
    }
}
