package Controllers;

import DataBase.CategoryDAO;
import DataBase.ProductDAO;
import DataBase.OrderStore;
import Entities.*;

import java.util.*;

public class GarsonController {

    private CategoryDAO categoryDAO = new CategoryDAO();
    private ProductDAO productDAO = new ProductDAO();

    public List<Category> getCategories() {
        return categoryDAO.getAll();
    }

    public List<Product> getProductsByCategory(Category category) {
        List<Product> result = new ArrayList<>();
        for (Product p : productDAO.getAll()) {
            if (p.getCategoryId() == category.getId()) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Table> getTables() {
        List<Table> tables = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            tables.add(new Table(i, "Masa " + i));
        }
        return tables;
    }

    public void addProductToOrder(Map<Integer, List<OrderItem>> orders,
                                  int tableId, Product product) {
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

    public void decreaseProduct(Map<Integer, List<OrderItem>> orders,
                                int tableId, OrderItem item) {
        if (item == null) return;
        item.decrease();
        if (item.getQuantity() <= 0) {
            orders.get(tableId).remove(item);
        }
    }

    public void removeProduct(Map<Integer, List<OrderItem>> orders,
                              int tableId, OrderItem item) {
        if (item != null) {
            orders.get(tableId).remove(item);
        }
    }

    public double getTableTotal(List<OrderItem> list) {
        double total = 0;
        for (OrderItem item : list) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void sendToKasa(Map<Integer, List<OrderItem>> orders, int tableId) {
        OrderStore.kasaOrders.put(tableId, orders.get(tableId));
    }
}
