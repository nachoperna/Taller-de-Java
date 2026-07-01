package ejercicio2;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//Procesa múltiples archivos de forma concurrente y agrega las frecuencias de palabras en un único resultado
public class ContadorPalabrasConcurrente {

    public FrecuenciaPalabras contarPalabras(List<java.nio.file.Path> rutasArchivos) throws IOException {
        int tamPool = Math.min(rutasArchivos.size(), Runtime.getRuntime().availableProcessors());
        ExecutorService ejecutor = Executors.newFixedThreadPool(tamPool);

        try {
            //usamos un AnalizadorArchivos por cada archivo a contabilizar
            List<Future<Map<String, Integer>>> futuros = rutasArchivos.stream()
                    .map(ruta -> ejecutor.submit(new AnalizadorArchivos(ruta)))
                    .toList();

            Map<String, Integer> frecuenciasGlobales = new HashMap<>();

            for (Future<Map<String, Integer>> futuro : futuros) {
                try {
                    Map<String, Integer> mapaLocal = futuro.get();
                    for (Map.Entry<String, Integer> entrada : mapaLocal.entrySet()) {
                        frecuenciasGlobales.merge(entrada.getKey(), entrada.getValue(), Integer::sum);
                    }
                } catch (ExecutionException e) {
                    Throwable causa = e.getCause();
                    if (causa instanceof IOException ioException) {
                        throw ioException;
                    }
                    throw new IOException("Error inesperado durante el procesamiento", causa);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Procesamiento interrumpido", e);
                }
            }
            return new FrecuenciaPalabras(frecuenciasGlobales);
        } finally {
            ejecutor.shutdown();
        }
    }
}
