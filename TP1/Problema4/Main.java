import java.util.ArrayList;
import java.util.Arrays;

public class Main {

      public static void main(String[] args) {
            ArrayList<Caso> casos = new ArrayList<>();
            initCasos(casos);
            for (Caso caso : casos) { // Iteramos por todos los casos iniciales que tenemos, mostramos sus datos y los mandamos a calcular su cantidad de fotografias artisticas
                  System.out.printf("\nCaso A=%s X=%d Y=%d", Arrays.asList(caso.getArr()), caso.getX(), caso.getY());
                  System.out.println("\nSalida: " + caso.cantFotografiasArtisticas());
            }
      }

      public static void initCasos(ArrayList<Caso> casos) {
            /*
               Decidimos crear una clase para cada caso con todos los datos necesarios para que esten encapsaulados
               y cada uno tenga la responsabilidad de calcular su propia cantidad de fotografias artisticas
            */
            casos.add(new Caso(new Character[]{'a', 'f', 'a', 'e', 'a'}, 1, 2));
            casos.add(new Caso(new Character[]{'a', 'f', 'a', 'e', 'a'}, 2, 3));
            casos.add(new Caso(new Character[]{'.', 'f', 'e', 'a', 'a', 'f', '.', 'e'}, 1, 2));
      }
}
