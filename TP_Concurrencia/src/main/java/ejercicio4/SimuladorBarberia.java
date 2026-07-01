package ejercicio4;

import java.util.ArrayList;
import java.util.List;

public class SimuladorBarberia {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Simulación con Monitores ===");
        ejecutarSimulacion(new BarberiaConMonitor(3), 10);

        System.out.println("\n=== Simulación con Lock/Condition ===");
        ejecutarSimulacion(new BarberiaConBloqueo(3), 10);
    }

    private static void ejecutarSimulacion(Barberia barberia, int numClientes) throws InterruptedException {
        Thread barbero = new Thread(new Barbero(barberia));
        barbero.start();

        List<Thread> clientes = new ArrayList<>();
        for (int i = 1; i <= numClientes; i++) {
            clientes.add(new Thread(new Cliente(i, barberia)));
        }
        for (Thread c : clientes) {
            c.start();
            Thread.sleep(200);
        }
        for (Thread c : clientes) {
            c.join();
        }

        barberia.cerrar();
        barbero.join();
    }
}
