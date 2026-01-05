package Controllers;

import DataBase.UserDAO;
import Entities.User;

public class Control {

    private UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        if (username == null || password == null ||
                username.isEmpty() || password.isEmpty()) {
            return null;
        }

        return userDAO.login(username, password);
    }
}
