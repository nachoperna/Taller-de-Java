package ClasesEjemplo;

public class EqExample{ 
      private int field; 
      public EqExample(int init){ 
            this.field = init; 
      }
      public boolean equals(Object o){ 
            if (!(o instanceof EqExample))  return false;  return ((EqExample)o).field==this.field;  
      } 
}
