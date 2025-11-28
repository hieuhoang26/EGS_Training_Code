package org.example.CollectionEx.queue;
import java.util.ArrayDeque;
import java.util.Iterator;


public class ArrayDequeEx {

    public static void main(String[] args) {

    /*
     INITIALIZED:
       - ArrayDeque = resizable circular array
       - Supports **Deque interface** (FIFO & LIFO)
       - No capacity restriction
       - Faster than LinkedList for stack/queue operations
     */
        ArrayDeque<String> deque = new ArrayDeque<>();

    /*
     ADD / ADD LAST / OFFER:
       - Adds element to tail (end)
       - O(1) amortized
     */
        deque.add("A");
        deque.addLast("B");
        deque.offer("C");
        System.out.println("Deque after adding: " + deque);

    /*
     ADD FIRST / OFFER FIRST:
       - Adds element at head (front)
       - O(1) amortized
     */
        deque.addFirst("Start");
        deque.offerFirst("VeryStart");
        System.out.println("Deque after addFirst/offerFirst: " + deque);

    /*
     REMOVE / POLL / POLL FIRST / POLL LAST:
       - Remove from head or tail
     */
        deque.poll();         // remove head
        deque.pollLast();     // remove tail
        System.out.println("Deque after poll/pollLast: " + deque);

    /*
     PEEK / PEEK FIRST / PEEK LAST:
       - View head or tail
     */
        System.out.println("Peek first: " + deque.peekFirst());
        System.out.println("Peek last: " + deque.peekLast());

    /*
     STACK BEHAVIOR:
       - push() = addFirst()
       - pop() = removeFirst()
     */
        deque.push("X");
        System.out.println("After push: " + deque);
        String top = deque.pop();
        System.out.println("After pop: " + deque + ", popped: " + top);

    /*
     ITERATION:
       - Iterates from head to tail
     */
        System.out.println("Iterating deque:");
        Iterator<String> it = deque.iterator();
        while (it.hasNext()) {
            System.out.println("- " + it.next());
        }

    /*
     CLEAR:
     */
        deque.clear();
        System.out.println("After clear: " + deque);
    }


}
