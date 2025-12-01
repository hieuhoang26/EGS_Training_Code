package org.example.day1;

public class StringBufferEx {
    public static void main(String[] args) {
        // Creating StringBuffer objects
        StringBuffer sb1 = new StringBuffer(); // Default capacity 16
        StringBuffer sb2 = new StringBuffer("Hello");
        StringBuffer sb3 = new StringBuffer(50); // With specific capacity

        // Basic operations
        sb1.append("Java");
        sb1.append(" Program");
        System.out.println("append: " + sb1);

        // Insert
        sb2.insert(5, " World");
        System.out.println("insert: " + sb2);

        // Replace
        sb2.replace(6, 11, "Earth");
        System.out.println("replace: " + sb2);

        // Reverse
        sb2.reverse();
        System.out.println("reverse: " + sb2);
        sb2.reverse(); // Reverse back

        // Cap
        System.out.println("Length: " + sb1.length());
        System.out.println("Capacity: " + sb1.capacity());

        // Thread-safe demonstration
//        StringBuffer threadSafeBuffer = new StringBuffer();
//
//        Thread t1 = new Thread(() -> {
//            for (int i = 0; i < 1000; i++) {
//                threadSafeBuffer.append("A");
//            }
//        });
//
//        Thread t2 = new Thread(() -> {
//            for (int i = 0; i < 1000; i++) {
//                threadSafeBuffer.append("B");
//            }
//        });
//
//        t1.start();
//        t2.start();
//
//        try {
//            t1.join();
//            t2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Thread-safe buffer length: " + threadSafeBuffer.length());
    }
}
