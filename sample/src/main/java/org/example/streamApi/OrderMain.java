package org.example.streamApi;

import java.util.*;
import java.util.stream.Collectors;

public class OrderMain {
    public static void main(String[] args) {
        List<Order> orders = Arrays.asList(
                new Order(1, "Laptop", "Electronics", "ABC Corp", 5, 500),
                new Order(2, "Phone", "Electronics", "XYZ Ltd", 15, 200),
                new Order(3, "Desk", "Furniture", "ABC Corp", 20, 150),
                new Order(4, "Chair", "Furniture", "XYZ Ltd", 8, 100),
                new Order(5, "Monitor", "Electronics", "ABC Corp", 10, 300)
        );

        //Filter
        OrderFilter filterQuantity = order -> order.getQuantity() > 10;

        OrderFilter filterVendor = order -> order.getVendor().equals("ABC Corp");

        System.out.println("Orders with quantity > 10:");
        orders.stream().filter(filterQuantity::test).forEach(System.out::println);

        System.out.println("Orders with quantity > 10 and vendor: ABC ");
        orders.stream().filter(filterQuantity.and(filterVendor)::test).forEach(System.out::println);

        // High value
        System.out.println("High Value Order:");
        orders.stream().filter(OrderUtils::isHighValue).forEach(System.out::println);

        // Search
        int id =3;
        Optional<Order> rs = OrderUtils.findById(orders, id);
        rs.ifPresentOrElse(System.out::println,() -> System.out.println("Not found "));

        // Total
        double total = orders.stream().mapToDouble(o -> o.getQuantity() * o.getPrice()).sum();
        System.out.println("Total sum:"+  total);

        // Grouping
        Map<String, Long> ordersByType = orders.stream().collect(Collectors.groupingBy(Order::getProductType,Collectors.counting()));
        ordersByType.forEach((type,count) -> System.out.println(type + count));

        // decreasing high value
        System.out.println("High value orders sorted by total price descending:");
        orders.stream()
                .filter(OrderUtils::isHighValue)
                .sorted(Comparator.comparingDouble(o -> -o.getPrice()*o.getQuantity()))
                .forEach(System.out::println);
    }
}
