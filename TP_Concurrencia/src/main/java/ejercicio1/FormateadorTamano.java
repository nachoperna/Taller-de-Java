package ejercicio1;

import java.util.Locale;

public final class FormateadorTamano {

    private static final String[] UNIDADES = {"bytes", "Kb", "Mb", "Gb"};
    private static final long[] UMBRALES = {
            1L,                   //bytes
            1024L,                //Kb
            1024L * 1024L,        //Mb
            1024L * 1024L * 1024L //Gb
    };

    //Formatea bytes en una cadena legible
    public static String formatear(long bytes) {
        if (bytes < 0) {
            throw new IllegalArgumentException("Conteo de bytes negativo: " + bytes);
        }

        if (bytes < 1024) {
            return bytes + " bytes";
        }

        //Encuentra la unidad más grande (empieza desde Gb hacia abajo)
        int indiceUnidad = 0;
        for (int i = UMBRALES.length - 1; i >= 1; i--) {
            if (bytes >= UMBRALES[i]) {
                indiceUnidad = i;
                break;
            }
        }

        long bytesUnidad = UMBRALES[indiceUnidad];

        //resultado entero
        if (bytes % bytesUnidad == 0) {
            return (bytes / bytesUnidad) + " " + UNIDADES[indiceUnidad];
        }

        //Formatea con hasta 3 dígitos significativos
        double valor = (double) bytes / bytesUnidad;
        String formateado;

        if (valor >= 100) {
            formateado = String.format(Locale.US, "%.0f", valor);
        } else if (valor >= 10) {
            formateado = String.format(Locale.US, "%.1f", valor);
        } else {
            formateado = String.format(Locale.US, "%.2f", valor);
            //Eliminamos ceros finales después del punto decimal
            if (formateado.indexOf('.') >= 0) {
                formateado = formateado.replaceAll("0*$", "").replaceAll("\\.$", "");
            }
        }

        return formateado + " " + UNIDADES[indiceUnidad];
    }
}
