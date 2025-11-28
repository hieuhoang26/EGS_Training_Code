package CollectionEx.map;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapEx {
    public static void main(String[] args) {

    /*
     INITIALIZED:
       - LinkedHashMap = HashMap + Doubly Linked List
       - Maintains insertion order
       - Each entry contains:
            - key, value
            - hash
            - pointers: before, after
       - Default: insertion order
     */
        LinkedHashMap<Integer, String> lhm = new LinkedHashMap<>();
        LinkedHashMap<Integer, String> lhmFromCollection =
                new LinkedHashMap<>(Map.of(1, "A", 2, "B", 3, "C"));


    /*
     PUT:
       - Works like HashMap:
            1. hash → bucket index
            2. insert new node
            3. handle collisions via chaining
       - Additionally, adds node to doubly linked list
     */
        lhm.put(1, "Apple");
        lhm.put(2, "Banana");
        lhm.put(3, "Orange");
        System.out.println("After put: " + lhm);


    /*
     PUT with same key:
       - Updates value
       - Order in linked list remains unchanged
     */
        lhm.put(2, "Blueberry");
        System.out.println("After updating key 2: " + lhm);


    /*
     GET:
       - Hash lookup → O(1)
       - If accessOrder = true → moves node to end of linked list
     */
        System.out.println("Value for key 1: " + lhm.get(1));


    /*
     CONTAINS KEY / VALUE:
       - containsKey() → hash lookup → O(1)
       - containsValue() → iterate all entries → O(n)
     */
        System.out.println("Contains key 3? " + lhm.containsKey(3));
        System.out.println("Contains value 'Apple'? " + lhm.containsValue("Apple"));


    /*
     REMOVE:
       - HashMap remove + unlink from doubly linked list
       - O(1) average
     */
        lhm.remove(3);
        System.out.println("After removing key 3: " + lhm);


    /*
     ITERATE ENTRIES:
       - Always in insertion order
     */
        System.out.println("Iterating entries:");
        for (Map.Entry<Integer, String> entry : lhm.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }


    /*
     FIRST / LAST ENTRY:
       - Can access first and last by linked list pointers
     */
        System.out.println("First key: " + lhm.keySet().iterator().next());


    /*
     CLEAR:
       - Removes all buckets + linked list
     */
        lhm.clear();
        System.out.println("After clear: " + lhm);
    }

}
