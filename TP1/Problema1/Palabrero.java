import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Palabrero {
      private Map<String, Integer> palabras;

      public Palabrero(String[] palabras_entrada, Integer N){
            this.palabras = new HashMap<>();
            this.insertarPalabras(palabras_entrada, N);
      }

      private void insertarPalabras(String[] palabras, Integer N) {
            for (String palabra : palabras) {
                  if (palabra.length() >= N){
                        Integer valor = this.palabras.get(palabra.toLowerCase());
                        if (valor != null){
                              this.palabras.put(palabra.toLowerCase(), valor+1);
                        }else{
                              this.palabras.put(palabra.toLowerCase(), 1);
                        }
                  }
            }
      }

      public void getMasUsada() {
            Entry<String, Integer> mas_usada = this.palabras.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).findFirst().get();
            System.out.println(mas_usada.getKey() + " - " + mas_usada.getValue());
      }
}
