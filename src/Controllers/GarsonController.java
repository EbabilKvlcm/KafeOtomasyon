package Controllers;

import Entities.*;

import java.util.*;

public class GarsonController {

    public void addProductToOrder(
            Map<Integer, List<OrderItem>> orders,
            int tableId,
            Product product) {

        orders.putIfAbsent(tableId, new ArrayList<>());

        List<OrderItem> list = orders.get(tableId);

        for (OrderItem item : list) {
            if (item.getProduct().getId() == product.getId()) {
                item.increase();
                return;
            }
        }

        list.add(new OrderItem(product));
    }

    // DB’den gelecek
    public List<Product> getProductsByCategory(Category c) { return List.of(); }
    public List<Category> getCategories() { return List.of(); }
    public List<Table> getTables() { return List.of(); }
}
