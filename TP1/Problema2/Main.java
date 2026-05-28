import java.math.BigInteger;
import java.util.Scanner;

public class Main {
      public static void main(String[] args) {
            System.out.printf("Fibonacci a calcular: ");
            Scanner sc = new Scanner(System.in);
            long N = (long)sc.nextLong();

            if (N < 1000){
                  System.out.println("Resultado = " + fibonacciChico(N) );
            }else{
                  System.out.println("Resultado = " + fibonacciGrande(N) );
            }
            sc.close();
      }
 
      public static long fibonacciChico(long n) {
            if (n <= 1) return 1;
            long a = 0;
            long b = 1;
            for (long i = 2; i <= n; i++) {
                  long c = a + b;
                  a = b;
                  b = c;
            }
            return b;
      }

      public static BigInteger fibonacciGrande(long n) {
            if (n <= 1) return BigInteger.ONE;
            BigInteger a = BigInteger.ZERO;
            BigInteger b = BigInteger.ONE;
            for (long i = 2; i <= n; i++) {
                  BigInteger c = a.add(b);
                  a = b;
                  b = c;
            }
            return b;
      }
 
}
