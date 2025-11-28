package CollectionEx.map;

import java.util.HashMap;
import java.util.Map;

public class HashMapEx {
    public static void main(String[] args) {

    /*
     INITIALIZED:
       - HashMap uses an array of buckets
       - Each bucket stores:
            - null (empty)
            - a linked list of nodes
            - OR a balanced tree (TreeNode) if chain > 8 elements
       - Capacity default = 16
       - Load factor default = 0.75
       - Resize occurs when size > capacity * loadFactor
     */
        HashMap<Integer, String> map = new HashMap<>();
        HashMap<Integer, String> map2 =
                new HashMap<>(Map.of(1, "A", 2, "B", 3, "C"));


    /*
     PUT:
       Steps:
         1. Compute hash = key.hashCode()
         2. bucketIndex = hash % capacity
         3. If bucket empty → insert new node
         4. If bucket has nodes:
             - Check equals()
             - If same key → update value
             - Else → append node
         5. If chain length > 8 → convert to Red-Black Tree
       Time: Average O(1)
     */
        map.put(1, "Apple");
        map.put(2, "Banana");
        map.put(3, "Orange");
        System.out.println("After put: " + map);


    /*
     PUT with same key:
       - Existing key? Replace value.
       - Returns old value.
     */
        String old = map.put(1, "Pineapple");
        System.out.println("Old value for key 1: " + old);
        System.out.println("After updating key 1: " + map);


    /*
     GET:
       - Find bucket using hash
       - Traverse bucket (linked list or tree)
       - Average O(1)
     */
        System.out.println("Value for key 2: " + map.get(2));


    /*
     CONTAINS KEY:
       - Same lookup procedure as get()
     */
        System.out.println("Contains key 3? " + map.containsKey(3));


    /*
     CONTAINS VALUE:
       - Scans all buckets
       - O(n)
     */
        System.out.println("Contains value 'Orange'? " + map.containsValue("Orange"));

    /*
     COMPUTE:
       - compute(key, BiFunction): Computes new value based on key and current value
       - If function returns null → entry is removed
       - If key doesn't exist → current value is null
     */

        // Update value based on current value
        map.compute(1, (key, oldValue) -> oldValue + " Updated");
        System.out.println("After compute key 1: " + map);

        // Remove entry by returning null
        map.compute(2, (key, oldValue) -> null);
        System.out.println("After compute key 2 to null: " + map);


    /*
     ITERATE KEYS:
       - Does NOT guarantee order
     */
        System.out.println("Iterating keys:");
        for (Integer key : map.keySet()) {
            System.out.println("- " + key);
        }

    /*
     ITERATE VALUES:
     */
        System.out.println("Iterating values:");
        for (String value : map.values()) {
            System.out.println("- " + value);
        }

    /*
     ITERATE ENTRY SET:
       - Best way to iterate pairs
     */
        System.out.println("Iterating entries:");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }

    /*
     REMOVE:
       - Compute hash → find bucket → unlink node
       - Average O(1)
     */
        map.remove(2);
        System.out.println("After removing key 2: " + map);

    /*
     SIZE / EMPTY:
     */
        System.out.println("Size: " + map.size());
        System.out.println("Is empty? " + map.isEmpty());


    /*
     CLEAR:
       - Removes all buckets
     */
        map.clear();
        System.out.println("After clear: " + map);
    }

}
