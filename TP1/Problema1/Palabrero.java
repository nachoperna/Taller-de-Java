import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Palabrero {
      private Map<String, Integer> palabras;

      public Palabrero(String[] palabras_entrada, Integer N){
            /*
                  Utilizamos un HashMap porque queremos una estructura que no permita repetidos y podamos guardar en el mismo lugar
                  la palabra insertada con un valor numerico indicando la cantidad de veces que se encuentra en el texto de entrada
             */
            this.palabras = new HashMap<>();
            this.insertarPalabras(palabras_entrada, N);
      }

      private void insertarPalabras(String[] palabras, Integer N) {
            for (String palabra : palabras) {
                  /*
                        Controlamos en este punto que la palabra válida sea de la longitud dada porque para comprobarlo debemos iterar
                        por toda la lista de palabras y nos ahorramos dobles iteraciones. Si la palabra no cumple con la condición de longitud 
                        directamente no se inserta en el mapa y luego no es elegible para buscar la mas usada.
                  */
                  if (palabra.length() >= N){
                        Integer valor = this.palabras.get(palabra.toLowerCase()); // Buscamos en el mapa la palabra descartando mayusculas
                        if (valor != null){ // La palabra fue ingresada anteriormente
                              this.palabras.put(palabra.toLowerCase(), valor+1); // Significa que estamos leyendo una palabra repetida asique aumentamos su valor de repetición.
                        }else{
                              this.palabras.put(palabra.toLowerCase(), 1); // Es la primera vez que leemos la palabra asique la ingresamos con valor de repetición 1.
                        }
                  }
            }
      }

      public Entry<String, Integer> getMasUsada() {
            /*
                  Como estrategia para obtener la palabra mas usada tomamos todas las clave valor de nuestro mapa y
                  las ordenamos por orden descendente segun su valor, que en este caso es el numero de repeticiones de la palabra
                  en el texto de entrada, y finalmente nos quedamos solamente con la primera que será la de mayor repetición.
            */
            Entry<String, Integer> mas_usada = this.palabras.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).findFirst().get();
            return mas_usada;
      }
}
