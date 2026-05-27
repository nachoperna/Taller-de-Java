public class Quiz1_tipos {

      public static void main(String[] args) {
            System.out.println("- EJ1 -");
            ej1();
            System.out.println("- EJ2 -");
            ej2();
            System.out.println("- EJ3 -");
            ej3();
            System.out.println("- EJ4 -");
            System.out.println(ej4(0.0));
            System.out.println("- EJ5 -");
            ej5();
            System.out.println("- EJ6 -");
            ej6();
            System.out.println("- EJ7 -");
            ej7();
            System.out.println("- EJ8 -");
            ej8();
      }

      public static void ej1() {
            int a = -1; 
            a=(int)(char)a; 
            System.out.println(a);  
            // int a = 10; 
            // System.out.println(a);
      }

      public static void ej2() {
            int a = 5; 
            char b=(char)a; 
            System.out.println(b);
      }

      public static void ej3() {
            float f = 1/10; 
            System.out.println(f*10 == 1);
      }

      public static Boolean ej4(double d) { 
            Boolean result = null; 
            if (d >= 0)  
                  result = true; 
            else if (d < 0) 
                  result = false; 
            return result; 
      } 

      public static void ej5() {
            char x = '0'; 
            int i = 0; 
            System.out.println(true ? x : 0); 
            System.out.println(false ? i : x); 
      }

      public static void ej6() {
            final long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000; 
            final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
            System.out.println(MICROS_PER_DAY / MILLIS_PER_DAY);
      }

      public static void ej7() {
            System.out.println(2.00 - 1.10);
      }

      public static void ej8() {
            byte x = 0; 
            int i=128; 
            x += i;
            System.out.println(x);
            Object z = "Que "; 
            String j = "interesante!!";
            z = z + j;
            System.out.println(z);
      }
}
