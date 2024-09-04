package p2p.rpc;

import p2p.core.Node;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer extends Thread {
    private Node node;             // Referencia al nodo que utiliza este servidor
    private ServerSocket serverSocket;

    public RPCServer(Node node) {
        this.node = node;
        try {
            this.serverSocket = new ServerSocket(0); // Inicia el servidor en un puerto disponible automáticamente
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Servidor RPC del nodo " + node.getId() + " escuchando en el puerto " + serverSocket.getLocalPort());
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();  // Acepta conexiones de otros nodos
                new RPCHandler(clientSocket, node).start();    // Maneja cada solicitud en un hilo separado
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return serverSocket.getLocalPort(); // Devuelve el puerto en el que está escuchando el servidor
    }

    public void shutdown() {
        try {
            serverSocket.close();  // Detiene el servidor cerrando el socket
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
