package CollectionEx.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ArrayListEx {
    public static void main(String[] args) {
        /*
        INITIALIZED:
           - Creates Object[] array with capacity = 10 (default)
           - size = 0 (actual number of elements)
         */
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list1 = new ArrayList<>(20); // With initial capacity
        ArrayList<String> list2 = new ArrayList<>(Arrays.asList("A", "B", "C")); // From collection

        /*
        ADD:
            - stores elements in a dynamic array
            - If space available (size < capacity): add directly
            - If full: create new array with capacity = oldCapacity * 1.5
               - Copy all elements to new array
               - Add new element
         */
        list.add("Apple");
        list.add("Banana");
        list.add("Orange");
        list.add("Grapes");
        System.out.println("After adding: " + list);
        System.out.println("Current size: " + list.size());

        /*
        ADD AT INDEX:
           - Shift elements from that position to the right
           - Insert new element at the empty position
         */
        list.add(1, "Mango"); // Add "Mango" at index 1
        System.out.println("After adding Mango at index 1: " + list);

        // ACCESSING
        // GET ELEMENT BY INDEX (get)
        // Direct index access with O(1) complexity
        String fruit = list.get(2);
        System.out.println("Elem at 2: " + fruit);

        // CONTAINS
        // Searches - O(n)
        boolean hasApple = list.contains("Apple");
        System.out.println("Contains 'Apple' " + hasApple);

        // IndexOf
        int index = list.indexOf("Orange");
        System.out.println("Position of 'Orange': " + index);


        // UPDATE (set)
        // Replaces element at specified position and returns the old element
        String oldFruit = list.set(3, "Pineapple");
        System.out.println("List after update: " + list);

        /*
         REMOVING:
           - Shift all elements after removal position one position left
           - Decrease size by 1
           - Doesn't immediately shrink the array
         */
        String removedFruit = list.remove(0);
        System.out.println("After removing index 0: " + list);

        // REMOVE BY OBJECT
        // Searches for the object and removes it from the list
        boolean isRemoved = list.remove("Banana");
        System.out.println("After removing 'Banana': " + list);


        // ITERATE USING FOR-EACH
        System.out.println("for-each:");
        for (String item : list) {
            System.out.println("- " + item);
        }

        // ITERATE USING ITERATOR
        System.out.println("Iterating with Iterator:");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println("- " + iterator.next());
        }

        // CHECK IF EMPTY
        System.out.println("Is ArrayList empty? " + list.isEmpty());

        // REMOVE ALL ELEMENTS
        list.clear();
        System.out.println("After clear: " + list);
        System.out.println("Is ArrayList empty? " + list.isEmpty());
    }
}
