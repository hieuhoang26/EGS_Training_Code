package org.example.streamApi;

import java.util.List;
import java.util.Optional;

public class OrderUtils {
    public static boolean isHighValue(Order order){
        return order.getPrice() * order.getQuantity() > 1000;
    }
    public static Optional<Order> findById(List<Order> list, Integer id){
        return list.stream().filter(o -> o.getId() == id).findFirst();
    }
}
