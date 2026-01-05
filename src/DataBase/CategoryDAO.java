package DataBase;

import Entities.Category;
import java.sql.*;
import java.util.*;

public class CategoryDAO {

    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();

        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement()) {

            ResultSet rs = st.executeQuery("SELECT * FROM categories");
            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addCategory(String name) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps =
                     c.prepareStatement("INSERT INTO categories(name) VALUES(?)")) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(int id) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps =
                     c.prepareStatement("DELETE FROM categories WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
