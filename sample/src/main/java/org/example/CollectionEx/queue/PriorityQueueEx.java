package org.example.CollectionEx.queue;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueEx {

    public static void main(String[] args) {

    /*
     INITIALIZED:
       - PriorityQueue is a **heap-based queue**
       - Default: **Min-Heap** (natural ordering)
       - Optional: pass a **Comparator** for custom order
       - Backed by an array (dynamic resizing)
     */
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        PriorityQueue<Integer> maxPq = new PriorityQueue<>(Comparator.reverseOrder());

    /*
     ADD / OFFER:
       - Adds element at the end of array
       - Bubble up to maintain heap property
       - O(log n)
     */
        pq.offer(30);
        pq.offer(10);
        pq.offer(20);
        pq.offer(5);
        System.out.println("PriorityQueue (min-heap): " + pq);

        maxPq.offer(30);
        maxPq.offer(10);
        maxPq.offer(20);
        maxPq.offer(5);
        System.out.println("PriorityQueue (max-heap): " + maxPq);

    /*
     PEEK:
       - Returns **head** (min for min-heap, max for max-heap)
       - O(1)
     */
        System.out.println("Peek min: " + pq.peek());
        System.out.println("Peek max: " + maxPq.peek());

    /*
     POLL:
       - Removes the head
       - Swap last element to root → bubble down to maintain heap
       - O(log n)
     */
        pq.poll();
        System.out.println("After poll min: " + pq);

        maxPq.poll();
        System.out.println("After poll max: " + maxPq);

    /*
     ITERATION:
       - Iterates internal array (not sorted)
       - For sorted view → poll repeatedly or use toArray + sort
     */
        System.out.println("Iterating min-heap:");
        for (Integer val : pq) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

}
