package ejercicio4;

public class Barbero implements Runnable {

    private final Barberia barberia;

    public Barbero(Barberia barberia) {
        this.barberia = barberia;
    }

    @Override
    public void run() {
        try {
            barberia.trabajaBarbero();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
