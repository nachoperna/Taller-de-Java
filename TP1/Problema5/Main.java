import java.util.ArrayList;
import java.util.List;

public class Main {

      public static void main(String[] args) {
            List<Caso> casos = new ArrayList<>();
            initCasos(casos);
            int i = 1;
            for (Caso caso : casos) {
                  System.out.println("CASO " + i + ": " + caso.cantPasos());
                  i++;
            }
      }

      public static void initCasos(List<Caso> casos) {
            casos.add(new Caso(new Character[][]{
                  {'E', '.', '.'},
                  {'.', '.', '.'},
                  {'.', '.', 'S'}
            }, 3, 3));
            casos.add(new Caso(new Character[][]{
                  {'E', '#', '.'},
                  {'.', '#', '.'},
                  {'.', '#', 'S'}
            }, 3, 3));
            casos.add(new Caso(new Character[][]{
                  {'E', '.', 'S'},
                  {'.', '.', '.'},
                  {'.', '.', 'S'}
            }, 3, 3));
            casos.add(new Caso(new Character[][]{
                  {'S', '.', 'b', '#' , 'b'},
                  {'#', '#', '#', '#' , 'a'},
                  {'.', '.', 'E', '#' , '#'},
                  {'c', '#', '#', '.' , 'c'},
                  {'#', 'a', '.', '.' , '.'},
            }, 5, 5));
      }
}
