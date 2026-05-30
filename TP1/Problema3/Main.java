public class Main {
      public static void main(String[] args) {
 
            Node n1 = new Node(5);
            n1.addNodeRecursivo(3, n1);
            n1.addNodeRecursivo(7, n1);
            n1.addNodeRecursivo(2, n1);
            n1.addNodeRecursivo(4, n1);
            n1.addNodeRecursivo(6, n1);

            n1.printInOrder();
            System.out.println("Arbol es binario: " + n1.esBinario(n1, null, null));
      }
}
