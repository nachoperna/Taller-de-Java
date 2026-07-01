package ejercicio1;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

//Clase principal, recibe como argumento la ruta a un directorio
public final class ReporteTamDir {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Error: Falta el argumento de ruta del directorio.");
            System.exit(1);
        }

        File directorio = new File(args[0]);

        if (!directorio.exists()) {
            System.err.println("Error: La ruta no existe: " + args[0]);
            System.exit(1);
        }

        if (!directorio.isDirectory()) {
            System.err.println("Error: La ruta no es un directorio: " + args[0]);
            System.exit(1);
        }

        TareaTamDir tareaRaiz = new TareaTamDir(directorio, 0);
        ForkJoinPool.commonPool().invoke(tareaRaiz);
        tareaRaiz.imprimirReporte();
    }
}
