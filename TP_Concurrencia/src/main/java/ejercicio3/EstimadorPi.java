package ejercicio3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

//Clase para calcular pi ya sea single thread o multi thread
public class EstimadorPi {
    private final int cantHilos;
    
    public enum Modo {
        UNICO,
        MULTI
    }

    public EstimadorPi() {
        this(Runtime.getRuntime().availableProcessors());
    }

    //Definimos la cantidad de hilos a usar en el constructor
    public EstimadorPi(int cantHilos) {
        if (cantHilos < 1) {
            throw new IllegalArgumentException(
                    "cantHilos debe ser >= 1, pero se recibió: " + cantHilos);
        }
        this.cantHilos = cantHilos;
    }

    public double estimar(long totalPruebas) {
        return estimar(totalPruebas, Modo.MULTI);
    }

    public double estimar(long totalPruebas, Modo modo) {
        lanzarSiInvalido(totalPruebas);
        long enCirculo = switch (modo) {
            case UNICO -> estimarUnHilo(totalPruebas);
            case MULTI -> estimarMultiHilo(totalPruebas);
        };
        return 4.0 * enCirculo / totalPruebas;
    }

    private void lanzarSiInvalido(long totalPruebas) {
        if (totalPruebas <= 0) {
            throw new IllegalArgumentException(
                    "totalPruebas debe ser positivo, pero se recibió: " + totalPruebas);
        }
    }

    private long estimarUnHilo(long totalPruebas) {
        long enCirculo = 0L;
        for (long i = 0L; i < totalPruebas; i++) {
            double x = ThreadLocalRandom.current().nextDouble();
            double y = ThreadLocalRandom.current().nextDouble();
            if (x * x + y * y < 1.0) {
                enCirculo++;
            }
        }
        return enCirculo;
    }

    private long estimarMultiHilo(long totalPruebas) {
        int cantTrabajadores = totalPruebas < cantHilos
                ? (int) totalPruebas
                : cantHilos;

        long tamLote = totalPruebas / cantTrabajadores;
        long resto = totalPruebas % cantTrabajadores;

        List<Callable<Long>> trabajadores = new ArrayList<>();
        for (int i = 0; i < cantTrabajadores; i++) {
            final long pruebasPorTrabajador = tamLote + (i == cantTrabajadores - 1 ? resto : 0L);
            trabajadores.add(() -> {
                long conteo = 0L;
                for (long j = 0L; j < pruebasPorTrabajador; j++) {
                    double x = ThreadLocalRandom.current().nextDouble();
                    double y = ThreadLocalRandom.current().nextDouble();
                    if (x * x + y * y < 1.0) {
                        conteo++;
                    }
                }
                return conteo;
            });
        }

        ExecutorService ejecutor = Executors.newFixedThreadPool(cantTrabajadores);
        try {
            long totalEnCirculo = 0L;
            for (var future : ejecutor.invokeAll(trabajadores)) {
                totalEnCirculo += future.get();
            }
            return totalEnCirculo;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("La computación fue interrumpida", e);
        } catch (ExecutionException e) {
            Throwable causa = e.getCause();
            throw new RuntimeException("Error en un worker", causa != null ? causa : e);
        } finally {
            ejecutor.shutdown();
        }
    }
}
