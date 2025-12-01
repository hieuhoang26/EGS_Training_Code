package org.example.CollectionEx.map;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapEx {
    public static void main(String[] args) {

    /*
     INITIALIZED:
       - TreeMap is implemented using a Red-Black Tree (self-balancing BST)
       - Keys are always sorted (natural order or custom Comparator)
       - Values can be anything
       - No null keys allowed (null values are allowed)
     */
        TreeMap<Integer, String> tm = new TreeMap<>();
        TreeMap<Integer, String> tmFromCollection =
                new TreeMap<>(Map.of(3, "C", 1, "A", 2, "B"));


    /*
     PUT: O(log n)
       - Insert key/value into Red-Black Tree
       - Tree automatically balances itself
     */
        tm.put(10, "Apple");
        tm.put(5, "Banana");
        tm.put(20, "Orange");
        tm.put(15, "Grapes");
        System.out.println("After put: " + tm); // Sorted by key


    /*
     PUT with existing key:
       - Updates value
       - Tree structure remains same
     */
        tm.put(10, "Pineapple");
        System.out.println("After updating key 10: " + tm);


    /*
     GET:
       - Traverse tree to find key → O(log n)
     */
        System.out.println("Value for key 5: " + tm.get(5));


    /*
     CONTAINS KEY / VALUE:
       - containsKey → O(log n)
       - containsValue → O(n) scan
     */
        System.out.println("Contains key 20? " + tm.containsKey(20));
        System.out.println("Contains value 'Orange'? " + tm.containsValue("Orange"));


    /*
     REMOVE:
       - Remove node from Red-Black Tree
       - Rebalance tree
     */
        tm.remove(15);
        System.out.println("After removing key 15: " + tm);


    /*
     ITERATE ENTRIES:
       - In-order traversal → keys always sorted
     */
        System.out.println("Iterating entries (sorted by key):");
        for (Map.Entry<Integer, String> entry : tm.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }


    /*
     FIRST / LAST KEY:
       - O(log n)
     */
        System.out.println("First key: " + tm.firstKey());
        System.out.println("Last key: " + tm.lastKey());


    /*
     HEADMAP / TAILMAP / SUBMAP:
       - Returns a view of portion of map
     */
        System.out.println("HeadMap (<=10): " + tm.headMap(10, true));
        System.out.println("TailMap (>=10): " + tm.tailMap(10, true));
        System.out.println("SubMap (5..20): " + tm.subMap(5, true, 20, true));


    /*
     CLEAR:
       - Removes all nodes
     */
        tm.clear();
        System.out.println("After clear: " + tm);
    }

}
