package CollectionEx.set;

import java.util.Arrays;
import java.util.TreeSet;

public class TreeSetEx {
    public static void main(String[] args) {

    /*
     INITIALIZED:
       - TreeSet is implemented using TreeMap
       - Internally uses a Red-Black Tree
       - Elements are always sorted (natural order or custom Comparator)
       - No duplicates allowed
     */
        TreeSet<String> ts = new TreeSet<>();
        TreeSet<String> tsFromCollection =
                new TreeSet<>(Arrays.asList("C", "A", "B"));


    /*
     ADD: O(log n)
       - Insert element into Red-Black Tree
       - Performs rotations to keep tree balanced
       - Sorting automatically maintained
     */
        ts.add("Apple");
        ts.add("Banana");
        ts.add("Orange");
        ts.add("Grapes");
        System.out.println("After adding: " + ts); // Sorted


    /*
     DUPLICATE:
       - Tree checks compareTo() == 0 → duplicate → ignore
     */
        boolean added = ts.add("Apple");
        System.out.println("Adding duplicate Apple? " + added);


    /*
     CONTAINS:
       - Searches tree using binary search-like traversal
       - O(log n)
     */
        System.out.println("Contains Banana? " + ts.contains("Banana"));


    /*
     REMOVE:
       - Remove node from Red-Black Tree
       - Rebalance tree
       - O(log n)
     */
        ts.remove("Orange");
        System.out.println("After removing Orange: " + ts);


    /*
     ITERATION:
       - In-order traversal of tree
       - Always sorted
     */
        System.out.println("Iterating (sorted):");
        for (String item : ts) {
            System.out.println("- " + item);
        }


    /*
     FIRST / LAST ELEMENT
     */
        System.out.println("First: " + ts.first());
        System.out.println("Last: " + ts.last());


    /*
     CLEAR
     */
        ts.clear();
        System.out.println("After clear: " + ts);
    }

}
