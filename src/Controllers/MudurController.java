package Controllers;

import DataBase.CategoryDAO;
import DataBase.ProductDAO;
import Entities.Category;
import Entities.Product;

import java.util.List;

public class MudurController {

    private CategoryDAO categoryDAO = new CategoryDAO();
    private ProductDAO productDAO = new ProductDAO();

    // CATEGORY
    public List<Category> getCategories() {
        return categoryDAO.getAll();
    }

    public void addCategory(String name) {
        if (name != null && !name.isEmpty()) {
            categoryDAO.addCategory(name);
        }
    }

    public void deleteCategory(Category category) {
        if (category != null) {
            categoryDAO.deleteCategory(category.getId());
        }
    }

    // PRODUCT
    public List<Product> getProducts() {
        return productDAO.getAll();
    }

    public void addProduct(String name, double price, Category category) {
        if (name != null && !name.isEmpty() && price > 0 && category != null) {
            productDAO.add(name, price, category.getId());
        }
    }

    public void deleteProduct(Product product) {
        if (product != null) {
            productDAO.delete(product.getId());
        }
    }
}
