package p2p.rpc;

import p2p.core.Node;

import java.io.*;
import java.net.Socket;

public class RPCHandler extends Thread {
    private Socket clientSocket;
    private Node node;

    public RPCHandler(Socket clientSocket, Node node) {
        this.clientSocket = clientSocket;
        this.node = node;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Lee la solicitud (ej. PUT key value o GET key)
            String request = in.readLine();
            String[] tokens = request.split(" ");
            String command = tokens[0];

            if ("PUT".equals(command)) {
                String key = tokens[1];
                String value = tokens[2];
                String response = node.handlePut(key, value); // Llama al método handlePut del nodo
                out.println(response); // Responde con "STORED"
            } else if ("GET".equals(command)) {
                String key = tokens[1];
                String response = node.handleGet(key); // Llama al método handleGet del nodo
                out.println(response); // Responde con el valor o "NOT_FOUND"
            } else {
                out.println("UNKNOWN_COMMAND");
            }

            clientSocket.close(); // Cierra la conexión con el cliente
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
