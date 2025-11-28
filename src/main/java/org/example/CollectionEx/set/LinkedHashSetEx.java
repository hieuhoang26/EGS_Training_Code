package CollectionEx.set;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class LinkedHashSetEx {
    public static void main(String[] args) {

    /*
     INITIALIZED:
       - LinkedHashSet = HashSet + Doubly Linked List
       - Maintains insertion order
     */
        LinkedHashSet<String> lhs = new LinkedHashSet<>();
        LinkedHashSet<String> lhsFromCollection =
                new LinkedHashSet<>(Arrays.asList("A", "B", "C"));


    /*
     ADD:
       - HashMap stores key
       - Doubly linked list keeps order
       - No duplicates
     */
        lhs.add("Apple");
        lhs.add("Banana");
        lhs.add("Orange");
        lhs.add("Grapes");
        lhs.add("Apple");
        System.out.println("After adding: " + lhs);


    /*
     ADD DUPLICATE:
       - Key already exists → ignore
     */
        boolean added = lhs.add("Apple");
        System.out.println("Adding duplicate Apple? " + added);


    /*
     CONTAINS:
       - Uses hash (O(1))
     */
        System.out.println("Contains Banana? " + lhs.contains("Banana"));


    /*
     REMOVE:
       - HashMap + unlink from doubly linked list
     */
        lhs.remove("Orange");
        System.out.println("After remove Orange: " + lhs);


    /*
     ITERATION:
       - Follows insertion order
     */
        System.out.println("Iterating:");
        for (String item : lhs) {
            System.out.println("- " + item);
        }

    /*
     CLEAR:
       - clears HashMap + linked list
     */
        lhs.clear();
        System.out.println("After clear: " + lhs);
    }

}
