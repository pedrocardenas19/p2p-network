package p2p.dht;

import java.util.HashMap;
import java.util.Map;

public class DHT {
    // Un mapa para almacenar pares clave-valor
    private Map<String, String> storage;

    // Constructor que inicializa el almacenamiento de la DHT
    public DHT() {
        this.storage = new HashMap<>();
    }

    // Método para almacenar un valor en la DHT
    public void put(String key, String value) {
        storage.put(key, value);
    }

    // Método para recuperar un valor de la DHT usando su clave
    public String get(String key) {
        return storage.get(key);
    }

    // Método para eliminar un valor de la DHT usando su clave
    public void remove(String key) {
        storage.remove(key);
    }
}
