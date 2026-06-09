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
                        pasos_actuales++;
                        celdas_visitadas.add(celda);
                        encuentraSalida(celda, celda_actual, mapa[celda.f][celda.c], pasos_actuales, celdas_visitadas);
                        pasos_actuales--;
                        iterador.remove();
                  }
            }
      }

      private ArrayList<Tupla> getMovimientosValidos(Tupla celda_actual, Tupla celda_anterior, ArrayList<Tupla> celdas_visitadas) {
            ArrayList<Tupla> aux = new ArrayList<>();

            if (esPortal(mapa[celda_actual.f][celda_actual.c], mapa[celda_anterior.f][celda_anterior.c])){
                  Tupla portal = encuentraPortal(celda_actual, mapa[celda_actual.f][celda_actual.c]);
                  if (portal == null) throw new RuntimeException("No se encontro el otro portal pretendido");
                  aux.add(portal);
                  return aux;
            }

            for (Tupla t : pasos_validos) {
                  int f = celda_actual.f + t.f;
                  int c = celda_actual.c + t.c;
                  if (f >= 0 && c >= 0 && f < F && c < C && mapa[f][c] != '#'){
                        Tupla celda = new Tupla(f, c);
                        if (!celdas_visitadas.contains(celda) && !celda_anterior.equals(celda))
                              aux.add(celda);
                  }
            }
            return aux;
      }

      private Tupla encuentraPortal(Tupla celda, char valor) {
            for (int i = 0; i < F; i++) {
                  for (int j = 0; j < C; j++) {
                        if (mapa[i][j] == valor && (i != celda.f || j != celda.c)){
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
