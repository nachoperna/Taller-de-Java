public class Caso {

      private Character[] arr;
      private int X;
      private int Y;

      public Caso(Character[] arr, int X, int Y){
            this.arr = arr;
            this.X = X;
            this.Y = Y;
      }
      
      public int cantFotografiasArtisticas(){
            /*
               Como la responsabilidad de calcular la cantida de fotografias es propia de la clase Caso y además optamos 
               por un enfoque backtracking, hicimos ese método privado y lo accedemos a través de este público que tiene 
               acceso el Main.
            */
            return encuentraFAEs(0, arr.length, 'f'); // Primero iteramos todo el arreglo con un fotografo como objetivo a encontrar
      }

      private int encuentraFAEs(int lim_inf, int lim_sup, char objetivo){
            if (objetivo == 'X'){ // Discernible que nos indica que encontramos una escenario luego de encontrar un fotografo y un artistica, y devolvemos una fotografia artistica encontrada
                  return 1;
            }
            int cantidad = 0; // Variable que lleva el contador de fotografias artisticas encontradas
            for (int i = lim_inf; i <= lim_sup && i < arr.length && i >= 0; i++) { // Al ser un enfoque recursivo, los limites de iteracion van por parametro y controlamos que no estemos fuera de rangos
                  if (arr[i] == objetivo) { // Si encontramos nuestro objetivo en el arreglo, buscamos de vuelta con nuestro siguiente objetivo y los limites adecuados
                        cantidad += encuentraFAEs(i + X, i + Y, getSiguienteObjetivo(objetivo)); // Hacemos una nueva iteracion en el arreglo hacia la derecha del objetivo actual en busqueda del siguiente objetivo
                        if (objetivo != 'e') // Ponemos esta condicion para no volver a retornar un 1 si ya encontramos un escenario, cuando queremos buscar por el lado izquierdo del objetivo
                              cantidad += encuentraFAEs(i - Y, i - X, getSiguienteObjetivo(objetivo));
                              /*
                                Iteramos por el lado izquierdo del objetivo, poniendo como limite inferior el rango determinado parametro inicial Y
                                y como limite superior el rango determinado por el parametro inicial X, ahorrandonos asi dos clausulas for distintas
                                e iterando siempre de izquierda a derecha
                              */
                  }
            }
            return cantidad; // Retornamos la cantidad de fotografias artisticas acumuladas en el arreglo
      }

      private char getSiguienteObjetivo(char o){
            if (o == 'f') // Si ya encontramos un fotografo, buscamos ahora un artista
                  return 'a';
            else if (o == 'a') // Si ya encontramos un artista, buscamos ahora un escenario
                  return 'e';
            return 'X'; // Si ya encontramos un escenario, ponemos un discernible para retornar 1 fotografia artistica encontrada
      }

      public Character[] getArr() {
          return arr;
      }

      public int getX() {
          return X;
      }

      public int getY() {
          return Y;
      }
}
