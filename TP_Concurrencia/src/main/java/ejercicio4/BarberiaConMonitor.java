package ejercicio4;

public class BarberiaConMonitor implements Barberia {

    private final int maxSillas;
    private int cantEspera;
    private int cortesCompletados;
    private boolean cerrado;

    public BarberiaConMonitor(int maxSillas) {
        this.maxSillas = maxSillas;
        this.cantEspera = 0;
        this.cortesCompletados = 0;
        this.cerrado = false;
    }

    @Override
    public synchronized boolean llegaCliente() throws InterruptedException {
        if (cerrado) return false;
        if (cantEspera >= maxSillas) {
            return false;
        }

        int miCorte = cortesCompletados;
        cantEspera++;
        notifyAll();

        // Espera hasta que el barbero complete su corte (avance el contador)
        while (cortesCompletados == miCorte) {
            wait();
        }
        return true;
    }

    @Override
    public synchronized void trabajaBarbero() throws InterruptedException {
        while (!cerrado) {
            while (cantEspera == 0 && !cerrado) {
                wait();
            }
            if (cerrado) return;

            cantEspera--;
            cortesCompletados++;
            notifyAll();
        }
    }

    @Override
    public synchronized void cerrar() {
        cerrado = true;
        notifyAll();
    }
}
