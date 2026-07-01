package ejercicio4;

public class Cliente implements Runnable {

    private final int id;
    private final Barberia barberia;

    public Cliente(int id, Barberia barberia) {
        this.id = id;
        this.barberia = barberia;
    }

    @Override
    public void run() {
        try {
            boolean atendido = barberia.llegaCliente();
            if (atendido) {
                System.out.println("Cliente " + id + " recibió corte");
            } else {
                System.out.println("Cliente " + id + " se fue (sin sillas)");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
