import java.util.Scanner;

public class Main {
      public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            StringBuilder entrada = new StringBuilder();
            String linea;
            while (!(linea = sc.nextLine()).isEmpty()){
                  entrada.append(linea + " ");
            }
            sc.close();

            String[] palabras = entrada.toString().split("[^a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+");

            Palabrero palabrero = new Palabrero(palabras, 3);
            palabrero.getMasUsada();
      }
}
