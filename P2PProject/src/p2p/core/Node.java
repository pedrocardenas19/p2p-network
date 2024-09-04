package p2p.core;

import p2p.rpc.RPCServer;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private String id;  // Identificador del nodo
    private RPCServer rpcServer;  // Servidor RPC asociado al nodo

    // Definir un mapa para simular la DHT
    private Map<String, String> dht;

    public Node(String id) {
        this.id = id;
        this.rpcServer = new RPCServer(this);  // Inicializar el servidor RPC del nodo
        this.dht = new HashMap<>(); // Inicializar el mapa para almacenar pares clave-valor
    }

    // Método para obtener el ID del nodo
    public String getId() {
        return id;
    }

    // Método para obtener el servidor RPC asociado al nodo
    public RPCServer getRpcServer() {
        return rpcServer;
    }

    public void start() {
        rpcServer.start(); // Inicia el servidor RPC
        System.out.println("Nodo " + id + " iniciado en el puerto " + rpcServer.getPort());
    }

    public void stop() {
        rpcServer.shutdown(); // Detiene el servidor RPC
        System.out.println("Nodo " + id + " detenido.");
    }

    // Método para manejar PUT y almacenar el valor en la DHT
    public String handlePut(String key, String value) {
        dht.put(key, value); // Almacena el valor en la DHT
        return "STORED";     // Retorna un mensaje de éxito
    }

    // Método para manejar GET y recuperar el valor desde la DHT
    public String handleGet(String key) {
        String result = dht.get(key); // Recupera el valor de la DHT
        if (result != null) {
            return result;
        } else {
            return "NOT_FOUND"; // Si la clave no existe, retorna "NOT_FOUND"
        }
    }
}
