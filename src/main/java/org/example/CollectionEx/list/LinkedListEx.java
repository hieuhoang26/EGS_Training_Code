package org.example.CollectionEx.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListEx {
    public static void main(String[] args) {

    /*
     INITIALIZED:
       - LinkedList is a Doubly Linked List
       - Each element is stored in a Node:
            [prev] <- [data] -> [next]
       - No fixed capacity (unlike ArrayList)
       - size = number of nodes
     */
        LinkedList<String> list = new LinkedList<>();
        LinkedList<String> list1 = new LinkedList<>(Arrays.asList("A", "B", "C")); // From collection


    /*
     ADD (addLast): O(1)
       - Adds new Node at the tail
       - tail.next = newNode
       - newNode.prev = oldTail
       - Update tail pointer
     */
        list.add("Apple");
        list.add("Banana");
        list.add("Orange");
        list.add("Grapes");
        System.out.println("After adding: " + list);


    /*
     ADD FIRST: O(1)
       - Insert new node before head
       - head.prev = newNode
       - newNode.next = oldHead
       - Update head pointer
     */
        list.addFirst("Strawberry");
        System.out.println("After addFirst: " + list);


    /*
     ADD AT INDEX:
       - Must traverse from head or tail to reach index → O(n)
       - After reaching position:
            newNode.prev = node.prev
            newNode.next = node
            Adjust pointers
     */
        list.add(2, "Mango");
        System.out.println("After add Mango at index 2: " + list);


    /*
     GET:
       - NO random access like ArrayList
       - Must traverse nodes from head or tail until index → O(n)
     */
        String fruit = list.get(3);
        System.out.println("Element at index 3: " + fruit);


    /*
     CONTAINS:
       - Linear scan node-by-node - O(n)
     */
        boolean hasApple = list.contains("Apple");
        System.out.println("Contains Apple? " + hasApple);


    /*
     INDEX OF:
       - Traverse through nodes to find element - O(n)
     */
        int index = list.indexOf("Orange");
        System.out.println("Index of Orange: " + index);


    /*
     UPDATE (set):
       - Traverse to index → O(n)
       - Replace node.item with new value
     */
        String oldFruit = list.set(1, "Blueberry");
        System.out.println("After update: " + list);


    /*
     REMOVE AT INDEX:
       - Traverse to index → O(n)
       - Remove node by adjusting pointers:
            node.prev.next = node.next
            node.next.prev = node.prev
     */
        String removed = list.remove(0);
        System.out.println("After removing index 0: " + list);


    /*
     REMOVE BY OBJECT:
       - Traverse until found → O(n)
       - Remove node like above
     */
        boolean removedBanana = list.remove("Banana");
        System.out.println("After removing Banana: " + list);


    /*
     FOR-EACH:
       - Each iteration moves to next node → O(n)
     */
        System.out.println("for-each:");
        for (String item : list) {
            System.out.println("- " + item);
        }


    /*
     ITERATOR:
       - Uses internal pointer to traverse nodes
     */
        System.out.println("Iterator:");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println("- " + iterator.next());
        }

    /*
        LISTITERATOR:
     */
        System.out.println("ListIterator:");
        ListIterator<String> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            System.out.println("- " + listIterator.next());
        }
        System.out.println("----------");
        while (listIterator.hasPrevious()) {
            System.out.println("- " + listIterator.previous());
        }
    /*
     CHECK IF EMPTY:
       - size == 0 → O(1)
     */
        System.out.println("Is empty? " + list.isEmpty());


    /*
     CLEAR:
       - Removes all nodes
       - head = tail = null
       - size = 0
     */
        list.clear();
        System.out.println("After clear: " + list);
        System.out.println("Is empty? " + list.isEmpty());

        /* ------------------QUEUE----------------------------*/
        System.out.println("------------------QUEUE----------------------------" );

        LinkedList<String> queue = new LinkedList<>();

    /*
     ENQUEUE (offer/add): O(1)
       - offerLast() adds a node at the tail
       - tail.next = newNode
       - newNode.prev = oldTail
       - tail = newNode
    */
        queue.offer("Task1");
        queue.offer("Task2");
        queue.offer("Task3");
        System.out.println("Queue after offer: " + queue);

    /*
     PEEK:
       - Returns head node value without removing it
       - Just returns head.item
    */
        System.out.println("Peek: " + queue.peek());

    /*
     DEQUEUE (poll):
       - Removes the head node
       - head = head.next
       - If new head != null → head.prev = null
    */
        String remove = queue.poll();
        System.out.println("After poll: " + queue);

    /*
     OFFER FIRST / OFFER LAST:
       - Queue can act like Deque
    */
        queue.offerFirst("UrgentTask");  // Insert at head
        queue.offerLast("BackgroundTask"); // Insert at tail
        System.out.println("After offerFirst/offerLast: " + queue);


        /* ------------------STACK----------------------------*/
        System.out.println("------------------STACK----------------------------" );
        LinkedList<String> stack = new LinkedList<>();

    /*
     PUSH:
       - push() = addFirst()
       - Creates new node and sets it to head:
            newNode.next = head
            head.prev = newNode
    */
        stack.push("A");
        stack.push("B");
        stack.push("C");
        System.out.println("Stack after push: " + stack);

    /*
     PEEK:
       - Returns top element = head.item
    */
        System.out.println("Peek top: " + stack.peek());

    /*
     POP:
       - Removes the head node
       - head = head.next
       - If head != null → head.prev = null
    */
        String top = stack.pop();
        System.out.println("After pop: " + stack);

    /*
       - push() <=> addFirst()
       - pop() <=> removeFirst()
       - peek() <=> getFirst()
    */
        stack.addFirst("X");  // Same as push
        stack.removeFirst();  // Same as pop

    }

}
