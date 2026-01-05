package DataBase;

import Entities.Role;
import Entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public User login(String username, String password) {

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps =
                     c.prepareStatement(
                             "SELECT * FROM users WHERE username=? AND password=?")) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        Role.valueOf(rs.getString("role"))
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
