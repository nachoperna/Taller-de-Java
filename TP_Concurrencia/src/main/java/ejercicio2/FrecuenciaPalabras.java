package ejercicio2;

import java.util.Collections;
import java.util.Map;

public class FrecuenciaPalabras {
    private final Map<String, Integer> frecuencias;
    
    public FrecuenciaPalabras(Map<String, Integer> frecuencias) {
        //Al ser compartido entre hilos evitamos race conditions haciendolo inmutable
        this.frecuencias = Collections.unmodifiableMap(frecuencias);
    }

    public Map<String, Integer> obtenerFrecuencias() {
        return frecuencias;
    }
}
