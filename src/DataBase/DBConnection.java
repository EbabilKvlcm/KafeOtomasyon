package DataBase;

import java.sql.*;

public class DBConnection {

    private static final String URL = "jdbc:h2:./cafeDB";
    private static final String USER = "sa";
    private static final String PASS = "";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("H2 Driver bulunamadı", e);
        }
    }

    public static void init() {
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             Statement st = c.createStatement()) {

            st.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(50),
                    password VARCHAR(50),
                    role VARCHAR(20)
                )
            """);

            st.execute("""
                INSERT INTO users (username,password,role)
                SELECT 'garson','123','GARSON'
                WHERE NOT EXISTS (SELECT 1 FROM users WHERE username='garson')
            """);

            st.execute("""
                INSERT INTO users (username,password,role)
                SELECT 'kasa','123','KASA'
                WHERE NOT EXISTS (SELECT 1 FROM users WHERE username='kasa')
            """);

            st.execute("""
                INSERT INTO users (username,password,role)
                SELECT 'mudur','123','MUDUR'
                WHERE NOT EXISTS (SELECT 1 FROM users WHERE username='mudur')
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
