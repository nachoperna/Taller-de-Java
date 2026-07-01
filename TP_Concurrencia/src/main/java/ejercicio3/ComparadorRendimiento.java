package ejercicio3;

public class ComparadorRendimiento {

    private final EstimadorPi estimador;

    /** Crea un comparador con un {@link EstimadorPi} por defecto. */
    public ComparadorRendimiento() {
        this(new EstimadorPi());
    }

    public ComparadorRendimiento(EstimadorPi estimador) {
        this.estimador = estimador;
    }

    public void comparar(long totalPruebas) {
        //Unico thread
        long t1 = System.nanoTime();
        double estimacionUnico = estimador.estimar(totalPruebas, EstimadorPi.Modo.UNICO);
        long t2 = System.nanoTime();
        double tiempoUnicoSeg = (t2 - t1) / 1_000_000_000.0;

        //Multi thread
        long t3 = System.nanoTime();
        double estimacionMulti = estimador.estimar(totalPruebas, EstimadorPi.Modo.MULTI);
        long t4 = System.nanoTime();
        double tiempoMultiSeg = (t4 - t3) / 1_000_000_000.0;

        //Aceleración
        double aceleracion = tiempoMultiSeg > 0.0 ? tiempoUnicoSeg / tiempoMultiSeg : 0.0;

        System.out.printf("Modo un hilo:     %.4f s%n", tiempoUnicoSeg);
        System.out.printf("Modo multihilo:    %.4f s%n", tiempoMultiSeg);
        System.out.printf("Ratio de speedup:  %.2f%n", aceleracion);
        System.out.printf("π estimado (1 hilo):  %.6f%n", estimacionUnico);
        System.out.printf("π estimado (%d hilos): %.6f%n",
                Runtime.getRuntime().availableProcessors(), estimacionMulti);
    }

    public static void main(String[] args) {
        long pruebas = 1_000_000_000L;
        if (args.length > 0) {
            pruebas = Integer.parseInt(args[0]);
        }
        new ComparadorRendimiento().comparar(pruebas);
    }
}
