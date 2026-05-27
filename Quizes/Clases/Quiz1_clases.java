import java.util.ArrayList;

import ClasesEjemplo.ClaseA;
import ClasesEjemplo.ClaseB;
import ClasesEjemplo.ClaseC;
import ClasesEjemplo.ClaseD;
import ClasesEjemplo.ClaseE;
import ClasesEjemplo.InterfaceI;
import ClasesEjemplo.EqExample;

public class Quiz1_clases {

      public static void main(String[] args) {
            System.out.println("- EJ1 -");
            ej1();
            System.out.println("- EJ2b -");
            ej2b();
            System.out.println("- EJ2c -");
            ej2c();
            System.out.println("- EJ3 -");
            ej3();
            System.out.println("- EJ4a -");
            ej4a();
            System.out.println("- EJ4b -");
            ej4b();
            System.out.println("- EJ4c -");
            ej4c();
            System.out.println("- EJ4d -");
            ej4d();
            System.out.println("- EJ5a -");
            ej5a();
            System.out.println("- EJ5b -");
            ej5b();
            System.out.println("- EJ9 -");
            ej9();
      }

      public static void ej1() {
            System.out.println("- instancia de ClaseB -");
            ClaseA var1 = new ClaseB();     
            System.out.println(var1.metodo1());
            System.out.println(var1.metodo2());
            System.out.println(var1.metodo3());

            System.out.println("- instancia de ClaseC -");
            ClaseA var2 = new ClaseC();     
            System.out.println(var2.metodo1());
            System.out.println(var2.metodo2());
            System.out.println(var2.metodo3());
            
            System.out.println("- instancia de ClaseD -");
            ClaseA var3 = new ClaseD();     
            System.out.println(var3.metodo1());
            System.out.println(var3.metodo2());
            System.out.println(var3.metodo3());
            
            System.out.println("- instancia de ClaseE -");
            ClaseA var4 = new ClaseD();     
            System.out.println(var4.metodo1());
            System.out.println(var4.metodo2());
            System.out.println(var4.metodo3());
      }

      public static void ej2b() {
            ClaseA var = new ClaseD();
            System.out.println(var instanceof ClaseA);
            System.out.println(var instanceof ClaseB);
            System.out.println(var instanceof ClaseC);
            System.out.println(var instanceof ClaseD);
            System.out.println(var instanceof ClaseE);
      }

      public static void ej2c() {
            InterfaceI var = new ClaseE();
            System.out.println(var instanceof ClaseA);
            System.out.println(var instanceof ClaseB);
            System.out.println(var instanceof ClaseC);
            System.out.println(var instanceof ClaseD);
            System.out.println(var instanceof ClaseE);
      }

      public static void ej3() {
            EqExample v1 = new EqExample(10);
            EqExample v2 = new EqExample(10);
            System.out.println(v1 == v2);
            System.out.println(v1.equals(v2));
      }

      public static void ej4a() {
            Integer a = new Integer(10); 
            Integer b = new Integer(10); 
            System.out.println(a==b); 
            System.out.println(a.equals(b));
      }

      public static void ej4b() {
            Integer a = 10; 
            Integer b = 10; 
            System.out.println(a==b); 
            System.out.println(a.equals(b));
      }
      
      public static void ej4c() {
            Integer a = 128; 
            Integer b = 128; 
            System.out.println(a==b); 
            System.out.println(a.equals(b));
      }

      public static void ej4d() {
            Integer a = -129; 
            Integer b = -129; 
            System.out.println(a==b); 
            System.out.println(a.equals(b));
      }

      public static void ej5a() {
            ArrayList<Integer> al = new ArrayList<>(); 
            al.add(10); 
            al.add(9); 
            al.add(8); 
            al.add(7); 
            al.add(5); 
            al.add(4); 
            al.add(3); 
            al.add(2); 
            al.add(1); 
            al.add(0); 
            al.remove(2);
            System.out.println(al);
      }

      public static void ej5b() {
            ArrayList<Integer> al = new ArrayList<>(); 
            al.add(10); 
            al.add(9); 
            al.add(8); 
            al.add(7); 
            al.add(5); 
            al.add(4); 
            al.add(3); 
            al.add(2); 
            al.add(1); 
            al.add(0); 
            al.remove(Integer.valueOf(2));
            System.out.println(al);
      }

      public static void ej9() {
            String str = NULL;
            System.out.println(str);
      }
}
