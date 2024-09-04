package p2p.rpc;

import p2p.core.Node;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer extends Thread {
    private Node node;
    private ServerSocket serverSocket;

    public RPCServer(Node node) {
        this.node = node;
        try {
            // Inicializa el ServerSocket aquí, en lugar de en el método run
            this.serverSocket = new ServerSocket(0); // Usa un puerto disponible automáticamente
            System.out.println("Node " + node.getId() + " listening on port " + this.serverSocket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // Mueve el mensaje de impresión aquí para evitar problemas de sincronización
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new RPCHandler(clientSocket, node).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }
}
