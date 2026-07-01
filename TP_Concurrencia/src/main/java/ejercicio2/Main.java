package ejercicio2;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//Recibe una lista de rutas a archivos de texto y los analiza en paralelo
public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Error: Debe indicar al menos un archivo de texto.");
            System.exit(1);
        }

        List<Path> archivos = Arrays.stream(args).map(Path::of).toList();

        ContadorPalabrasConcurrente contador = new ContadorPalabrasConcurrente();
        FrecuenciaPalabras resultado = contador.contarPalabras(archivos);

        Map<String, Integer> frecuencias = resultado.obtenerFrecuencias();

        System.out.println("Total de palabras distintas: " + frecuencias.size());

        frecuencias.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
    }
}
