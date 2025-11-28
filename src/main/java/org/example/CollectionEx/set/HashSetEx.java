package CollectionEx.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class HashSetEx {
    public static void main(String[] args) {

    /*
     INITIALIZED:
       - HashSet is backed by a HashMap internally
       - Each element is stored as a key in the HashMap
       - Value = a constant dummy object (new Object())
       - No duplicates allowed (because keys in HashMap are unique)
       - NOT Ordered
     */
        HashSet<String> set = new HashSet<>();
        HashSet<String> setFromCollection = new HashSet<>(Arrays.asList("A", "B", "C"));


    /*
     ADD: O(1)
       - When adding an element:
            1. hash = element.hashCode()
            2. bucket index = hash % capacity
            3. Check bucket:
                - If no node → insert new node
                - If node exists → compare using equals()
                  - If equals == true → duplicate → do NOT insert
                  - Else → add to linked list or tree
            4. If load factor exceeded → resize (capacity * 2)
     */
        set.add("Apple");
        set.add("Banana");
        set.add("Orange");
        set.add("Grapes");
        System.out.println("After adding: " + set);


    /*
     DUPLICATES:
       - Since equality is checked using hashCode() + equals()
       - Duplicate insert returns false
     */
        boolean added = set.add("Apple");  // Apple already exists
        System.out.println("Adding duplicate Apple? " + added);


    /*
     CONTAINS:
       - Compute hash - find bucket - scan bucket
     */
        boolean hasBanana = set.contains("Banana");
        System.out.println("Contains Banana? " + hasBanana);


    /*
     REMOVE:
       - Compute hash - find bucket - remove node
     */
        boolean removed = set.remove("Orange");
        System.out.println("Removing Orange: " + removed);
    /*
     ITERATION: O(n)
       - Iterates over internal HashMap keys
       - Order is NOT guaranteed (NOT sorted, NOT insertion order)
     */
        System.out.println("for-each:");
        for (String item : set) {
            System.out.println("- " + item);
        }
        Iterator<String> setIterator = set.iterator();
        while (setIterator.hasNext()){
            while (setIterator.hasNext()){
                String element = setIterator.next();
                System.out.println("- " + element);
            }
        }
    /*
     SIZE:
       - Returns number of unique elements
     */
        System.out.println("Size: " + set.size());


    /*
     IS EMPTY:
       - O(1)
     */
        System.out.println("Is empty? " + set.isEmpty());


    /*
     CLEAR:
       - Removes all entries from internal HashMap
       - size = 0
     */
        set.clear();
        System.out.println("After clear: " + set);
        System.out.println("Is empty? " + set.isEmpty());
    }


}
