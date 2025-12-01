OK! Mình sẽ tạo một **bài tập Java tổng hợp** cho lớp `Order`, sao cho bạn có thể luyện tất cả các kiến thức bạn liệt kê (Functional Interface, Lambda, Default Methods, Method References, Optional, Stream API). Đây là đề bài chi tiết:

---

## **Đề bài: Quản lý đơn hàng với Java 8+**

Bạn có lớp `Order` như sau:

```java
public class Order {
    private int id;
    private String productName;
    private String productType;
    private String vendor;
    private int quantity;
    private double price;

    // Constructor, getters, setters
}
```

### **Yêu cầu:**

1. **Functional Interface & Lambda Expression**

    * Tạo một functional interface `OrderFilter` để kiểm tra điều kiện lọc một đơn hàng (`boolean test(Order o)`).
    * Sử dụng **Lambda Expression** để:

        * Lọc các đơn hàng có `quantity > 10`.
        * Lọc các đơn hàng của vendor `"ABC Corp"`.

2. **Default Methods**

    * Trong interface `OrderFilter`, thêm một **default method** `and(OrderFilter other)` để kết hợp hai filter bằng AND.
    * Ví dụ: `filterQuantity.and(filterVendor)`.

3. **Method References**

    * Tạo một method `static boolean isHighValue(Order o)` trong lớp `OrderUtils` để kiểm tra `price * quantity > 1000`.
    * Sử dụng **method reference** để lọc danh sách các đơn hàng giá trị cao.

4. **Optional**

    * Viết phương thức `findOrderById(List<Order> orders, int id)` trả về `Optional<Order>` để tìm một đơn hàng theo ID.
    * Sử dụng Optional để in ra thông tin đơn hàng nếu tồn tại, hoặc in `"Order not found"` nếu không tìm thấy.

5. **Stream API**

    * Tính tổng doanh thu (`quantity * price`) cho tất cả đơn hàng.
    * Nhóm các đơn hàng theo `productType` và in ra số lượng đơn hàng từng loại.
    * Lọc các đơn hàng giá trị cao (dùng method reference `OrderUtils::isHighValue`) và sắp xếp theo giá giảm dần.

---

### **Mục tiêu**

* Áp dụng **Functional Interface** và **Lambda** để lọc dữ liệu.
* Thực hành **default methods** kết hợp filter.
* Dùng **method references** thay cho lambda khi có thể.
* Dùng **Optional** để tránh null.
* Thực hành **Stream API** với `map`, `filter`, `reduce`, `collect`, `groupingBy`, `sorted`.

---

Nếu bạn muốn, mình có thể viết sẵn **một skeleton code** cho bài này, bạn chỉ cần điền Lambda, Stream, Optional, v.v. để tập luyện.

Bạn có muốn mình viết skeleton code không?
