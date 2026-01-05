package Controllers;

import DataBase.CategoryDAO;
import DataBase.ProductDAO;
import DataBase.OrderStore;
import Entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GarsonController {

    private CategoryDAO categoryDAO = new CategoryDAO();
    private ProductDAO productDAO = new ProductDAO();

    public List<Table> getTables() {
        return new ArrayList<>(OrderStore.tableMap.values());
    }

    public List<Category> getCategories() {
        return categoryDAO.getAll();
    }

    public List<Product> getProductsByCategory(Category c) {
        List<Product> result = new ArrayList<>();
        for (Product p : productDAO.getAll()) {
            if (p.getCategoryId() == c.getId()) {
                result.add(p);
            }
        }
        return result;
    }

    public void addProduct(Table table, Product product) {

        OrderStore.activeOrders.putIfAbsent(table.getId(), new ArrayList<>());
        List<OrderItem> list = OrderStore.activeOrders.get(table.getId());

        for (OrderItem item : list) {
            if (item.getProduct().getId() == product.getId()) {
                item.increase();
                table.setStatus(TableStatus.ORDERED);
                return;
            }
        }

        list.add(new OrderItem(product));
        table.setStatus(TableStatus.ORDERED);
    }

    public List<OrderItem> getOrder(Table table) {
        return OrderStore.activeOrders.get(table.getId());
    }

    public void sendToKasa(Table table) {
        OrderStore.kasaOrders.put(
                table.getId(),
                OrderStore.activeOrders.get(table.getId())
        );
        OrderStore.activeOrders.remove(table.getId());
        table.setStatus(TableStatus.SENT_TO_KASA);
    }
}
