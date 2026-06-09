import java.util.ArrayList;
import java.util.ListIterator;

public class Caso {
      private Character[][] mapa;
      private int F;
      private int C;
      private record Tupla(Integer f, Integer c) { } // Se decidio crear un tipo record para almacenar siempre la tupla de 2 valores enteros correspondiente a la poosicion en el mapa, porque el tipo
                                                     // Map era de un acceso mas costoso y no tan util si solo necesitamos almacenar posiciones temporales y no son necesarias para la solucion final
      private int pasos_minimos;
      private Tupla[] pasos_validos;

      public Caso(Character[][] mapa, int F, int C){
            this.mapa = mapa;
            this.F = F;
            this.C = C;
            this.pasos_minimos = Integer.MAX_VALUE; // Discernible para ser reemplazada ante la primer solucion encontrada
            this.pasos_validos = new Tupla[]{ // Variable utilizada para calcular las nuevas celdas a las que nos podemos mover desde una posicion especifica
                  new Tupla(-1, 0),
                  new Tupla(0, 1),
                  new Tupla(1, 0),
                  new Tupla(0, -1)
            };
      }
      
      public int cantPasos(){
            Tupla entrada = getEntrada(); // Buscamos la entrada en el mapa, porque el enunciado no dice nada sobre si la entrada es un valor dado o no
            ArrayList<Tupla> celdas_visitadas = new ArrayList<>();
            encuentraSalida(entrada, entrada, 'E', 0, celdas_visitadas);
            if (pasos_minimos == Integer.MAX_VALUE) // Si la variable no cambio su valor inicial, entonces no encontramos una salida en el mapa
                  return -1;
            return pasos_minimos;
      }

      private Tupla getEntrada(){
            for (int i = 0; i < F; i++) {
                  for (int j = 0; j < C; j++) {
                        if (mapa[i][j] == 'E')
                              return new Tupla(i, j);
                  }
            }
            throw new RuntimeException("No se encontró entrada al mapa");
      }

      private void encuentraSalida(Tupla celda_actual, Tupla celda_anterior, char valor_celda, int pasos_actuales, ArrayList<Tupla> celdas_visitadas) {
            if (valor_celda == 'S' && pasos_actuales < pasos_minimos){ // Adoptamos un enfoque de backtracking donde solo actualizamos la mejor solucion si encontramos una salida y los pasos hechos
                                                                       // hasta el momento son menores que la ultima cantidad de pasos obtenidas
                  pasos_minimos = pasos_actuales;
            }else{
                  ArrayList<Tupla> movimientos_validos = getMovimientosValidos(celda_actual, celda_anterior, celdas_visitadas); // Obtenemos los lugares a los que nos podemos mover desde la posicion actual
                  ListIterator<Tupla> iterador = movimientos_validos.listIterator(); // Usamos un iterador para acceder a los movimientos validos porque necesitabamos una herramienta con la que se
                                                                                     // pueda recorrer mientras borramos un elemento de la lista
                  while (iterador.hasNext()){
                        Tupla celda = iterador.next();
                        pasos_actuales++;
                        celdas_visitadas.add(celda);
                        encuentraSalida(celda, celda_actual, mapa[celda.f][celda.c], pasos_actuales, celdas_visitadas); // Visitamos la celda valida para verificar la condicion de corte
                        pasos_actuales--; // Ya evaluamos el camino valido asique lo descartamos de nuestras posibilidades
                        iterador.remove();
                  }
            }
      }

      private ArrayList<Tupla> getMovimientosValidos(Tupla celda_actual, Tupla celda_anterior, ArrayList<Tupla> celdas_visitadas) {
            ArrayList<Tupla> aux = new ArrayList<>();

            if (esPortal(mapa[celda_actual.f][celda_actual.c], mapa[celda_anterior.f][celda_anterior.c])){ // Si estamos ante una celda que su valor es una letra distinta de la entrada y la salida,
                                                                                                           // significa que tenemos un portal y procedemos a encontrar el otro portal en el mapa
                  Tupla portal = encuentraPortal(celda_actual, mapa[celda_actual.f][celda_actual.c]);
                  if (portal == null) throw new RuntimeException("No se encontro el otro portal pretendido");
                  aux.add(portal); // Agregamos el portal como unico movimiento valido desde la posicion actual
                  return aux;
            }

            for (Tupla t : pasos_validos) { // Calculamos la posicion de los movimientos validos para movernos (norte, sur, este, oeste)
                  int f = celda_actual.f + t.f;
                  int c = celda_actual.c + t.c;
                  if (f >= 0 && c >= 0 && f < F && c < C && mapa[f][c] != '#'){ // Controlamos que el movimiento valido no salga de los limites ni sea una pared para agregarlo
                        Tupla celda = new Tupla(f, c);
                        if (!celdas_visitadas.contains(celda) && !celda_anterior.equals(celda)) // Controlamos que la celda no haya sido visitada previamente
                              aux.add(celda);
                  }
            }
            return aux;
      }

      private Tupla encuentraPortal(Tupla celda, char valor) {
            for (int i = 0; i < F; i++) {
                  for (int j = 0; j < C; j++) {
                        if (mapa[i][j] == valor && (i != celda.f || j != celda.c)){ // Buscamos el portal con la misma letra evitando encontrar el mismo por el que entramos
                              return new Tupla(i, j);
                        }
                  }
            }
            return null;
      }

      private boolean esPortal(char valor, char valor_anterior) {
            return valor != 'S' && valor != 'E' && valor != '#' && valor != '.' && valor != valor_anterior;
      }
}
