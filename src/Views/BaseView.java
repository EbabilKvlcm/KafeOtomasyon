package Views;

import Entities.User;
import javax.swing.*;

public abstract class BaseView extends JFrame {

    protected User user;

    public BaseView(User user, String title) {
        this.user = user;

        setTitle(title + " - " + user.getUsername());
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        init();
        setVisible(true);
    }

    protected abstract void init();
}
