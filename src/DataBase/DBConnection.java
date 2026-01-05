package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    private static final String URL = "jdbc:h2:./cafeDB";
    private static final String USER = "sa";
    private static final String PASS = "";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 🔴 MAIN BURAYI ÇAĞIRIYOR
    public static void init() {

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             Statement st = c.createStatement()) {

            // USERS
            st.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(50),
                    password VARCHAR(50),
                    role VARCHAR(20)
                )
            """);

            // DEFAULT USERS
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

            // CATEGORIES
            st.execute("""
                CREATE TABLE IF NOT EXISTS categories (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(50)
                )
            """);

            // PRODUCTS
            st.execute("""
                CREATE TABLE IF NOT EXISTS products (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100),
                    price DOUBLE,
                    category_id INT,
                    FOREIGN KEY (category_id) REFERENCES categories(id)
                )
            """);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
