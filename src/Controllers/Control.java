package Controllers;

import DataBase.UserDAO;
import Entities.User;

public class Control {

    private final UserDAO userDAO;

    public Control() {
        this.userDAO = new UserDAO();
    }

    public User login(String username, String password) {

        if (username == null || password == null) {
            return null;
        }

        username = username.trim();

        if (username.isEmpty() || password.isEmpty()) {
            return null;
        }

        return userDAO.login(username, password);
    }
}
