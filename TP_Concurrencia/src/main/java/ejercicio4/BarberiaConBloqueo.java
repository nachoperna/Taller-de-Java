package ejercicio4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BarberiaConBloqueo implements Barberia {

    private final int maxSillas;
    private int cantEspera;
    private int cortesCompletados;
    private boolean cerrado;
    private final ReentrantLock bloqueo;
    private final Condition condicion;

    public BarberiaConBloqueo(int maxSillas) {
        this.maxSillas = maxSillas;
        this.cantEspera = 0;
        this.cortesCompletados = 0;
        this.cerrado = false;
        this.bloqueo = new ReentrantLock();
        this.condicion = bloqueo.newCondition();
    }

    @Override
    public boolean llegaCliente() throws InterruptedException {
        bloqueo.lock();
        try {
            if (cerrado) return false;
            if (cantEspera >= maxSillas) {
                return false;
            }

            int miCorte = cortesCompletados;
            cantEspera++;
            condicion.signalAll();

            // Espera hasta que el barbero complete su corte (avance el contador)
            while (cortesCompletados == miCorte) {
                condicion.await();
            }
            return true;
        } finally {
            bloqueo.unlock();
        }
    }

    @Override
    public void trabajaBarbero() throws InterruptedException {
        bloqueo.lock();
        try {
            while (!cerrado) {
                while (cantEspera == 0 && !cerrado) {
                    condicion.await();
                }
                if (cerrado) return;

                cantEspera--;
                cortesCompletados++;
                condicion.signalAll();
            }
        } finally {
            bloqueo.unlock();
        }
    }

    @Override
    public void cerrar() {
        bloqueo.lock();
        try {
            cerrado = true;
            condicion.signalAll();
        } finally {
            bloqueo.unlock();
        }
    }
}
