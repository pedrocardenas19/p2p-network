package p2p.core;

import p2p.dht.DHT;
import p2p.rpc.RPCServer;

public class Node {
    private String id; // Identificador único del nodo
    private DHT dht; // Instancia de la tabla de hash distribuida
    private RPCServer rpcServer; // Servidor RPC para manejar solicitudes entrantes

    // Constructor que inicializa el nodo con un ID y crea la DHT y el servidor RPC
    public Node(String id) {
        this.id = id;
        this.dht = new DHT();
        this.rpcServer = new RPCServer(this);
    }

    // Métodos para obtener el ID, la DHT y el servidor RPC
    public String getId() {
        return id;
    }

    public DHT getDht() {
        return dht;
    }

    public RPCServer getRpcServer() {
        return rpcServer;
    }

    // Método para iniciar el servidor RPC
    public void start() {
        rpcServer.start();
    }
}
