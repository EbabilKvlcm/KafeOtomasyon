import DataBase.DBConnection;
import Views.LogIn;

public class Main {

    public static void main(String[] args) {
        DBConnection.init();   // veritabanını hazırlar
        new LogIn();           // login ekranını açar
    }
}
