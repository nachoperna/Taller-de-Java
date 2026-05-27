import java.util.*;

public class Quiz2 {

      public static void main(String[] args) {
            System.out.println("- EJ1 -");
            ej1();
            System.out.println("- EJ3a -");
            ej3a();
            System.out.println("- EJ3b -");
            ej3b();
            System.out.println("- EJ4 -");
            ej4();
      }

      public static void ej1() {
            List list = new ArrayList<Integer>();
            for(int i=0;i<10;i++)
            list.add(new Integer(i));

            for(Iterator<Integer> i=list.iterator();i.hasNext();){
                  Integer val = i.next();
                  System.out.println(val.intValue()+1);
            }
      }

      public static void ej3a() {
            String test = "no"; 
 		try{ 
 			System.out.println("Empieza el try"); 
 			hacerAlgoRiesgoso(test); 
 			System.out.println("Termina el try"); 
 		}catch(Exception e){ 
 			System.out.println("Scary Exception"); 
 		}finally{ 
 			System.out.println("finally"); 
 		} 
 		System.out.println("Fin del main");
      }

      public static void hacerAlgoRiesgoso(String test) throws Exception{ 
            System.out.println("Empieza hacerAlgoRiesgoso");  
            if(test.equals("si")) 
                  throw new Exception(); 
            System.out.println("Termina hacerAlgoRiesgoso");  
      }

      public static void ej3b() {
            String test = "si"; 
 		try{ 
 			System.out.println("Empieza el try"); 
 			hacerAlgoRiesgoso(test); 
 			System.out.println("Termina el try"); 
 		}catch(Exception e){ 
 			System.out.println("Scary Exception"); 
 		}finally{ 
 			System.out.println("finally"); 
 		} 
 		System.out.println("Fin del main");
      }

      public static void ej4() {
            try{

            }finally{
            }
            System.out.println("try sin catch compilado");
      }

      public static void ej5() {
            // catch (Exception e) {
            // }
      }
      
      public static void ej6() {
            // try {
            // } catch (Exception e) {
            // } catch (ArithmeticException a) {
            // }
      }

      // public static HashMap<String,Integer> ej8(List<String> nombres) {
      //       nombres.is
      // }
}
