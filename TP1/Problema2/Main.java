import java.math.BigInteger;

public class Main {
      public static void main(String[] args) {
            long numeros[] = {-10l, 2l, 5l, 8l, 10l, 50l, 150l, 500l, 1500l}; // Utilizamos un arreglo porque solo necesitamos algunos numeros de prueba
            long N = 90l;
            for (long numero : numeros) {
                  if (numero < 0){
                        System.out.println("No se puede calcular fibonacci de numero negativo");
                  } else if (numero < N){ 
                        /* 
                              Si el numero de prueba esta por debajo del umbral establecido por el enunciado 
                              entonces calculamos fibonacci con el tipo long porque es mas que suficiente el tamaño de la variable
                              para guardar el resultado de la operacion.
                         */
                        System.out.printf("\nFibonacci(%d) = %d", numero, fibonacciChico(numero));
                  } else{
                        /*
                              Si el numero esta por encima del umbral usamos la funcion que utiliza el tipo de dato BigInteger
                              el cual su tamaño solo esta limitado por la memoria del sistema asignada a la ejecucion de la aplicacion.
                              Es importante usar este tipo de dato para un valor mayor a 90 porque el fibonacci del numero 93 supera
                              el limite maximo del tipo de dato long.
                         */
                        System.out.printf("\nFibonacci(%d) = %d", numero, fibonacciGrande(numero));
                  }
            }
      }
 
      public static long fibonacciChico(long n) {
            if (n <= 1) return 1;
            long a = 1;
            long b = 1;
            /*
                  Iteramos hasta el numero por parametro sumando el resultado
                  de las ultimas 2 sumas, siendo asi: a = f(i-2) y b = f(i-1)
            */
            for (long i = 2; i <= n; i++) {
                  long c = a + b;
                  a = b;
                  b = c;
            }
            return b;
      }

      public static BigInteger fibonacciGrande(long n) {
            if (n <= 1) return BigInteger.ONE;
            BigInteger a = BigInteger.ONE;
            BigInteger b = BigInteger.ONE;
            for (long i = 2; i <= n; i++) {
                  BigInteger c = a.add(b);
                  a = b;
                  b = c;
            }
            return b;
      }
 
}
