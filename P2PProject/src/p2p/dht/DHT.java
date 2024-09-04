package p2p.dht;

import java.util.HashMap;
import java.util.Map;

public class DHT {
    private Map<String, String> storage;

    public DHT() {
        this.storage = new HashMap<>(); // Inicializa un mapa para almacenar pares clave-valor
    }

    // Método para almacenar un valor en la DHT
    public void put(String key, String value) {
        storage.put(key, value); // Almacena el valor asociado a la clave
    }

    // Método para recuperar un valor de la DHT usando una clave
    public String get(String key) {
        return storage.get(key); // Devuelve el valor asociado a la clave o null si no existe
    }

    // Método para eliminar un valor de la DHT usando una clave
    public void remove(String key) {
        storage.remove(key); // Elimina el valor asociado a la clave
    }
}
