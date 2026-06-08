import java.util.ArrayList;
import java.util.ListIterator;

public class Caso {
      private Character[][] mapa;
      private int F;
      private int C;
      private record Tupla(Integer f, Integer c) { }
      private int pasos_minimos;
      private Tupla[] pasos_validos;

      public Caso(Character[][] mapa, int F, int C){
            this.mapa = mapa;
            this.F = F;
            this.C = C;
            this.pasos_minimos = Integer.MAX_VALUE;
            this.pasos_validos = new Tupla[]{
                  new Tupla(-1, 0),
                  new Tupla(0, 1),
                  new Tupla(1, 0),
                  new Tupla(0, -1)
            };
      }
      
      public int cantPasos(){
            Tupla entrada = getEntrada();
            ArrayList<Tupla> celdas_visitadas = new ArrayList<>();
            encuentraSalida(entrada, entrada, 'E', 0, celdas_visitadas);
            if (pasos_minimos == Integer.MAX_VALUE)
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
            if (valor_celda == 'S' && pasos_actuales < pasos_minimos){
                  pasos_minimos = pasos_actuales;
            }else{
                  ArrayList<Tupla> movimientos_validos = getMovimientosValidos(celda_actual, celda_anterior, celdas_visitadas);
                  ListIterator<Tupla> iterador = movimientos_validos.listIterator();
                  while (iterador.hasNext()){
                        Tupla celda = iterador.next();
                        if (!celda.equals(celda_anterior)){
                              pasos_actuales++;
                              celdas_visitadas.add(celda);
                              encuentraSalida(celda, celda_actual, mapa[celda.f][celda.c], pasos_actuales, celdas_visitadas);
                              pasos_actuales--;
                              iterador.remove();
                        }else{
                              iterador.remove();
                        }
                  }
            }
      }

      private ArrayList<Tupla> getMovimientosValidos(Tupla celda_actual, Tupla celda_anterior, ArrayList<Tupla> celdas_visitadas) {
            ArrayList<Tupla> aux = new ArrayList<>();
            for (Tupla t : pasos_validos) {
                  int f = celda_actual.f + t.f;
                  int c = celda_actual.c + t.c;
                  if (f >= 0 && c >= 0 && f < F && c < C && mapa[f][c] != '#'){
                        if (!celdas_visitadas.contains(new Tupla(f, c)))
                              aux.add(new Tupla(f, c));
                  }
            }
            return aux;
      }
}
