package p2p.core;

import java.io.IOException;

public class NodeProcess {
    private String nodeId;
    private Process process;

    public NodeProcess(String nodeId) throws IOException {
        this.nodeId = nodeId;
        // El proceso ya se ha iniciado en AdminPanel
    }

    public NodeProcess(String nodeId, Process process) {
        this.nodeId = nodeId;
        this.process = process;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public void stop() {
        if (process != null) {
            process.destroy();
            System.out.println("Proceso del nodo " + nodeId + " detenido.");
        } else {
            System.out.println("No se pudo detener el proceso del nodo " + nodeId + " porque no está asignado.");
        }
    }
}
