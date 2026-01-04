package DataBase;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInit {

    public static void init() {

        try (Connection c = DBConnection.getConnection();
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
