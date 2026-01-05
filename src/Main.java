import DataBase.DBConnection;
import Views.LogIn;
import Views.UIStyle;

public class Main {

    public static void main(String[] args) {

        UIStyle.apply();   // global modern stil
        DBConnection.init(); // db başlat

        new LogIn(); // login ekranı
    }
}
