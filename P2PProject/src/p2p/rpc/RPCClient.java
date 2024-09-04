package p2p.rpc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class RPCClient {
    // Método para enviar una solicitud RPC a otro nodo
    public String sendRequest(String host, int port, String request) {
        try (Socket socket = new Socket(host, port)) { // Conecta al nodo remoto
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true); // Para enviar la solicitud
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Para leer la respuesta

            out.println(request); // Envía la solicitud
            return in.readLine(); // Retorna la respuesta del nodo remoto
        } catch (Exception e) {
            e.printStackTrace(); // Manejo básico de excepciones
            return null;
        }
    }
}
