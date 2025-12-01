package org.example.GenericEx;

public class MethodEx {
    public static  <T extends Comparable<T>> int greaterThan(T[] arr, T elem){
        int count =0;
        for (T item: arr){
            if(item.compareTo(elem) > 0){
                count++;
            }
        }
        return count;

    }

    public static void main(String[] args) {
        Integer[] intArray = {1, 5, 3, 8, 2, 7};
        int greaterThan5 = greaterThan(intArray, 5);
        System.out.println("Số phần tử lớn hơn 5: " + greaterThan5);
    }
}
