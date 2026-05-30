public class Node {

      int data;
      Node left;
      Node right;

      public Node(int data){
            this.data = data;
            this.left = null;
            this.right = null;
      }
      public Node addNodeRecursivo(int data, Node nodo){
            if (nodo == null)
                  return new Node(data);
            if (data < nodo.data) {
                  nodo.left = addNodeRecursivo(data, nodo.left);
            } else {
                  nodo.right = addNodeRecursivo(data, nodo.right);
            }
            return nodo;
      }
      public void addNode(int data){
            if(this.data > data){
                  if(this.left == null){
                        this.left = new Node(data);
                  } else {
                        this.left.addNode(data);
                  }
            } else {
                  if(this.right == null){
                        this.right = new Node(data);
                  } else {
                        this.right.addNode(data);
                  }
            }
      }

      public void printInOrder(){
            if(this.left != null){
                  this.left.printInOrder();
            }
            System.out.print(this.data + " ");
            if(this.right != null){
                  this.right.printInOrder();
            }
      }

      public boolean esBinario(Node nodo, Integer min, Integer max){
            if (nodo == null)
                  return true;
            if ((min != null && nodo.data <= min) || (max != null && nodo.data >= max))
                  return false;
            return esBinario(nodo.left, min, nodo.data) && esBinario(nodo.right, nodo.data, max);
      }
}
