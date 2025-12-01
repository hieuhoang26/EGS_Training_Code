package org.example.streamApi;

@FunctionalInterface
public interface OrderFilter {
    boolean test(Order order);

    default OrderFilter and(OrderFilter other){
        return o -> this.test(o) && other.test(o);
    }
}
