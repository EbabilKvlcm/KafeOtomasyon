package DataBase;

import Entities.Product;
import java.sql.*;
import java.util.*;

public class ProductDAO {

    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();

        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement()) {

            ResultSet rs = st.executeQuery("SELECT * FROM products");
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("category_id")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void add(String name, double price, int categoryId) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps =
                     c.prepareStatement(
                             "INSERT INTO products(name,price,category_id) VALUES(?,?,?)")) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, categoryId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps =
                     c.prepareStatement("DELETE FROM products WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePrice(int id, double price) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps =
                     c.prepareStatement("UPDATE products SET price=? WHERE id=?")) {
            ps.setDouble(1, price);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
