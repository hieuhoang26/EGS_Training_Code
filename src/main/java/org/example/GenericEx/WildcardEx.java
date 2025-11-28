package org.example.GenericEx;

import java.util.List;

public class WildcardEx {
    public static void printList(List<?> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("List is empty or null");
            return;
        }

        for (Object element : list) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
    public static double sumNumbers(List<? extends Number> list) {
        if (list == null || list.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Number number : list) {
            if (number != null) {
                sum += number.doubleValue();
            }
        }
        return sum;
    }
}
