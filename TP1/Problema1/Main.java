import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;

public class Main {
      public static ArrayList<String> entradas = new ArrayList<>();
      public static int longitudes[] = {4, 5, 6};
      public static void main(String[] args) {
            setEntradas();
            for (int N: longitudes) {
                  for (String entrada : entradas) {
                        System.out.println("\n N = " + N);
                        System.out.println("Entrada: " + entrada);
                        /*
                              Quitamos todos los caracteres especiales, espacios y numeros de la entrada usando una expresion regular 
                              quedandonos solamente con las palabras dentro de un arreglo, y nos parecio mas conveniente directamente 
                              eliminar de la entrada todo lo que NO sea una letra del alfabeto
                         */ 
                        String[] palabras = entrada.split("[^a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+"); 
                        Palabrero palabrero = new Palabrero(palabras, N);
                        Entry<String, Integer> mas_usada = palabrero.getMasUsada();
                        System.out.println("Mas usada: " + mas_usada.getKey() + " - " + mas_usada.getValue() + " veces");
                        palabrero.getMasUsada();
                  }
            }
      }

      public static void setEntradas(){
            String entrada1 = new String("Esta primera entrada deberia tener la palabra palabra como palabra mas usada");
            String entrada2 = new String("Esta-segunda-entrada-deberia-tener-la-palabra-entrada-como-la-mas-usada");
            String entrada3 = new String("Esta1tercer2entrada3esta4mas5corta6y7esta8deberia9ser10la11palabra12mas13usada");
            entradas.addAll(Arrays.asList(entrada1, entrada2, entrada3));
      }
      
}
