import DataBase.DBConnection;
import Views.LogIn;
import Views.UIStyle;

public class Main {

    public static void main(String[] args) {

        // Global modern UI
        UIStyle.apply();

        // Veritabanı init
        DBConnection.init();

        // Login ekranı
        new LogIn();
    }
}
