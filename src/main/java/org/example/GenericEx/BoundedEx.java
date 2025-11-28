package org.example.GenericEx;

import java.util.Arrays;
import java.util.List;

public class BoundedEx {
    public static <T extends Number> double sumList(List<T> list){
        if (list == null || list.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (T number : list) {
            if (number != null) {
                sum += number.doubleValue();
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        List<Integer> arr = Arrays.asList(1,2,3,4);
        double intSum = sumList(arr);
        System.out.println("Tổng List<Integer>: " + intSum);

    }
}
