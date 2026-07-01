package ejercicio1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class TareaTamDir extends RecursiveTask<Long> {

    private final File directorio;
    private final int profundidad;
    private final List<TareaTamDir> subTareas = new ArrayList<>();
    private long tamTotal;

    public TareaTamDir(File directorio, int profundidad) {
        this.directorio = directorio;
        this.profundidad = profundidad;
    }

    @Override
    protected Long compute() {
        return calcular();
    }

    public long calcular() {
        File[] hijos = directorio.listFiles();

        //Si el directorio no puede leerse, se trata como vacío
        if (hijos == null) {
            tamTotal = 0L;
            return 0L;
        }

        List<File> archivos = new ArrayList<>();
        List<File> subdirectorios = new ArrayList<>();

        for (File hijo : hijos) {
            if (hijo.isFile()) {
                archivos.add(hijo);
            } else if (hijo.isDirectory()) {
                subdirectorios.add(hijo);
            }
        }

        long total = 0L;
        for (File archivo : archivos) {
            total += archivo.length();
        }

        //Construimos subtareas para subdirectorios
        for (File subdir : subdirectorios) {
            subTareas.add(new TareaTamDir(subdir, profundidad + 1));
        }

        //Ejecutamos todas as tareas en paralelo y unimos
        for (TareaTamDir tarea : subTareas) {
            tarea.fork();
        }
        for (TareaTamDir tarea : subTareas) {
            total += tarea.join();
        }

        tamTotal = total;
        return total;
    }

    public void imprimirReporte() {
        String indentacion = "  ".repeat(profundidad);
        System.out.println(indentacion + directorio.getPath() + " (" + FormateadorTamano.formatear(tamTotal) + ")");
        for (TareaTamDir hijo : subTareas) {
            hijo.imprimirReporte();
        }
    }
}
