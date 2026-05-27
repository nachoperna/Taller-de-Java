import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class Quiz3 {

      public static void main(String[] args) {
            System.out.println("- EJ1 -");
            ej1();
            System.out.println("- EJ2 -");
            ej2();
            System.out.println("- EJ3 -");
            ej3();
            System.out.println("- EJ4 -");
            ej4();
            System.out.println("- EJ5 -");
            ej5();
            System.out.println("- EJ6 -");
            ej6();
            System.out.println("- EJ7 -");
            ej7();
            System.out.println("- EJ8 -");
            ej8();
            System.out.println("- EJ9 -");
            ej9();
      }

      public static void ej1() {
            List<Integer> list = new ArrayList<Integer>();
            Integer[] arr = {3,10,3};
            list = Arrays.asList(arr);
            list.set(0, 3);
            System.out.println(list);
            try {
                  list.add(1);
                  System.out.println(list);
            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public static void ej2() {
            Set<Student> students = new HashSet<Student>();
            students.add(new Student(1));
            students.add(new Student(3));
            students.add(new Student(4));
            students.add(new Student(1));
            students.add(new Student(3));
            System.out.println(students.size());
            Set<Integer> aux = new HashSet<Integer>();
            aux.add(new Integer(1));
            aux.add(new Integer(3));
            aux.add(new Integer(4));
            aux.add(new Integer(1));
            aux.add(new Integer(3));
            System.out.println(aux.size());
      }

      public static void ej3() {
            PriorityQueue<String> pQueue = new PriorityQueue<String>();
            pQueue.add("Apple");
            pQueue.add("Nokia");
            pQueue.add("Samsung");
            pQueue.add("Apple");
            System.out.println(pQueue.poll() + " " + pQueue.poll());
            System.out.println(" " + pQueue.peek() + " " + pQueue.poll());
      }
      
      public static void ej4() {
            TreeSet<Employee> empTreeSet = new TreeSet<Employee>(new EmployeeComparator());
            Employee emp1 = new Employee(20, "Clark");
            Employee emp2 = new Employee(24, "Bernie");
            Employee emp3 = new Employee(3, "Alex");
            Employee emp4 = new Employee(3, "Abex");

            empTreeSet.add(emp1);
            empTreeSet.add(emp2);
            empTreeSet.add(emp3);
            empTreeSet.add(emp4);

            for (Employee emp : empTreeSet) {
                  System.out.println(emp.name + " ");
            }
      }

      public static void ej5() {
            List<String> ll = new LinkedList<String>();
            ll.add("C");
            // ll.push("B");
            ll.addFirst("A");
            // ll.offer("D");
            // System.out.println(ll.remove() + " ");
            // System.out.println(ll.poll());
      }

      public static void ej6() {
            ArrayDeque<String> adq = new ArrayDeque<String>();
            adq.add("A");
            adq.push("B");
            adq.addFirst("C");
            adq.offer("D");

            System.out.println(adq.peek() + " " + adq.pop() + " " + adq.poll());
      }

      public static void ej7() {
            List<String> countries = new ArrayList<String>();
            countries.addAll(Arrays.asList("Australia", "Canada", "India", "USA"));
            countries.remove(new String("USA"));
            System.out.println(countries.size());
            
            List<Employee> empList = new ArrayList<Employee>();
            empList.add(new Employee(1, "A"));
            empList.add(new Employee(2, "B"));
            empList.add(new Employee(3, "C"));

            empList.remove(new Employee(1, "A"));

            System.out.println(empList.size());
      }
      
      public static void ej8() {
            List<Integer> list = new ArrayList<Integer>();
            list.add(10);
            list.add(10);
            System.out.println(list.size());
            try {
                  list.remove(10);
            } catch (Exception e) {
                  e.printStackTrace();
            }
            System.out.println(list.size());
      }

      public static void ej9() {
            List<Integer> list = new ArrayList<Integer>();
            list.add(10);
            list.add(10);
            System.out.println(list.size());
            try {
                  list.remove(new Integer(10));
            } catch (Exception e) {
                  e.printStackTrace();
            }
            System.out.println(list.size());
      }
}
