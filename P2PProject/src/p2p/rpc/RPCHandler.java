package p2p.rpc;

import p2p.core.Node;

import java.io.*;
import java.net.Socket;

public class RPCHandler extends Thread {
    private Socket clientSocket; // Socket del cliente conectado
    private Node node; // Nodo asociado a este manejador

    // Constructor que inicializa el manejador con el socket del cliente y el nodo
    public RPCHandler(Socket clientSocket, Node node) {
        this.clientSocket = clientSocket;
        this.node = node;
    }

    // Método principal que procesa la solicitud del cliente
    @Override
    public void run() {
        try {
            // Streams para leer la solicitud del cliente y enviar una respuesta
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Lee la solicitud del cliente (una línea de texto)
            String request = in.readLine();
            String[] tokens = request.split(" "); // Divide la solicitud en palabras

            // Obtiene el comando y sus parámetros (clave y valor)
            String command = tokens[0];
            String key = tokens.length > 1 ? tokens[1] : null;
            String value = tokens.length > 2 ? tokens[2] : null;

            // Procesa el comando recibido
            switch (command) {
                case "PUT":
                    node.getDht().put(key, value); // Almacena el valor en la DHT del nodo
                    out.println("STORED"); // Responde al cliente
                    break;
                case "GET":
                    String result = node.getDht().get(key); // Recupera el valor de la DHT
                    out.println(result != null ? result : "NOT_FOUND"); // Responde con el valor o con "NOT_FOUND"
                    break;
                case "REMOVE":
                    node.getDht().remove(key); // Elimina el valor de la DHT
                    out.println("REMOVED"); // Responde al cliente
                    break;
                default:
                    out.println("UNKNOWN_COMMAND"); // Responde con un error si el comando no es reconocido
            }

            clientSocket.close(); // Cierra la conexión con el cliente
        } catch (IOException e) {
            e.printStackTrace(); // Manejo básico de excepciones
        }
    }
}
