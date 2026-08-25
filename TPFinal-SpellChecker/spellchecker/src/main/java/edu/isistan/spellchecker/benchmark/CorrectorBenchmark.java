package edu.isistan.spellchecker.benchmark;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import edu.isistan.spellchecker.corrector.Dictionary;
import edu.isistan.spellchecker.corrector.impl.Levenshtein;
import edu.isistan.spellchecker.corrector.impl.Levenshtein_LSH;

// Configuramos JMH para medir el tiempo promedio en milisegundos
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
// Guardamos el estado en memoria durante toda la prueba
@State(Scope.Benchmark)
@Fork(0)
// 2 iteraciones para "calentar" la JVM y 3 para medir realmente
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
public class CorrectorBenchmark {

    private Levenshtein correctorNormal;
    private Levenshtein_LSH correctorLSH;
    
    // La palabra que usaremos para estresar los correctores
    private String palabraPrueba = "benefecial"; 

    @Setup(Level.Trial)
    public void prepararEstado() throws IOException {
        // Todo esto ocurre FUERA del cronómetro
        Dictionary dict = Dictionary.make("dictionary.txt");
        
        correctorNormal = new Levenshtein(dict);
        correctorLSH = new Levenshtein_LSH(dict);
    }

    @Benchmark
    public Set<String> medirFuerzaBruta() {
        // Retornar el valor evita que Java elimine el código por considerarlo inútil
        return correctorNormal.getCorrections(palabraPrueba);
    }

    @Benchmark
    public Set<String> medirLSH() {
        return correctorLSH.getCorrections(palabraPrueba);
    }
}
