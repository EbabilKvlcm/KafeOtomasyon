package Controllers;

import DataBase.CategoryDAO;
import DataBase.ProductDAO;
import Entities.*;

import java.util.List;

public class MudurController {

    private CategoryDAO categoryDAO = new CategoryDAO();
    private ProductDAO productDAO = new ProductDAO();

    public List<Category> getCategories() {
        return categoryDAO.getAll();
    }

    public void addCategory(String name) {
        if (!name.isEmpty()) categoryDAO.addCategory(name);
    }

    public void deleteCategory(Category c) {
        if (c != null) categoryDAO.deleteCategory(c.getId());
    }

    public List<Product> getProducts() {
        return productDAO.getAll();
    }

    public void addProduct(String name, double price, Category c) {
        if (c != null && price > 0) {
            productDAO.add(name, price, c.getId());
        }
    }

    public void deleteProduct(Product p) {
        if (p != null) productDAO.delete(p.getId());
    }

    public void updateProductPrice(Product p, double price) {
        if (p != null && price > 0) {
            productDAO.updatePrice(p.getId(), price);
        }
    }
}
