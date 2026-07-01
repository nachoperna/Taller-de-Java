package ejercicio2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class AnalizadorArchivos implements Callable<Map<String, Integer>> {
    private final Path rutaArchivo;

    public AnalizadorArchivos(Path rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public Map<String, Integer> call() throws IOException {
        Map<String, Integer> frecuenciasLocales = new HashMap<>();

        try (var lineas = Files.lines(rutaArchivo)) {
            lineas.forEach(linea -> {
                //Mantenemos la misma lógica que el problema 1 del primer TP
                for (String palabra : linea.split("[^a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+")) {
                    if (palabra.isEmpty()) {
                        continue;
                    }
                    String minuscula = palabra.toLowerCase();
                    frecuenciasLocales.merge(minuscula, 1, Integer::sum);
                }
            });
        }

        return frecuenciasLocales;
    }
}
